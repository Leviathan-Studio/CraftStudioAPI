package com.leviathanstudio.craftstudio.client.animation;

import com.leviathanstudio.craftstudio.common.animation.InfoChannel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A InfoChannel that hold keyframes to animate a model.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class ClientChannel extends InfoChannel {
    /**
     * KeyFrames. Key is the position of that keyFrame in the frames list.
     */
    private Map<Integer, KeyFrame> keyFrames = new HashMap<>();

    /**
     * How this animation should behave.
     */
    private EnumAnimationMode animationMode = EnumAnimationMode.LINEAR;

    /**
     * Create an empty ClientChannel, with {@code totalFrames} = 0.
     *
     * @param channelName The name of the animation channel.
     * @param initialize  If the keyFrames should be initialized in the constructor.
     */
    public ClientChannel(String channelName, boolean initialize) {
        super(channelName);
        if (initialize)
            this.initializeAllFrames();
    }

    /**
     * Create a ClientChannel.
     *
     * @param animationName The name of the animation channel.
     * @param fps           The number of frame per seconds.
     * @param totalFrames   The total number of frames.
     * @param animationMode The animation mode.
     * @param initialize    If the keyFrames should be initialized in the constructor.
     */
    public ClientChannel(String animationName, float fps, int totalFrames, EnumAnimationMode animationMode, boolean initialize) {
        this(animationName, initialize);
        this.fps = fps;
        this.totalFrames = totalFrames;
        this.setAnimationMode(animationMode);
        if (animationMode == EnumAnimationMode.LOOP)
            this.looped = true;
    }

    /**
     * Create all the frames and add them in the list in the correct order.
     */
    protected void initializeAllFrames() {
    }

    /**
     * Return the previous rotation KeyFrame before this frame that uses this
     * box, if it exists. If currentFrame is a keyFrame that uses this box, it
     * is returned.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The previous key frames.
     */
    public KeyFrame getPreviousRotationKeyFrameForBox(String boxName, float currentFrame) {
        int latestFramePosition = -1;
        int lastFramePosition = -1;
        KeyFrame latestKeyFrame = null;
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key <= currentFrame && key > latestFramePosition)
                if (value.useBoxInRotations(boxName)) {
                    latestFramePosition = key;
                    latestKeyFrame = value;
                }
            if (key > lastFramePosition && value.useBoxInRotations(boxName))
                lastFramePosition = key;
        }
        if (latestKeyFrame == null && lastFramePosition >= 0) {
            this.keyFrames.put(lastFramePosition - this.totalFrames, this.keyFrames.get(lastFramePosition).clone());
            latestKeyFrame = this.keyFrames.get(lastFramePosition - this.totalFrames);
        }

        return latestKeyFrame;
    }

    /**
     * Return the next rotation KeyFrame before this frame that uses this box,
     * if it exists. If currentFrame is a keyFrame that uses this box, it is NOT
     * considered.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The next key frames.
     */
    public KeyFrame getNextRotationKeyFrameForBox(String boxName, float currentFrame) {
        int nextFramePosition = -1;
        int firstFramePosition = this.totalFrames + 1;
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
            if (key < firstFramePosition && value.useBoxInRotations(boxName))
                firstFramePosition = key;
        }
        if (nextKeyFrame == null && firstFramePosition < this.totalFrames + 1) {
            this.keyFrames.put(this.totalFrames + firstFramePosition, this.keyFrames.get(firstFramePosition).clone());
            nextKeyFrame = this.keyFrames.get(this.totalFrames + firstFramePosition);
        }
        return nextKeyFrame;
    }

    /**
     * Return the previous translation KeyFrame before this frame that uses this
     * box, if it exists. If currentFrame is a keyFrame that uses this box, it
     * is returned.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The previous key frames.
     */
    public KeyFrame getPreviousTranslationKeyFrameForBox(String boxName, float currentFrame) {
        int latestFramePosition = -1;
        int lastFramePosition = -1;
        KeyFrame latestKeyFrame = null;
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key <= currentFrame && key > latestFramePosition)
                if (value.useBoxInTranslations(boxName)) {
                    latestFramePosition = key;
                    latestKeyFrame = value;
                }
            if (key > lastFramePosition && value.useBoxInTranslations(boxName))
                lastFramePosition = key;
        }
        if (latestKeyFrame == null && lastFramePosition >= 0) {
            this.keyFrames.put(lastFramePosition - this.totalFrames, this.keyFrames.get(lastFramePosition).clone());
            latestKeyFrame = this.keyFrames.get(lastFramePosition - this.totalFrames);
        }

        return latestKeyFrame;
    }

    /**
     * Return the next translation KeyFrame before this frame that uses this
     * box, if it exists. If currentFrame is a keyFrame that uses this box, it
     * is NOT considered.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The next key frames.
     */
    public KeyFrame getNextTranslationKeyFrameForBox(String boxName, float currentFrame) {
        int nextFramePosition = -1;
        int firstFramePosition = this.totalFrames + 1;
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
            if (key < firstFramePosition && value.useBoxInTranslations(boxName))
                firstFramePosition = key;
        }
        if (nextKeyFrame == null && firstFramePosition < this.totalFrames + 1) {
            this.keyFrames.put(this.totalFrames + firstFramePosition, this.keyFrames.get(firstFramePosition).clone());
            nextKeyFrame = this.keyFrames.get(this.totalFrames + firstFramePosition);
        }

        return nextKeyFrame;
    }

    /**
     * Return the previous offset KeyFrame before this frame that uses this box,
     * if it exists. If currentFrame is a keyFrame that uses this box, it is
     * returned.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The previous key frames.
     */
    public KeyFrame getPreviousOffsetKeyFrameForBox(String boxName, float currentFrame) {
        int latestFramePosition = -1;
        int lastFramePosition = -1;
        KeyFrame latestKeyFrame = null;
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key <= currentFrame && key > latestFramePosition)
                if (value.useBoxInOffsets(boxName)) {
                    latestFramePosition = key;
                    latestKeyFrame = value;
                }
            if (key > lastFramePosition && value.useBoxInOffsets(boxName))
                lastFramePosition = key;
        }
        if (latestKeyFrame == null && lastFramePosition >= 0) {
            this.keyFrames.put(lastFramePosition - this.totalFrames, this.keyFrames.get(lastFramePosition).clone());
            latestKeyFrame = this.keyFrames.get(lastFramePosition - this.totalFrames);
        }

        return latestKeyFrame;
    }

    /**
     * Return the next offset KeyFrame before this frame that uses this box, if
     * it exists. If currentFrame is a keyFrame that uses this box, it is NOT
     * considered.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The next key frames.
     */
    public KeyFrame getNextOffsetKeyFrameForBox(String boxName, float currentFrame) {
        int nextFramePosition = -1;
        int firstFramePosition = this.totalFrames + 1;
        KeyFrame nextKeyFrame = null;
        if (currentFrame > this.totalFrames)
            return this.keyFrames.get(this.totalFrames);
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key > currentFrame && (key < nextFramePosition || nextFramePosition == -1))
                if (value.useBoxInOffsets(boxName)) {
                    nextFramePosition = key;
                    nextKeyFrame = value;
                }
            if (key < firstFramePosition && value.useBoxInOffsets(boxName))
                firstFramePosition = key;
        }
        if (nextKeyFrame == null && firstFramePosition < this.totalFrames + 1) {
            this.keyFrames.put(this.totalFrames + firstFramePosition, this.keyFrames.get(firstFramePosition).clone());
            nextKeyFrame = this.keyFrames.get(this.totalFrames + firstFramePosition);
        }

        return nextKeyFrame;
    }

    /**
     * Return the previous stretching KeyFrame before this frame that uses this
     * box, if it exists. If curretFrame is a keyFrame that uses this box, it is
     * returned.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The previous key frames.
     */
    public KeyFrame getPreviousStretchKeyFrameForBox(String boxName, float currentFrame) {
        int latestFramePosition = -1;
        int lastFramePosition = -1;
        KeyFrame latestKeyFrame = null;
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key <= currentFrame && key > latestFramePosition)
                if (value.useBoxInStretchs(boxName)) {
                    latestFramePosition = key;
                    latestKeyFrame = value;
                }
            if (key > lastFramePosition && value.useBoxInStretchs(boxName))
                lastFramePosition = key;
        }
        if (latestKeyFrame == null && lastFramePosition >= 0) {
            this.keyFrames.put(lastFramePosition - this.totalFrames, this.keyFrames.get(lastFramePosition).clone());
            latestKeyFrame = this.keyFrames.get(lastFramePosition - this.totalFrames);
        }

        return latestKeyFrame;
    }

    /**
     * Return the next stretching KeyFrame before this frame that uses this box,
     * if it exists. If currentFrame is a keyFrame that uses this box, it is NOT
     * considered.
     *
     * @param boxName      The name of the box.
     * @param currentFrame The current frame.
     * @return The next key frames.
     */
    public KeyFrame getNextStretchKeyFrameForBox(String boxName, float currentFrame) {
        int nextFramePosition = -1;
        int firstFramePosition = this.totalFrames + 1;
        KeyFrame nextKeyFrame = null;
        if (currentFrame > this.totalFrames)
            return this.keyFrames.get(this.totalFrames);
        for (final Map.Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet()) {
            final Integer key = entry.getKey();
            final KeyFrame value = entry.getValue();

            if (key > currentFrame && (key < nextFramePosition || nextFramePosition == -1))
                if (value.useBoxInStretchs(boxName)) {
                    nextFramePosition = key;
                    nextKeyFrame = value;
                }
            if (key < firstFramePosition && value.useBoxInStretchs(boxName))
                firstFramePosition = key;
        }
        if (nextKeyFrame == null && firstFramePosition < this.totalFrames + 1) {
            this.keyFrames.put(this.totalFrames + firstFramePosition, this.keyFrames.get(firstFramePosition).clone());
            nextKeyFrame = this.keyFrames.get(this.totalFrames + firstFramePosition);
        }

        return nextKeyFrame;
    }

    /**
     * Get the position of the keyframe in this animation, if the keyframe
     * exists.
     *
     * @param keyFrame The keyframe.
     * @return The position of the keyframe, -1 if it doesn't exist.
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

    /**
     * Get inverted channel, for inverted animation.
     *
     * @param name The name of the new Channel.
     * @return The new Channel.
     */
    public ClientChannel getInvertedChannel(String name) {
        ClientChannel chan = new ClientChannel(name, this.fps, this.totalFrames, this.getAnimationMode(), true);
        for (Entry<Integer, KeyFrame> entry : this.keyFrames.entrySet())
            chan.keyFrames.put(this.totalFrames - entry.getKey(), entry.getValue().clone());
        return chan;
    }

    /**
     * Getter of keyFrames.
     *
     * @return the keyFrames.
     */
    public Map<Integer, KeyFrame> getKeyFrames() {
        return this.keyFrames;
    }

    /**
     * Setter of keyFrames.
     *
     * @param keyFrames the keyFrames to set.
     */
    public void setKeyFrames(Map<Integer, KeyFrame> keyFrames) {
        this.keyFrames = keyFrames;
    }

    /**
     * Getter of animationMode.
     *
     * @return the animationMode.
     */
    public EnumAnimationMode getAnimationMode() {
        return this.animationMode;
    }

    /**
     * Setter of animationMode.
     *
     * @param animationMode the animationMode to set.
     */
    public void setAnimationMode(EnumAnimationMode animationMode) {
        this.animationMode = animationMode;
    }
}