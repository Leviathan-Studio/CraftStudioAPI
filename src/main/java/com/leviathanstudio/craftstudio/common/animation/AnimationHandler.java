package com.leviathanstudio.craftstudio.common.animation;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.common.network.CSNetworkHelper;
import com.leviathanstudio.craftstudio.common.network.EnumIAnimatedEvent;
import com.leviathanstudio.craftstudio.common.network.AnimatedEventMessage;

import net.minecraft.util.ResourceLocation;

/**
 * An object that hold the informations about its animated objects and all their
 * animations. It also start/stop/update the animations.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 * @author ZeAmateis
 *
 * @param <T>
 *            The class of the animated object.
 */
public abstract class AnimationHandler<T extends IAnimated>
{
    /** List of the channels name to give them ids */
    protected List<String> channelIds = new ArrayList<>();

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

    /**
     * Start an animation on client side.
     * 
     * @param res
     *            The animation to start.
     * @param startingFrame
     *            The frame to start on.
     * @param animatedElement
     *            The animated object.
     */
    public void startAnimation(String res, float startingFrame, T animatedElement) {
        if (animatedElement.isWorldRemote())
            this.clientStartAnimation(res, startingFrame, animatedElement);
    }

    /**
     * Start an animation across the network. This is just a message send, avoid
     * using with "hold last keyframe" animations or long animations. In those
     * cases, prefer using {@link #startAnimation(String, float, IAnimated)} and
     * a custom network updating system.
     * 
     * @param res
     *            The animation to start.
     * @param startingFrame
     *            The frame to start on.
     * @param animatedElement
     *            The animated object.
     * @param clientSend
     *            If false, the packet will be send be the server only. If true,
     *            the packet will be send by the clients only.
     */
    public void networkStartAnimation(String res, float startingFrame, T animatedElement, boolean clientSend) {
        if (animatedElement.isWorldRemote() == clientSend) {
            this.serverInitAnimation(res, startingFrame, animatedElement);
            CSNetworkHelper.sendIAnimatedEvent(
                    new AnimatedEventMessage(EnumIAnimatedEvent.START_ANIM, animatedElement, this.getAnimIdFromName(res), startingFrame));
        }
    }

    /**
     * Start an animation on this client only. Do nothing on server.
     * 
     * @param res
     *            The animation to start.
     * @param startingFrame
     *            The frame to start on.
     * @param animatedElement
     *            The object that is animated.
     * @return True, if the animation is successfully started.
     */
    protected abstract boolean clientStartAnimation(String res, float startingFrame, T animatedElement);

    /**
     * Initialize an animation on the server and wait for a
     * {@link #serverStartAnimation(String, float, IAnimated)}.
     * 
     * @param res
     *            The animation to initialize.
     * @param startingFrame
     *            The frame the animation will be started on.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully initialized.
     */
    protected abstract boolean serverInitAnimation(String res, float startingFrame, T animatedElement);

    /**
     * Start an initialized animation on the server.
     * 
     * @param res
     *            The animation to start.
     * @param endingFrame
     *            The ending frame of the animation.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully started.
     */
    protected abstract boolean serverStartAnimation(String res, float endingFrame, T animatedElement);

    public void stopAnimation(String res, T animatedElement) {
        if (animatedElement.isWorldRemote())
            this.clientStopAnimation(res, animatedElement);
    }

    /**
     * Stop an animation across the network.
     * 
     * @param res
     *            The animation to stop.
     * @param animatedElement
     *            The animated object.
     * @param clientSend
     *            If false, the packet will be send be the server only. If true,
     *            the packet will be send by the clients only.
     */
    public void networkStopAnimation(String res, T animatedElement, boolean clientSend) {
        if (animatedElement.isWorldRemote() == clientSend) {
            this.serverStopAnimation(res, animatedElement);
            CSNetworkHelper.sendIAnimatedEvent(new AnimatedEventMessage(EnumIAnimatedEvent.STOP_ANIM, animatedElement, this.getAnimIdFromName(res)));
        }
    }

