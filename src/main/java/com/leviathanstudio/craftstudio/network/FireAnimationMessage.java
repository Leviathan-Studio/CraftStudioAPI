package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FireAnimationMessage implements IMessage
{
    public FireAnimationMessage() {}

    public FireAnimationMessage(String animationNameIn, IAnimated animated, float startingKeyframe) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class FireAnimationHandler implements IMessageHandler<FireAnimationMessage, RFireAnimationMessage>
    {

        @Override
        public RFireAnimationMessage onMessage(FireAnimationMessage message, MessageContext ctx) {
            return null;
        }

    }

}
