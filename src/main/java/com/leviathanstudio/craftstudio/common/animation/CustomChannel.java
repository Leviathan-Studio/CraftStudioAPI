package com.leviathanstudio.craftstudio.common.animation;

import com.leviathanstudio.craftstudio.client.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.IAnimated;

public class CustomChannel extends Channel
{

    public CustomChannel(String channelName)
    {
        super(channelName);
        this.animationMode = EnumAnimationMode.CUSTOM;
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
    public void update(CSModelRenderer parts, IAnimated animated)
    {
        // This must be filled in the actual custom channels!
    }
}
