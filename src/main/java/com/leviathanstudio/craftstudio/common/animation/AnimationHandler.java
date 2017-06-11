package com.leviathanstudio.craftstudio.common.animation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public abstract class AnimationHandler<T extends IAnimated>
{
    public static AnimTickHandler animTickHandler;

    protected List<String>        channelIds = new ArrayList<>();

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
    public abstract void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped);

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
    public abstract void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn);

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
    public abstract void addAnim(String modid, String invertedAnimationName, String animationToInvert);

    public abstract void startAnimation(String res, float startingFrame, T animatedElement);

    /**
     * Start your animation
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     */
    public void startAnimation(String modid, String animationName, T animatedElement) {
        this.startAnimation(modid, animationName, 0.0F, animatedElement);
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
     *
     */
    public void startAnimation(String modid, String animationName, float startingFrame, T animatedElement) {
        this.startAnimation(modid + ":" + animationName, startingFrame, animatedElement);
    }

    public abstract void clientStartAnimation(String res, float startingFrame, T animatedElement);

    public void clientStartAnimation(String modid, String animationName, float startingFrame, T animatedElement) {
        this.clientStartAnimation(modid + ":" + animationName, startingFrame, animatedElement);
    }

    public void clientStartAnimation(String modid, String animationName, T animatedElement) {
        this.clientStartAnimation(modid, animationName, 0.0F, animatedElement);
    }

    public abstract void stopAnimation(String res, T animatedElement);

    /**
     * Stop your animation
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     */
    public void stopAnimation(String modid, String animationName, T animatedElement) {
        this.stopAnimation(modid + ":" + animationName, animatedElement);
    }

    public abstract void stopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement);

    public void stopStartAnimation(String modid1, String animToStop, String modid2, String animToStart, float startingFrame, T animatedElement) {
        this.stopStartAnimation(modid1 + ":" + animToStop, modid2 + ":" + animToStart, startingFrame, animatedElement);
    }

    public void stopStartAnimation(String modid, String animToStop, String animToStart, float startingFrame, T animatedElement) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, startingFrame, animatedElement);
    }

    public void stopStartAnimation(String modid, String animToStop, String animToStart, T animatedElement) {
        this.stopStartAnimation(modid + ":" + animToStop, modid + ":" + animToStart, 0.0F, animatedElement);
    }

    public abstract void animationsUpdate(T animatedElement);

    /**
     * Check if your animation is activated or not
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of the animation you want to check
     */
    public boolean isAnimationActive(String modid, String animationName, T animatedElement) {
        return this.isAnimationActive(modid + ":" + animationName, animatedElement);
    }

    public abstract boolean isAnimationActive(String name, T animatedElement);

    public abstract boolean canUpdateAnimation(Channel channel, T animatedElement);

    public void addAnimated(T animated) {
        if (AnimationHandler.animTickHandler == null)
            AnimationHandler.animTickHandler = new AnimTickHandler();
        AnimationHandler.animTickHandler.addAnimated(animated);
    }

    public String getAnimNameFromId(short id) {
        return this.channelIds.get(id);
    }

    public short getAnimIdFromName(String name) {
        return (short) this.channelIds.indexOf(name);
    }

    public void removeAnimated(T animated) {
        AnimationHandler.animTickHandler.removeAnimated(animated);
    }

    /** Get world object from an IAnimated */
    public static boolean isWorldRemote(IAnimated animated) {
        if (animated instanceof Entity)
            return ((Entity) animated).getEntityWorld().isRemote;
        else if (animated instanceof TileEntity)
            return ((TileEntity) animated).getWorld().isRemote;
        else
            return false;
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
}
