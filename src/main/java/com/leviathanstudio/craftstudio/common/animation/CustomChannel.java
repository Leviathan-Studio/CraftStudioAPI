package com.leviathanstudio.craftstudio.common.animation;

import com.leviathanstudio.craftstudio.client.animation.ClientChannel;
import com.leviathanstudio.craftstudio.client.animation.EnumAnimationMode;
import com.leviathanstudio.craftstudio.client.animation.KeyFrame;
import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CustomChannel extends InfoChannel
{

    public CustomChannel(String channelName)
    {
        super(channelName);
    }

    /**
     * Write the actual behaviour of this custom animation here. It will called
     * every tick until the animation is active.
     */
    @SideOnly(Side.CLIENT)
    public abstract void update(CSModelRenderer parts, IAnimated animated);
}
