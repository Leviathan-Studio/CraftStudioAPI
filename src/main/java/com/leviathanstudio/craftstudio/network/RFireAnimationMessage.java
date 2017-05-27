package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RFireAnimationMessage implements IMessage
{
    public RFireAnimationMessage(String animationNameIn, IAnimated animated) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // TODO Auto-generated method stub

    }

    @Override
    public void toBytes(ByteBuf buf) {
        // TODO Auto-generated method stub

    }

    public static class RFireAnimationHandler implements IMessageHandler<RFireAnimationMessage, IMessage>
    {
        @Override
        public IMessage onMessage(RFireAnimationMessage message, MessageContext ctx) {
            return null;
        }
    }

}
