package com.leviathanstudio.craftstudio.common.animation;

import com.leviathanstudio.craftstudio.client.animation.CustomChannel;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public abstract class AnimationHandler
{
    public static AnimTickHandler animTickHandler;
    /** Owner of this handler. */
    protected final IAnimated     animatedElement;

    public AnimationHandler(IAnimated animated) {
        if (AnimationHandler.animTickHandler == null)
            AnimationHandler.animTickHandler = new AnimTickHandler();
        AnimationHandler.animTickHandler.addAnimated(animated);
        this.animatedElement = animated;
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

    protected IAnimated getAnimated() {
        return this.animatedElement;
    }

    public abstract void startAnimation(String res, float startingFrame);

    /**
     * Start your animation
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     */
    public void startAnimation(String modid, String animationName) {
        this.startAnimation(modid, animationName, 0.0F);
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
    public void startAnimation(String modid, String animationName, float startingFrame) {
        this.startAnimation(modid + ":" + animationName, startingFrame);
    }

    public abstract void stopAnimation(String res);

    /**
     * Stop your animation
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of your animation you want to start
     */
    public void stopAnimation(String modid, String animationName) {
        this.stopAnimation(modid + ":" + animationName);
    }

    public abstract void animationsUpdate();

    /**
     * Check if your animation is activated or not
     *
     * @param modid
     *            The ID of your mod
     * @param animationName
     *            The name of the animation you want to check
     */
    public boolean isAnimationActive(String modid, String animationName) {
        return this.isAnimationActive(modid + ":" + animationName);
    }

    public abstract boolean isAnimationActive(String name);

    public abstract boolean canUpdateAnimation(Channel channel);

    public abstract void fireAnimationEvent(Channel anim, float prevFrame, float frame);

    /** Get world object from an IAnimated */
    public static boolean isWorldRemote(IAnimated animated) {
        if (animated instanceof Entity)
            return ((Entity) animated).getEntityWorld().isRemote;
        else if (animated instanceof TileEntity)
            return ((TileEntity) animated).getWorld().isRemote;
        else
            return false;
    }
}
