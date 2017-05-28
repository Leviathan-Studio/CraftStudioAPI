package com.leviathanstudio.craftstudio.client.animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.leviathanstudio.craftstudio.common.animation.Channel;

public class ClientChannel extends Channel
{
    /** KeyFrames. Key is the position of that keyFrame in the frames list. */
    private Map<Integer, KeyFrame> keyFrames     = new HashMap<>();
    /** How this animation should behave: 0 = Normal; 1 = Loop; 2 = Cycle. */
    private EnumAnimationMode      animationMode = EnumAnimationMode.LINEAR;

    public ClientChannel(String channelName, boolean initialize) {
        super(channelName);
        this.totalFrames = 0;
        if (initialize)
            this.initializeAllFrames();
    }

    public ClientChannel(String animationName, float fps, int totalFrames, EnumAnimationMode animationMode, boolean initialize) {
        this(animationName, initialize);
        this.fps = fps;
        this.totalFrames = totalFrames;
        this.animationMode = animationMode;
        if (animationMode == EnumAnimationMode.LOOP)
            this.looped = true;
    }

    /** Create all the frames and add them in the list in the correct order. */
    protected void initializeAllFrames() {}

    /**
     * Return the previous rotation KeyFrame before this frame that uses this
     * box, if it exists. If currentFrame is a keyFrame that uses this box, it
     * is returned.
     */
    public KeyFrame getPreviousRotationKeyFrameForBox(String boxName, float currentFrame) {
        int latestFramePosition = -1;
        KeyFrame latestKeyFrame = null;
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key <= currentFrame && key > latestFramePosition)
                if (value.useBoxInRotations(boxName)) {
                    latestFramePosition = key;
                    latestKeyFrame = value;
                }
        }

        return latestKeyFrame;
    }

    /**
     * Return the next rotation KeyFrame before this frame that uses this box,
     * if it exists. If currentFrame is a keyFrame that uses this box, it is NOT
     * considered.
     */
    public KeyFrame getNextRotationKeyFrameForBox(String boxName, float currentFrame) {
        int nextFramePosition = -1;
        KeyFrame nextKeyFrame = null;
        if (currentFrame > this.totalFrames)
            return this.keyFrames.get(this.totalFrames);
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key > currentFrame && (key < nextFramePosition || nextFramePosition == -1))
                if (value.useBoxInRotations(boxName)) {
                    nextFramePosition = key;
                    nextKeyFrame = value;
                }
        }
        return nextKeyFrame;
    }

    /**
     * Return the previous translation KeyFrame before this frame that uses this
     * box, if it exists. If curretFrame is a keyFrame that uses this box, it is
     * returned.
     */
    public KeyFrame getPreviousTranslationKeyFrameForBox(String boxName, float currentFrame) {
        int latestFramePosition = -1;
        KeyFrame latestKeyFrame = null;
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key <= currentFrame && key > latestFramePosition)
                if (value.useBoxInTranslations(boxName)) {
                    latestFramePosition = key;
                    latestKeyFrame = value;
                }
        }

        return latestKeyFrame;
    }

    /**
     * Return the next translation KeyFrame before this frame that uses this
     * box, if it exists. If currentFrame is a keyFrame that uses this box, it
     * is NOT considered.
     */
    public KeyFrame getNextTranslationKeyFrameForBox(String boxName, float currentFrame) {
        int nextFramePosition = -1;
        KeyFrame nextKeyFrame = null;
        if (currentFrame > this.totalFrames)
            return this.keyFrames.get(this.totalFrames);
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key > currentFrame && (key < nextFramePosition || nextFramePosition == -1))
                if (value.useBoxInTranslations(boxName)) {
                    nextFramePosition = key;
                    nextKeyFrame = value;
                }
        }

        return nextKeyFrame;
    }

    /**
     * Get the position of the keyframe in this animation, if the keyframe
     * exists.
     */
    public int getKeyFramePosition(KeyFrame keyFrame) {
        if (keyFrame != null)
            for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
                final Integer key = entry.getKey();
                final KeyFrame keyframe = entry.getValue();

                if (keyframe == keyFrame)
                    return key;
            }
        return -1;
    }

    /** Check if an animation should stop, restart or whatever. */
    public static boolean shouldAnimationStop() {
        return false;
    }

    /** Get inverted channel, for inverted animation */
    public ClientChannel getInvertedChannel(String name) {
        ClientChannel chan = new ClientChannel(name, this.fps, this.totalFrames, this.animationMode, true);
        for (Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet())
            chan.keyFrames.put(this.totalFrames - entry.getKey(), entry.getValue().clone());
        return chan;
    }

    public Map<Integer, KeyFrame> getKeyFrames() {
        return this.keyFrames;
    }

    public EnumAnimationMode getAnimationMode() {
        return this.animationMode;
    }

    public void setAnimationMode(EnumAnimationMode animationModeIn) {
        this.animationMode = animationModeIn;
    }

    public enum EnumAnimationMode {
        LINEAR, LOOP, CUSTOM;
    }

}