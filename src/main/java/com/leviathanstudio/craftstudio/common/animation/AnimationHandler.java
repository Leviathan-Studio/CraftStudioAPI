package com.leviathanstudio.craftstudio.common.animation;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.common.network.CSNetworkHelper;
import com.leviathanstudio.craftstudio.common.network.EnumIAnimatedEvent;
import com.leviathanstudio.craftstudio.common.network.IAnimatedEventMessage;

import net.minecraft.util.ResourceLocation;

public abstract class AnimationHandler<T extends IAnimated>
{
    protected List<String> channelIds = new ArrayList<>();

    public AnimationHandler() {}

    /**
     * Add animation to the IAnimated instance, entity or block
     *
     * @param modid
     *            The id of your mod
     * @param animNameIn
     *            The name of the animation you want to add
     * @param modelNameIn
     *            The name of the parent model of your animation
     * @param looped
     *            Is a looped or not animation
     */
    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.channelIds.add(anim.toString());
    }

    /**
     * Add animation to the IAnimated instance, entity or block
     *
     * @param modid
     *            The id of your mod
     * @param animNameIn
     *            The name of the animation you want to add
     * @param modelNameIn
     *            The name of the parent model of your animation
     * @param customChannelIn
     *            Your custom channel for hard coded animations, (ex: lookAt)
     */
    public void addAnim(String modid, String animNameIn, CustomChannel customChannelIn) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.channelIds.add(anim.toString());
    }

    /**
     * Add an inverted animation to the IAnimated instance, entity or block
     *
     * @param modid
     *            The id of your mod
     * @param invertedAnimationName
     *            The name of the inverted animation you want to add
     * @param animationToInvert
     *            The name the animation you want to invert
     */
    public void addAnim(String modid, String invertedAnimationName, String animationToInvert) {
        ResourceLocation anim = new ResourceLocation(modid, invertedAnimationName);
        this.channelIds.add(anim.toString());
    }

    public void startAnimation(String res, float startingFrame, T animatedElement, boolean clientSend) {
        if (animatedElement.isWorldRemote() == clientSend) {
            this.serverInitAnimation(res, startingFrame, animatedElement);
            CSNetworkHelper.sendIAnimatedEvent(
                    new IAnimatedEventMessage(EnumIAnimatedEvent.START_ANIM, animatedElement, this.getAnimIdFromName(res), startingFrame));
        }
    }

    public abstract boolean clientStartAnimation(String res, float startingFrame, T animatedElement);

    protected abstract boolean serverInitAnimation(String res, float startingFrame, T animatedElement);

    protected abstract boolean serverStartAnimation(String res, float endingFrame, T animatedElement);

    public void stopAnimation(String res, T animatedElement, boolean clientSend) {
        if (animatedElement.isWorldRemote() == clientSend) {
            this.serverStopAnimation(res, animatedElement);
            CSNetworkHelper.sendIAnimatedEvent(new IAnimatedEventMessage(EnumIAnimatedEvent.STOP_ANIM, animatedElement, this.getAnimIdFromName(res)));
        }
    }

    public abstract boolean clientStopAnimation(String res, T animatedElement);

    protected abstract boolean serverStopAnimation(String res, T animatedElement);

    public void stopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement, boolean clientSend) {
        if (animatedElement.isWorldRemote() == clientSend) {
            this.serverStopStartAnimation(animToStop, animToStart, startingFrame, animatedElement);
            CSNetworkHelper.sendIAnimatedEvent(new IAnimatedEventMessage(EnumIAnimatedEvent.STOP_START_ANIM, animatedElement,
                    this.getAnimIdFromName(animToStart), startingFrame, this.getAnimIdFromName(animToStop)));
        }
    }

    public boolean clientStopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement) {
        boolean stopSucces = this.clientStopAnimation(animToStop, animatedElement);
        return this.clientStartAnimation(animToStart, startingFrame, animatedElement) && stopSucces;
    }

    protected boolean serverStopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement) {
        boolean stopSucces = this.serverStopAnimation(animToStop, animatedElement);
        return this.serverInitAnimation(animToStart, startingFrame, animatedElement) && stopSucces;
    }

    public abstract void animationsUpdate(T animatedElement);

    public abstract boolean isAnimationActive(String name, T animatedElement);

    /**
     * Check if an hold animation is active.
     *
     * @param name
     *            The animation you want to check.
     * @param animatedElement
     *            The object that is animated.
     * @return True if the animation is active, false otherwise.
     */
    public abstract boolean isHoldAnimationActive(String name, T animatedElement);

    public abstract boolean canUpdateAnimation(Channel channel, T animatedElement);

    public String getAnimNameFromId(short id) {
        return this.channelIds.get(id);
    }

    public short getAnimIdFromName(String name) {
        return (short) this.channelIds.indexOf(name);
    }

    public boolean onClientIAnimatedEvent(IAnimatedEventMessage message) {
        AnimationHandler hand = message.animated.getAnimationHandler();
        switch (EnumIAnimatedEvent.getEvent(message.event)) {
            case START_ANIM:
                return hand.clientStartAnimation(hand.getAnimNameFromId(message.animId), message.keyframeInfo, message.animated);
            case STOP_ANIM:
                return hand.clientStopAnimation(hand.getAnimNameFromId(message.animId), message.animated);
            case STOP_START_ANIM:
                return hand.clientStopStartAnimation(hand.getAnimNameFromId(message.optAnimId), hand.getAnimNameFromId(message.animId),
                        message.keyframeInfo, message.animated);
            default:
                return false;
        }
    }

    public static boolean onServerIAnimatedEvent(IAnimatedEventMessage message) {
        AnimationHandler hand = message.animated.getAnimationHandler();
        switch (EnumIAnimatedEvent.getEvent(message.event)) {
            case START_ANIM:
                return hand.serverInitAnimation(hand.getAnimNameFromId(message.animId), message.keyframeInfo, message.animated);
            case ANSWER_START_ANIM:
                return hand.serverStartAnimation(hand.getAnimNameFromId(message.animId), message.keyframeInfo, message.animated);
            case STOP_ANIM:
                hand.serverStopAnimation(hand.getAnimNameFromId(message.animId), message.animated);
                return true;
            case STOP_START_ANIM:
                hand.serverStopStartAnimation(hand.getAnimNameFromId(message.optAnimId), hand.getAnimNameFromId(message.animId), message.keyframeInfo,
                        message.animated);
                return true;
            default:
                return false;
        }
    }

    public static class AnimInfo
    {
        public long  prevTime;
        public float currentFrame;

        public AnimInfo(long prevTime, float currentFrame) {
            this.prevTime = prevTime;
            this.currentFrame = currentFrame;
        }
    }

    ///////////////////////////////////////
    // Overloaded methods for easier use //
    ///////////////////////////////////////

    /**
     * Start your animation
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     * @param animatedElement
     *            The IAnimated that is animated.
     */
    public void startAnimation(String modid, String animationName, T animatedElement) {
        this.startAnimation(modid, animationName, 0.0F, animatedElement, false);
    }

    public void startAnimation(String modid, String animationName, T animatedElement, boolean clientSend) {
        this.startAnimation(modid, animationName, 0.0F, animatedElement, clientSend);
    }

    /**
     * Start your animation at a given time
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     * @param startingFrame
     *            The frame you want your animation to start
     * @param animatedElement
     *            The IAnimated that is animated.
     */
    public void startAnimation(String modid, String animationName, float startingFrame, T animatedElement, boolean clientSend) {
        this.startAnimation(modid + ":" + animationName, startingFrame, animatedElement, clientSend);
    }

    /**
     * Start your animation on the client only.
     *
     * @param modid
     *            The ID of your mod.
     * @param animationName
     *            The name of your animation you want to start.
     * @param animatedElement
     *            The IAnimated that is animated.
     */
    public void clientStartAnimation(String modid, String animationName, T animatedElement) {
        this.clientStartAnimation(modid, animationName, 0.0F, animatedElement);
    }

    public void clientStartAnimation(String modid, String animationName, float startingFrame, T animatedElement) {
        this.clientStartAnimation(modid + ":" + animationName, startingFrame, animatedElement);
    }

    /**
     * Stop your animation
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     * @param animatedElement
     *            The IAnimated that is animated.
     */
    public void stopAnimation(String modid, String animationName, T animatedElement) {
        this.stopAnimation(modid + ":" + animationName, animatedElement, false);
    }

    public void stopAnimation(String modid, String animationName, T animatedElement, boolean clientSend) {
        this.stopAnimation(modid + ":" + animationName, animatedElement, clientSend);
    }

    public void clientStopAnimation(String modid, String animationName, T animatedElement) {
        this.clientStopAnimation(modid + ":" + animationName, animatedElement);
    }

    /**
     * Stop an animation and directly start an other.
     *
     * @param modid
     *            The ID of your mod
     * @param animToStop
     *            The name of your animation you want to stop
     * @param animToStart
     *            The name of your animation you want to start
     * @param animatedElement
     *            The IAnimated that is animated.
     */
    public void stopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement, false);
    }

    public void stopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement, boolean clientSend) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement, clientSend);
    }

    public void stopStartAnimation(String modid, String animToStop, String animToStart, float startingFrame, T animatedElement, boolean clientSend) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, startingFrame, animatedElement, clientSend);
    }

    public void stopStartAnimation(String modid1, String animToStop, String modid2, String animToStart, float startingFrame, T animatedElement,
            boolean clientSend) {
        this.stopStartAnimation(modid1 + ":" + animToStop, modid2 + ":" + animToStart, startingFrame, animatedElement, clientSend);
    }

    public void clientStopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement) {
        this.clientStopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement);
    }

    public void clientStopStartAnimation(String modid, String animToStop, String animToStart, float startingFrame, T animatedElement) {
        this.clientStopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, startingFrame, animatedElement);
    }

    public void clientStopStartAnimation(String modid1, String animToStop, String modid2, String animToStart, float startingFrame,
            T animatedElement) {
        this.clientStopStartAnimation(modid1 + ":" + animToStop, modid2 + ":" + animToStart, startingFrame, animatedElement);
    }

    /**
     * Check if your animation is activated or not
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of the animation you want to check
     * @param animatedElement
     *            The IAnimated that is animated.
     */
    public boolean isAnimationActive(String modid, String animationName, T animatedElement) {
        return this.isAnimationActive(modid + ":" + animationName, animatedElement);
    }
}
