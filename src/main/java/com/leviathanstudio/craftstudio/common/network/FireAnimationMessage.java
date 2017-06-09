package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.client.animation.ClientChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FireAnimationMessage extends CraftStudioBasePacket
{
    public FireAnimationMessage() {}

    protected float startingKeyframe;

    public FireAnimationMessage(String animationNameIn, IAnimated animated, float startingKeyframeIn) {
        super(animationNameIn, animated);
        this.startingKeyframe = startingKeyframeIn;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeFloat(this.startingKeyframe);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.startingKeyframe = buf.readFloat();
    }

    public static class FireAnimationHandler implements IMessageHandler<FireAnimationMessage, RFireAnimationMessage>
    {
        @Override
        public RFireAnimationMessage onMessage(FireAnimationMessage message, MessageContext ctx) {
            Entity entity = message.getEntityByUUID(Minecraft.getMinecraft().world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated) {
                IAnimated animated = (IAnimated) entity;
                String animName = animated.getAnimationHandler().getAnimNameFromId(message.animationId);
                ((ClientAnimationHandler) animated.getAnimationHandler()).clientStartAnimation(animName, message.startingKeyframe, animated);
                Object infoChannel = ((ClientAnimationHandler) animated.getAnimationHandler()).getAnimChannels().get(animName);
                if (infoChannel instanceof ClientChannel)
                    return new RFireAnimationMessage(animName, animated, ((ClientChannel) infoChannel).totalFrames);
                return new RFireAnimationMessage(animName, animated, 0);
            }
            return null;
        }

    }

}
