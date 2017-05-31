package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.server.animation.ServerAnimationHandler;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

public class RFireAnimationMessage extends CraftStudioBasePacket
{
    public RFireAnimationMessage()
    {
    }

    private float endingKeyframe;

    public RFireAnimationMessage(String animationNameIn, IAnimated animated, float endingKeyframeIn)
    {
        super(animationNameIn, animated);
        this.endingKeyframe = endingKeyframeIn;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        super.toBytes(buf);
        buf.writeFloat(this.endingKeyframe);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
        this.endingKeyframe = buf.readFloat();
    }

    public static class RFireAnimationHandler implements IMessageHandler<RFireAnimationMessage, IMessage>
    {
        @Override
        public IMessage onMessage(RFireAnimationMessage message, MessageContext ctx)
        {
            Entity entity = message.getEntityByUUID(ctx.getServerHandler().player.world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated)
            {
                IAnimated animated = (IAnimated) entity;
                ((ServerAnimationHandler) animated.getAnimationHandler()).serverStartAnimation(message.animationName,
                        message.endingKeyframe);
            }
            return null;
        }
    }

}
