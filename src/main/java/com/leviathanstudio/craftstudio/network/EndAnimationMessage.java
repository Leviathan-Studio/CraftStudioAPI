package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EndAnimationMessage implements IMessage
{

    public EndAnimationMessage() {}

    private String animationName;
    private String uuid;

    public EndAnimationMessage(String animationNameIn, IAnimated animated) {
        this.animationName = animationNameIn;
        this.uuid = animated.getUUID().toString();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        CraftStudioPacketHandler.writeStringtoBuffer(buf, this.animationName);
        CraftStudioPacketHandler.writeStringtoBuffer(buf, this.uuid);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.animationName = CraftStudioPacketHandler.readStringFromBuffer(buf);
        this.uuid = CraftStudioPacketHandler.readStringFromBuffer(buf);
    }

    public static class EndAnimationHandler implements IMessageHandler<EndAnimationMessage, IMessage>
    {
        @Override
        public RFireAnimationMessage onMessage(EndAnimationMessage message, MessageContext ctx) {
            Entity entity = CraftStudioPacketHandler.getEntityByUUID(Minecraft.getMinecraft().world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated) {
                IAnimated animated = (IAnimated) entity;
                ((ClientAnimationHandler) animated.getAnimationHandler()).clientStopAnimation(message.animationName);
            }
            return null;
        }

    }

}
