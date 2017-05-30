package com.leviathanstudio.craftstudio.client.animation;

import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CustomChannel extends ClientChannel
{

    public CustomChannel(String channelName)
    {
        super(channelName, true);
        this.setAnimationMode(EnumAnimationMode.CUSTOM);
    }

    @Override
    protected void initializeAllFrames()
    {
    }

    @Override
    public KeyFrame getPreviousRotationKeyFrameForBox(String boxName, float currentFrame)
    {
        return null;
    }

    @Override
    public KeyFrame getNextRotationKeyFrameForBox(String boxName, float currentFrame)
    {
        return null;
    }

    @Override
    public KeyFrame getPreviousTranslationKeyFrameForBox(String boxName, float currentFrame)
    {
        return null;
    }

    @Override
    public KeyFrame getNextTranslationKeyFrameForBox(String boxName, float currentFrame)
    {
        return null;
    }

    @Override
    public int getKeyFramePosition(KeyFrame keyFrame)
    {
        return -1;
    }

    /**
     * Write the actual behaviour of this custom animation here. It will called
     * every tick until the animation is active.
     */
    @SideOnly(Side.CLIENT)
    public abstract void update(CSModelRenderer parts, IAnimated animated);
}
