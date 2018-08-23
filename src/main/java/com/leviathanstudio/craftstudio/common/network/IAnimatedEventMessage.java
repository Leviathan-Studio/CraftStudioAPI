package com.leviathanstudio.craftstudio.common.network;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Base class for
 * {@link com.leviathanstudio.craftstudio.common.animation.IAnimated IAnimated}
 * event messages.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
public class IAnimatedEventMessage implements IMessage
{
    /** The id of the event. See for more info {@link EnumIAnimatedEvent}. */
    public short     event;
    /** The id of the primary animation. */
    public short     animId;
    /** The id of the secondary animation, used for stopStart message. */
    public short     optAnimId    = -1;
    /** A float used to transmit keyframe related informations. */
    public float     keyframeInfo = -1;
    /** The object that is animated */
    public IAnimated animated;
    /** Variable that transmit part of the UUID of an Entity. */
    public long      most, least;
    /** Variable that transmit the position of a TileEntity. */
    public int       x, y, z;
    /** True, if on message receiving the animated object is an entity. */
    public boolean   hasEntity;

    /** Simple empty constructor for packets system. */
    public IAnimatedEventMessage() {}

    /** Constructor */
    public IAnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId) {
        if (event != null)
            this.event = event.getId();
        this.animated = animated;
        this.animId = animId;
    }

    /** Constructor */
    public IAnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId, float keyframeInfo) {
        this(event, animated, animId);
        this.keyframeInfo = keyframeInfo;
    }

    /** Constructor */
    public IAnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId, float keyframeInfo, short optAnimId) {
        this(event, animated, animId, keyframeInfo);
        this.optAnimId = optAnimId;
    }

    /** Constructor */
    public IAnimatedEventMessage(IAnimatedEventMessage eventObj) {
        this(null, eventObj.animated, eventObj.animId, eventObj.keyframeInfo, eventObj.optAnimId);
        this.event = eventObj.event;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        short actualEvent = buf.readShort();
        if (actualEvent < 0 || actualEvent >= 2 * EnumIAnimatedEvent.ID_COUNT) {
            this.event = -1;
            CraftStudioApi.getLogger().error("Networking error : invalid packet.");
            return;
        }
        else if (actualEvent < EnumIAnimatedEvent.ID_COUNT) {
            this.most = buf.readLong();
            this.least = buf.readLong();
            this.event = actualEvent;
            this.hasEntity = true;
        }
        else {
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.z = buf.readInt();
            this.event = (short) (actualEvent - EnumIAnimatedEvent.ID_COUNT);
            this.hasEntity = false;
        }
        this.animId = buf.readShort();
        if (this.event != 2)
            this.keyframeInfo = buf.readFloat();
        if (this.event > 2)
            this.optAnimId = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (this.event < 0 || this.event >= EnumIAnimatedEvent.ID_COUNT) {
            buf.writeShort(-1);
            CraftStudioApi.getLogger().error("Unsupported event id " + this.event + " for network message.");
            return;
        }
        if (this.animated instanceof Entity) {
            Entity e = (Entity) this.animated;
            buf.writeShort(this.event);
            UUID uuid = e.getUniqueID();
            buf.writeLong(uuid.getMostSignificantBits());
            buf.writeLong(uuid.getLeastSignificantBits());
        }
        else if (this.animated instanceof TileEntity) {
            TileEntity te = (TileEntity) this.animated;
            buf.writeShort(this.event + EnumIAnimatedEvent.ID_COUNT);
            BlockPos pos = te.getPos();
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
        }
        else {
            buf.writeShort(-1);
            CraftStudioApi.getLogger().error("Unsupported class " + this.animated.getClass().getSimpleName() + " for network message.");
            CraftStudioApi.getLogger().error("You are trying to animate an other class than Entity or TileEntity.");
            return;
        }
        buf.writeShort(this.animId);
        if (this.event != EnumIAnimatedEvent.STOP_ANIM.getId())
            buf.writeFloat(this.keyframeInfo);
        if (this.event == EnumIAnimatedEvent.STOP_START_ANIM.getId())
            buf.writeShort(this.optAnimId);
    }

    /**
     * Base class for the messages handler.
     * 
     * @since 0.3.0
     * 
     * @author Timmypote
     */
    public static abstract class IAnimatedEventHandler
    {

        /**
         * Extract the Entity or TileEntity when a message is received.
         * 
         * @param message
         *            The message received.
         * @param ctx
         *            The context of the message.
         * @return True, if the Entity/TileEntity was successfully extracted.
         *         False, otherwise.
         */
        public boolean onMessage(IAnimatedEventMessage message, MessageContext ctx) {
            if (message.hasEntity) {
                Entity e = this.getEntityByUUID(ctx, message.most, message.least);
                if (!(e instanceof IAnimated)) {
                    CraftStudioApi.getLogger().debug("Networking error : invalid entity.");
                    return false;
                }
                message.animated = (IAnimated) e;
            }
            else {
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
         * @param ctx
         *            The context of the message received.
         * @param most
         *            The most significants bits of the UUID.
         * @param least
         *            The least significants bits of the UUID.
         * @return The Entity, null if it couldn't be found.
         */
        public abstract Entity getEntityByUUID(MessageContext ctx, long most, long least);

        /**
         * Get a TileEntity by its position.
         * 
         * @param ctx
         *            The context of the message received.
         * @param x
         *            The position on the x axis.
         * @param y
         *            The position on the y axis.
         * @param z
         *            The position on the z axis.
         * @return The TileEntity, null if it couldn't be found.
         */
        public abstract TileEntity getTileEntityByPos(MessageContext ctx, int x, int y, int z);
    }
}