    /**
     * Stop an animation on this client only.
     * 
     * @param res
     *            The animation to stop.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully stopped.
     */
    protected abstract boolean clientStopAnimation(String res, T animatedElement);

    /**
     * Stop an animation on the server only.
     * 
     * @param res
     *            The animation to stop.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully stopped.
     */
    protected abstract boolean serverStopAnimation(String res, T animatedElement);

    public void stopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement) {
        if (animatedElement.isWorldRemote())
            this.clientStopStartAnimation(animToStop, animToStart, startingFrame, animatedElement);
    }

    /**
     * Stop an animation and directly start another across the network.
     * 
     * @param animToStop
     *            The animation to stop.
     * @param animToStart
     *            The animation to start.
     * @param startingFrame
     *            The frame to start the animation on.
     * @param animatedElement
     *            The animated object.
     * @param clientSend
     *            If false, the packet will be send be the server only. If true,
     *            the packet will be send by the clients only.
     */
    public void networkStopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement, boolean clientSend) {
        if (animatedElement.isWorldRemote() == clientSend) {
            this.serverStopStartAnimation(animToStop, animToStart, startingFrame, animatedElement);
            CSNetworkHelper.sendIAnimatedEvent(new AnimatedEventMessage(EnumIAnimatedEvent.STOP_START_ANIM, animatedElement,
                    this.getAnimIdFromName(animToStart), startingFrame, this.getAnimIdFromName(animToStop)));
        }
    }

    /**
     * Stop an animation and directly start another on this client. Same as
     * doing clientStopAnimation(); clientStartAnimation();
     * 
     * @param animToStop
     *            The animation to stop.
     * @param animToStart
     *            The animation to start.
     * @param startingFrame
     *            The frame to start the animation on.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully stopped and the other
     *         animation was successfully started.
     */
    protected boolean clientStopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement) {
        boolean stopSucces = this.clientStopAnimation(animToStop, animatedElement);
        return this.clientStartAnimation(animToStart, startingFrame, animatedElement) && stopSucces;
    }

    /**
     * Stop an animation and directly initialize another on the server.
     * 
     * @param animToStop
     *            The animation to stop.
     * @param animToStart
     *            The animation to initialize.
     * @param startingFrame
     *            The frame to start the animation on.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully stopped and the other
     *         animation was successfully initialized.
     */
    protected boolean serverStopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement) {
        boolean stopSucces = this.serverStopAnimation(animToStop, animatedElement);
        return this.serverInitAnimation(animToStart, startingFrame, animatedElement) && stopSucces;
    }

    /**
     * Update the animation. Should be done every ticks.
     * 
     * @param animatedElement
     *            The animated object.
     */
    public abstract void animationsUpdate(T animatedElement);

    /**
     * Check if an animation is active for an IAnimated.
     * 
     * @param name
     *            The name of the animation to check.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is running. False, otherwise.
     */
    public abstract boolean isAnimationActive(String name, T animatedElement);

    /**
     * Check if an hold animation is active for an IAnimated.
     *
     * @param name
     *            The animation you want to check.
     * @param animatedElement
     *            The object that is animated.
     * @return True, if the animation is running or holding on the last key,
     *         false otherwise.
     */
    public abstract boolean isHoldAnimationActive(String name, T animatedElement);

    /**
     * Update the tick timer of the animation or stop it if it should.
     * 
     * @param channel
     *            The animation to update.
     * @param animatedElement
     *            The animated object.
     * @return True, if the tick timer was updated. False, if the animation was
     *         stopped.
     */
    public abstract boolean canUpdateAnimation(Channel channel, T animatedElement);

    /**
     * Get an animation name from its id. Used for network messages.
     * 
     * @param id
     *            The animation id.
     * @return The name of the animation, null if the id doesn't exist.
     */
    public String getAnimNameFromId(short id) {
        return this.channelIds.get(id);
    }

    /**
     * Get the id of an animation from its name. Used for network messages.
     * 
     * @param name
     *            The animation name.
     * @return The id of the animation, -1 if the animation doesn't exist.
     */
    public short getAnimIdFromName(String name) {
        return (short) this.channelIds.indexOf(name);
    }

    /**
     * Method called when a network message is received in the client.
     * 
     * @param message
     *            The message.
     * @return True, if the message was correctly processed and a response
     *         should be send if it's needed.
     */
    public boolean onClientIAnimatedEvent(AnimatedEventMessage message) {
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

    /**
     * Methods called when a network message is received on the server.
     * 
     * @param message
     *            The message.
     * @return True, if the message was correctly processed and should be send
     *         to all the clients in range.
     */
    public static boolean onServerIAnimatedEvent(AnimatedEventMessage message) {
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

    /**
     * A class that hold ticks and frames informations about an animation.
     * 
     * @since 0.3.0
     * 
     * @author Timmypote
     */
    public static class AnimInfo
    {
        /** The previous time the animation was updated.. */
        public long  prevTime;
        /** The previous frame the animation was on. */
        public float currentFrame;

        /** Constructor */
        public AnimInfo(long prevTime, float currentFrame) {
            this.prevTime = prevTime;
            this.currentFrame = currentFrame;
        }
    }

    ///////////////////////////////////////
    // Overloaded methods for easier use //
    ///////////////////////////////////////

    /**
     * See {@link #startAnimation(String, String, float, IAnimated)}.<br>
     * startingFrame is set to 0.
     */
    public void startAnimation(String modid, String animationName, T animatedElement) {
        this.startAnimation(modid, animationName, 0.0F, animatedElement);
    }

    /**
     * Start an animation on this client only. Do nothing on server.
     * 
     * @param modid
     *            The ID of your mod.
     * @param animationName
     *            The name of your animation you want to start.
     * @param startingFrame
     *            The frame to start on.
     * @param animatedElement
     *            The object that is animated.
     * @return True, if the animation is successfully started.
     */
    public void startAnimation(String modid, String animationName, float startingFrame, T animatedElement) {
        this.startAnimation(modid + ":" + animationName, startingFrame, animatedElement);
    }

    /**
     * See
     * {@link #networkStartAnimation(String, String, IAnimated, boolean)}.<br>
     * clientSend is set to False.
     */
    public void networkStartAnimation(String modid, String animationName, T animatedElement) {
        this.networkStartAnimation(modid, animationName, 0.0F, animatedElement, false);
    }

    /**
     * See
     * {@link #networkStartAnimation(String, String, float, IAnimated, boolean)}.<br>
     * startingFrame is set to 0.
     */
    public void networkStartAnimation(String modid, String animationName, T animatedElement, boolean clientSend) {
        this.networkStartAnimation(modid, animationName, 0.0F, animatedElement, clientSend);
    }

    /**
     * Start an animation across the network. This is just a message send, avoid
     * using with "hold last keyframe" animations or long animations. In those
     * cases, prefer using
     * {@link #startAnimation(String, String, float, IAnimated)} and a custom
     * network updating system.
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     * @param startingFrame
     *            The frame you want your animation to start
     * @param animatedElement
     *            The IAnimated that is animated.
     * @param clientSend
     *            If false, the packet will be send be the server only. If true,
     *            the packet will be send by the clients only.
     */
    public void networkStartAnimation(String modid, String animationName, float startingFrame, T animatedElement, boolean clientSend) {
        this.networkStartAnimation(modid + ":" + animationName, startingFrame, animatedElement, clientSend);
    }

    /**
     * Stop an animation on this client only.
     * 
     * @param modid
     *            The ID of your mod.
     * @param animationName
     *            The name of your animation you want to stop.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully stopped.
     */
    public void stopAnimation(String modid, String animationName, T animatedElement) {
        this.stopAnimation(modid + ":" + animationName, animatedElement);
    }

    /**
     * See
     * {@link #networkStopAnimation(String, String, IAnimated, boolean)}.<br>
     * clientSend is set to False.
     */
    public void networkStopAnimation(String modid, String animationName, T animatedElement) {
        this.networkStopAnimation(modid + ":" + animationName, animatedElement, false);
    }

    /**
     * Stop an animation across the network.
     * 
     * @param modid
     *            The ID of your mod.
     * @param animationName
     *            The name of your animation you want to stop.
     * @param animatedElement
     *            The animated object.
     * @param clientSend
     *            If false, the packet will be send be the server only. If true,
     *            the packet will be send by the clients only.
     */
    public void networkStopAnimation(String modid, String animationName, T animatedElement, boolean clientSend) {
        this.networkStopAnimation(modid + ":" + animationName, animatedElement, clientSend);
    }

    /**
     * See
     * {@link #stopStartAnimation(String, String, String, float, IAnimated)}.<br>
     * startingFrame is set to 0.
     */
    public void stopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement);
    }

    /**
     * See
     * {@link #stopStartAnimation(String, String, String, String, float, IAnimated)}.<br>
     * modid1 and modid2 are set to the value of modid.
     * 
     * @param modid
     *            The ID of your mod.
     */
    public void stopStartAnimation(String modid, String animToStop, String animToStart, float startingFrame, T animatedElement) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, startingFrame, animatedElement);
    }

    /**
     * Stop an animation and directly start another on this client. Same as
     * doing clientStopAnimation(); clientStartAnimation();
     * 
     * @param modid1
     *            The ID of the mod of the animation you want to stop.
     * @param animToStop
     *            The name of the animation to stop.
     * @param modid2
     *            The ID of the mod of the animation you want to start.
     * @param animToStart
     *            The name of your animation you want to start.
     * @param startingFrame
     *            The frame to start the animation on.
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is successfully stopped and the other
     *         animation was successfully started.
     */
    public void stopStartAnimation(String modid1, String animToStop, String modid2, String animToStart, float startingFrame, T animatedElement) {
        this.stopStartAnimation(modid1 + ":" + animToStop, modid2 + ":" + animToStart, startingFrame, animatedElement);
    }

    /**
     * See
     * {@link #networkStopStartAnimation(String, String, String, IAnimated, boolean)}.<br>
     * clientSend is set to False.
     */
    public void networkStopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement) {
        this.networkStopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement, false);
    }

    /**
     * See
     * {@link #networkStopStartAnimation(String, String, String, float, IAnimated, boolean)}.<br>
     * startingFrame is set to 0.
     */
    public void networkStopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement, boolean clientSend) {
        this.networkStopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement, clientSend);
    }

    /**
     * See
     * {@link #networkStopStartAnimation(String, String, String, String, float, IAnimated, boolean)}.<br>
     * modid1 and modid2 are set to the value of modid.
     * 
     * @param modid
     *            The ID of your mod.
     */
    public void networkStopStartAnimation(String modid, String animToStop, String animToStart, float startingFrame, T animatedElement,
            boolean clientSend) {
        this.networkStopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, startingFrame, animatedElement, clientSend);
    }

    /**
     * Stop an animation and directly start another across the network.
     * 
     * @param modid1
     *            The ID of the mod of the animation you want to stop.
     * @param animToStop
     *            The name of the animation to stop.
     * @param modid2
     *            The ID of the mod of the animation you want to start.
     * @param animToStart
     *            The name of your animation you want to start.
     * @param startingFrame
     *            The frame to start the animation on.
     * @param animatedElement
     *            The animated object.
     * @param clientSend
     *            If false, the packet will be send be the server only. If true,
     *            the packet will be send by the clients only.
     */
    public void networkStopStartAnimation(String modid1, String animToStop, String modid2, String animToStart, float startingFrame, T animatedElement,
            boolean clientSend) {
        this.networkStopStartAnimation(modid1 + ":" + animToStop, modid2 + ":" + animToStart, startingFrame, animatedElement, clientSend);
    }

    /**
     * Check if an animation is active for an IAnimated.
     * 
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of the animation you want to check
     * @param animatedElement
     *            The animated object.
     * @return True, if the animation is running. False, otherwise.
     */
    public boolean isAnimationActive(String modid, String animationName, T animatedElement) {
        return this.isAnimationActive(modid + ":" + animationName, animatedElement);
    }
}