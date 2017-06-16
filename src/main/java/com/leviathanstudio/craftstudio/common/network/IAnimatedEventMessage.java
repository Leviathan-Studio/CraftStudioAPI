package com.leviathanstudio.craftstudio.common.network;

import java.util.List;
import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class IAnimatedEventMessage implements IMessage
{
    public short event, animId, optAnimId = -1;
    public float keyframeInfo = -1;
    public IAnimated animated;
    
    public long most, least;
    public int x, y, z;
    public boolean hasEntity;
    
    public IAnimatedEventMessage(){}
    
    public IAnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId){
        if (event != null)
            this.event = event.getId();
        this.animated = animated;
        this.animId = animId;
    }
    
    public IAnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId, float keyframeInfo){
        this(event, animated, animId);
        this.keyframeInfo = keyframeInfo;
    }
    
    public IAnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId, float keyframeInfo, short optAnimId){
        this(event, animated, animId, keyframeInfo);
        this.optAnimId = optAnimId;
    }
    
    public IAnimatedEventMessage(IAnimatedEventMessage eventObj){
        this(null, eventObj.animated, eventObj.animId, eventObj.keyframeInfo, eventObj.optAnimId);
        this.event = eventObj.event;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        short actualEvent = buf.readShort();
        if (actualEvent < 0 || actualEvent >= 2*EnumIAnimatedEvent.ID_COUNT){
            this.event = -1;
            CraftStudioApi.getLogger().error("Networking error : invalid packet.");
            return;
        } else if (actualEvent < EnumIAnimatedEvent.ID_COUNT){
            this.most = buf.readLong();
            this.least = buf.readLong();
            this.event = actualEvent;
            this.hasEntity = true;
        } else {
            int x = buf.readInt();
            int y = buf.readInt();
            int z = buf.readInt();
            this.event = (short) (actualEvent - EnumIAnimatedEvent.ID_COUNT);
            this.hasEntity = false;
        }
        this.animId = buf.readShort();
        if(this.event != 2)
            this.keyframeInfo = buf.readFloat();
        if(this.event > 2)
            this.optAnimId = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (this.event < 0 || this.event >= EnumIAnimatedEvent.ID_COUNT){
            buf.writeShort(-1);
            CraftStudioApi.getLogger().error("Unsuported event id " + this.event + " for network message.");
            return;
        }
        if (this.animated instanceof Entity){
            Entity e = (Entity) this.animated;
            buf.writeShort(this.event);
            UUID uuid = e.getUniqueID();
            buf.writeLong(uuid.getMostSignificantBits());
            buf.writeLong(uuid.getLeastSignificantBits());
        } else if (this.animated instanceof TileEntity){
            TileEntity te = (TileEntity) this.animated;
            buf.writeShort(this.event + EnumIAnimatedEvent.ID_COUNT);
            BlockPos pos = te.getPos();
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
        } else {
            buf.writeShort(-1);
            CraftStudioApi.getLogger().error("Unsuported class " + this.animated.getClass().getSimpleName() + " for network message.");
            CraftStudioApi.getLogger().error("You are trying to animate an other class than Entity or TileEntity.");
            return;
        }
        buf.writeShort(this.animId);
        if(this.event != EnumIAnimatedEvent.STOP_ANIM.getId())
            buf.writeFloat(this.keyframeInfo);
        if(this.event == EnumIAnimatedEvent.STOP_START_ANIM.getId())
            buf.writeShort(this.optAnimId);
    }
    
    public static Entity getEntityByUUID(World world, long most, long least) {
        UUID uuid = new UUID(most, least);
        for (Entity e : world.loadedEntityList)
            if (e.getPersistentID().equals(uuid))
                return e;
        return null;
    }
    
    public static TileEntity getTileEntityByPos(World world, int x, int y, int z){
        BlockPos pos = new BlockPos(x, y, z);
        return world.getTileEntity(pos);
    }
    
    public static abstract class IAnimatedEventHandler
    {
        public boolean onMessage(IAnimatedEventMessage message, MessageContext ctx) {
            if (message.hasEntity) {
                Entity e = getEntityByUUID(this.getWorld(ctx), message.most, message.least);
                if (!(e instanceof IAnimated)) {
                    CraftStudioApi.getLogger().error("Networking error : invalid entity.");
                    return false;
                }
                message.animated = (IAnimated) e;
            }
            else {
                TileEntity te = getTileEntityByPos(this.getWorld(ctx), message.x, message.y, message.z);
                if (!(te instanceof IAnimated)) {
                    CraftStudioApi.getLogger().error("Networking error : invalid tile entity.");
                    return false;
                }
                message.animated = (IAnimated) te;
            }
            return true;
        }
        
        protected abstract World getWorld(MessageContext ctx);
    }
}
