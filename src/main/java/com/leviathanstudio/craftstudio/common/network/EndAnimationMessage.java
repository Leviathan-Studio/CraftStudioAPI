package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EndAnimationMessage extends CraftStudioBasePacket
{

    public EndAnimationMessage()
    {
    }

    public EndAnimationMessage(String animationNameIn, IAnimated animated)
    {
        super(animationNameIn, animated);
    }

    public static class EndAnimationHandler implements IMessageHandler<EndAnimationMessage, IMessage>
    {
        @Override
        public RFireAnimationMessage onMessage(EndAnimationMessage message, MessageContext ctx)
        {
            Entity entity = message.getEntityByUUID(Minecraft.getMinecraft().world.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated)
            {
                IAnimated animated = (IAnimated) entity;
                ((ClientAnimationHandler) animated.getAnimationHandler()).clientStopAnimation(animated.getAnimationHandler().getAnimNameFromId(message.animationId), animated);
            }
            return null;
        }

    }

}
