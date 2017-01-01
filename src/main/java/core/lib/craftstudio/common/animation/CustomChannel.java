package lib.craftstudio.common.animation;

import java.util.HashMap;

import lib.craftstudio.client.CSModelRenderer;
import lib.craftstudio.common.IAnimated;

public class CustomChannel extends Channel
{

    public CustomChannel(String channelName) {
        super(channelName);
        this.animationMode = EnumAnimationMode.CUSTOM;
    }

    @Override
    protected void initializeAllFrames() {}

    @Override
    public KeyFrame getPreviousRotationKeyFrameForBox(String boxName, float currentFrame) {
        return null;
    }

    @Override
    public KeyFrame getNextRotationKeyFrameForBox(String boxName, float currentFrame) {
        return null;
    }

    @Override
    public KeyFrame getPreviousTranslationKeyFrameForBox(String boxName, float currentFrame) {
        return null;
    }

    @Override
    public KeyFrame getNextTranslationKeyFrameForBox(String boxName, float currentFrame) {
        return null;
    }

    @Override
    public int getKeyFramePosition(KeyFrame keyFrame) {
        return -1;
    }

    /**
     * Write the actual behaviour of this custom animation here. It will called
     * every tick until the animation is active.
     */
    public void update(HashMap<String, CSModelRenderer> parts, IAnimated animated) {
        // This must be filled in the actual custom channels!
    }
}
