package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

public class FireAnimationMessage extends CraftStudioBasePacket
{
    public FireAnimationMessage()
    {
    }

    private float startingKeyframe;

    public FireAnimationMessage(String animationNameIn, IAnimated animated, float startingKeyframeIn)
    {
        super(animationNameIn, animated);
        this.startingKeyframe = startingKeyframeIn;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        super.toBytes(buf);
        buf.writeFloat(this.startingKeyframe);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
        this.startingKeyframe = buf.readFloat();
    }

    public static class FireAnimationHandler implements IMessageHandler<FireAnimationMessage, RFireAnimationMessage>
    {
        @Override
        public RFireAnimationMessage onMessage(FireAnimationMessage message, MessageContext ctx)
        {
            Entity entity = message.getEntityByUUID(Minecraft.getMinecraft().world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated)
            {
                IAnimated animated = (IAnimated) entity;
                ((ClientAnimationHandler) animated.getAnimationHandler()).clientStartAnimation(message.animationName,
                        message.startingKeyframe);
                return new RFireAnimationMessage(message.animationName, animated,
                        ((ClientAnimationHandler) animated.getAnimationHandler()).getAnimChannels()
                                .get(message.animationName).totalFrames);
            }
            return null;
        }

    }

}
