package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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

}
