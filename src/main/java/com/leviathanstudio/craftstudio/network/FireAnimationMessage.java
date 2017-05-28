package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FireAnimationMessage implements IMessage
{
    public FireAnimationMessage() {}

    private String animationName;
    private String uuid;
    private float  startingKeyframe;

    public FireAnimationMessage(String animationNameIn, IAnimated animated, float startingKeyframeIn) {
        this.animationName = animationNameIn;
        this.uuid = animated.getUUID().toString();
        this.startingKeyframe = startingKeyframeIn;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        CraftStudioPacketHandler.writeStringtoBuffer(buf, this.animationName);
        CraftStudioPacketHandler.writeStringtoBuffer(buf, this.uuid);
        buf.writeFloat(this.startingKeyframe);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.animationName = CraftStudioPacketHandler.readStringFromBuffer(buf);
        this.uuid = CraftStudioPacketHandler.readStringFromBuffer(buf);
        this.startingKeyframe = buf.readFloat();
    }

    public static class FireAnimationHandler implements IMessageHandler<FireAnimationMessage, RFireAnimationMessage>
    {
        @Override
        public RFireAnimationMessage onMessage(FireAnimationMessage message, MessageContext ctx) {
            Entity entity = CraftStudioPacketHandler.getEntityByUUID(Minecraft.getMinecraft().world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated) {
                IAnimated animated = (IAnimated) entity;
                ((ClientAnimationHandler) animated.getAnimationHandler()).clientStartAnimation(message.animationName, message.startingKeyframe);
                return new RFireAnimationMessage(message.animationName, animated,
                        (float) ((ClientAnimationHandler) animated.getAnimationHandler()).getAnimChannels().get(message.animationName).totalFrames);
            }
            return null;
        }

    }

}
