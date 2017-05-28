package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.server.animation.ServerAnimationHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RFireAnimationMessage implements IMessage
{
    public RFireAnimationMessage() {}

    private String animationName;
    private String uuid;
    private float  endingKeyframe;

    public RFireAnimationMessage(String animationNameIn, IAnimated animated, float endingKeyframeIn) {
        this.animationName = animationNameIn;
        this.uuid = animated.getUUID().toString();
        this.endingKeyframe = endingKeyframeIn;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        CraftStudioPacketHandler.writeStringtoBuffer(buf, this.animationName);
        CraftStudioPacketHandler.writeStringtoBuffer(buf, this.uuid);
        buf.writeFloat(this.endingKeyframe);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.animationName = CraftStudioPacketHandler.readStringFromBuffer(buf);
        this.uuid = CraftStudioPacketHandler.readStringFromBuffer(buf);
        this.endingKeyframe = buf.readFloat();
    }

    public static class RFireAnimationHandler implements IMessageHandler<RFireAnimationMessage, IMessage>
    {
        @Override
        public IMessage onMessage(RFireAnimationMessage message, MessageContext ctx) {
            Entity entity = CraftStudioPacketHandler.getEntityByUUID(ctx.getServerHandler().player.world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated) {
                IAnimated animated = (IAnimated) entity;
                ((ServerAnimationHandler) animated.getAnimationHandler()).serverStartAnimation(message.animationName, message.endingKeyframe);
            }
            return null;
        }
    }

}
