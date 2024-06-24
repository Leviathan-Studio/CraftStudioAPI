package com.leviathanstudio.craftstudio.common.network;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Predicates;
import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.InfoChannel;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class AnimatedEventMessageUtil {
	
    private static final CommonHandler SERVER = new ServerHandler();
    private static final CommonHandler CLIENT = new ClientHandler();
    
	public static final BiConsumer<AnimatedEventMessage, PacketBuffer> ENCODER = (msg, buffer) -> {
    	if (msg.event < 0 || msg.event >= EnumIAnimatedEvent.ID_COUNT) {
            buffer.writeShort(-1);
            CraftStudioApi.getLogger().error("Unsuported event id " + msg.event + " for network message.");
            return;
        }
        if (msg.animated instanceof Entity) {
            Entity e = (Entity) msg.animated;
            buffer.writeShort(msg.event);
            UUID uuid = e.getUniqueID();
            buffer.writeLong(uuid.getMostSignificantBits());
            buffer.writeLong(uuid.getLeastSignificantBits());
        } else if (msg.animated instanceof TileEntity) {
            TileEntity te = (TileEntity) msg.animated;
            buffer.writeShort(msg.event + EnumIAnimatedEvent.ID_COUNT);
            BlockPos pos = te.getPos();
            buffer.writeInt(pos.getX());
            buffer.writeInt(pos.getY());
            buffer.writeInt(pos.getZ());
        } else {
            buffer.writeShort(-1);
            CraftStudioApi.getLogger().error("Unsuported class " + msg.animated.getClass().getSimpleName() + " for network message.");
            CraftStudioApi.getLogger().error("You are trying to animate an other class than Entity or TileEntity.");
            return;
        }
        buffer.writeShort(msg.animId);
        if (msg.event != EnumIAnimatedEvent.STOP_ANIM.getId())
            buffer.writeFloat(msg.keyframeInfo);
        if (msg.event == EnumIAnimatedEvent.STOP_START_ANIM.getId())
            buffer.writeShort(msg.optAnimId);
    };
    
    public static final Function<PacketBuffer, AnimatedEventMessage> DECODER = (buffer) -> {
    	AnimatedEventMessage msg = new AnimatedEventMessage();
    	
    	short actualEvent = buffer.readShort();
        if (actualEvent < 0 || actualEvent >= 2 * EnumIAnimatedEvent.ID_COUNT) {
        	msg.event = -1;
            CraftStudioApi.getLogger().error("Networking error : invalid packet.");
            return null;
        } else if (actualEvent < EnumIAnimatedEvent.ID_COUNT) {
        	msg.most = buffer.readLong();
        	msg.least = buffer.readLong();
        	msg.event = actualEvent;
        	msg.hasEntity = true;
        } else {
        	msg.x = buffer.readInt();
        	msg.y = buffer.readInt();
        	msg.z = buffer.readInt();
            msg.event = (short) (actualEvent - EnumIAnimatedEvent.ID_COUNT);
            msg.hasEntity = false;
        }
        msg.animId = buffer.readShort();
        if (msg.event != 2)
        	msg.keyframeInfo = buffer.readFloat();
        if (msg.event > 2)
        	msg.optAnimId = buffer.readShort();
    	return msg;
    };


    public static BiConsumer<AnimatedEventMessage, Supplier<Context>> HANDLER = (msg, ctx) -> {
    	switch(ctx.get().getDirection()) {
    	case PLAY_TO_SERVER:
    		SERVER.handle(msg, ctx.get());
    		break;
    	case PLAY_TO_CLIENT:
    		CLIENT.handle(msg, ctx.get());
    		break;
    	default:
    		// Do nothing
    		break;
    	}
        ctx.get().setPacketHandled(true);
    };
   

        
    private static class ServerHandler extends CommonHandler {

		@Override
		public Entity getEntityByUUID(Context ctx, long most, long least) {
			ServerPlayerEntity sender = ctx.getSender(); // the client that sent this packet
	        ServerWorld world = (ServerWorld)sender.getEntityWorld();
	        UUID uuid = new UUID(most, least);
	        for (Entity e : world.getEntities(null, Predicates.alwaysTrue()))
	        	if (e.getUniqueID().equals(uuid))
	        		return e;        	
	        	
	        return null;
		}

		@Override
		public TileEntity getTileEntityByPos(Context ctx, int x, int y, int z) {
	        return ctx.getSender().world.getTileEntity(new BlockPos(x, y, z));
		}

		@Override
		public void handle(AnimatedEventMessage message, Context ctx) {
			ctx.enqueueWork(() -> {
	    		if (handleCommonSide(message, ctx)) {
	                message.animated.getAnimationHandler();
	                boolean succes = AnimationHandler.onServerIAnimatedEvent(message);
	                if (succes && message.event != EnumIAnimatedEvent.ANSWER_START_ANIM.getId())
	                	CSNetworkHelper.sendPacketTo(new AnimatedEventMessage(message), ctx.getSender());
	            }
	    	});			
		}
    	
    }
    
    private static class ClientHandler extends CommonHandler {

    	@Override
		public Entity getEntityByUUID(Context ctx, long most, long least) {
			ServerPlayerEntity sender = ctx.getSender(); // the client that sent this packet
			ClientWorld world = (ClientWorld)sender.getEntityWorld();
	        UUID uuid = new UUID(most, least);
	        
	        for (Entity e : world.getAllEntities())
	        	if (e.getUniqueID().equals(uuid))
	        		return e;        	
	        	
	        return null;
		}

		@Override
		public TileEntity getTileEntityByPos(Context ctx, int x, int y, int z) {
	        return ctx.getSender().world.getTileEntity(new BlockPos(x, y, z));
		}

		@Override
		public void handle(AnimatedEventMessage message, Context ctx) {
			ctx.enqueueWork(() -> {
	            if (handleCommonSide(message, ctx)) {
	                boolean succes = message.animated.getAnimationHandler().onClientIAnimatedEvent(message);
	                if (succes && message.animated.getAnimationHandler() instanceof ClientAnimationHandler
	                        && (message.event == EnumIAnimatedEvent.START_ANIM.getId() || message.event == EnumIAnimatedEvent.STOP_START_ANIM.getId())) {
	                    @SuppressWarnings("rawtypes")
						ClientAnimationHandler hand = (ClientAnimationHandler) message.animated.getAnimationHandler();
	                    String animName = hand.getAnimNameFromId(message.animId);
	                    InfoChannel infoC = (InfoChannel) hand.getAnimChannels().get(animName);
	                    CSNetworkHelper.sendPacketToServer(new AnimatedEventMessage(EnumIAnimatedEvent.ANSWER_START_ANIM, message.animated, message.animId, infoC.totalFrames));
	                }
	            }
	        });
		}
    	
    }

    private static abstract class CommonHandler {
    	
    	protected boolean handleCommonSide(AnimatedEventMessage message, Context ctx) {
        	if (message.hasEntity) {
                Entity e = this.getEntityByUUID(ctx, message.most, message.least);
                if (!(e instanceof IAnimated)) {
                    CraftStudioApi.getLogger().debug("Networking error : invalid entity.");
                    return false;
                }
                message.animated = (IAnimated) e;
            } else {
                TileEntity te = this.getTileEntityByPos(ctx, message.x, message.y, message.z);
                if (!(te instanceof IAnimated)) {
                    CraftStudioApi.getLogger().debug("Networking error : invalid tile entity.");
                    return false;
                }
                message.animated = (IAnimated) te;
            }
        	return true;
        }
    	
    	/**
         * Get an entity by its UUID.
         *
         * @param ctx   The context of the message received.
         * @param most  The most significants bits of the UUID.
         * @param least The least significants bits of the UUID.
         * @return The Entity, null if it couldn't be found.
         */
        public abstract Entity getEntityByUUID(Context ctx, long most, long least);
        
        /**
         * Get a TileEntity by its position.
         *
         * @param ctx The context of the message received.
         * @param x   The position on the x axis.
         * @param y   The position on the y axis.
         * @param z   The position on the z axis.
         * @return The TileEntity, null if it couldn't be found.
         */
        public abstract TileEntity getTileEntityByPos(Context ctx, int x, int y, int z);
        
        public abstract void handle(AnimatedEventMessage message, Context ctx);
    }
}
