package com.leviathanstudio.craftstudio.client.animation;

import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CustomChannel extends ClientChannel
{

    public CustomChannel(String channelName) {
        super(channelName, true);
        this.setAnimationMode(EnumAnimationMode.CUSTOM);
    }

    /**
     * Write the actual behaviour of this custom animation here. It will called
     * every tick until the animation is active.
     */
    @SideOnly(Side.CLIENT)
    public abstract void update(CSModelRenderer parts, IAnimated animated);
}
