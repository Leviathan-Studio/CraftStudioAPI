package com.leviathanstudio.craftstudio.common.animation;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.client.animation.CustomChannel;

public abstract class AnimationHandler
{
    public static AnimTickHandler animTickHandler;
    /** Owner of this handler. */
    protected final IAnimated     animatedElement;

    public AnimationHandler(IAnimated animated) {
        if (ClientAnimationHandler.animTickHandler == null)
            ClientAnimationHandler.animTickHandler = new AnimTickHandler();
        ClientAnimationHandler.animTickHandler.addAnimated(animated);
        this.animatedElement = animated;
    }

    public abstract void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped);

    public abstract void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn);

    public abstract void addAnim(String modid, String animNameIn, String invertedChannelName);

    protected IAnimated getAnimated() {
        return this.animatedElement;
    }

    public abstract void startAnimation(String modid, String name, float startingFrame);

    public void startAnimation(String modid, String name) {
        this.startAnimation(modid, name, 0.0F);
    }

    public abstract void stopAnimation(String modid, String name);

    public abstract void animationsUpdate();

    public boolean isAnimationActive(String modid, String name) {
        return this.isAnimationActive(modid + ":" + name);
    }

    public abstract boolean isAnimationActive(String name);

    public abstract void fireAnimationEvent(Channel anim, float prevFrame, float frame);
    //
    // /** Get world object from an IAnimated */
    // public static boolean isWorldRemote(IAnimated animated) {
    // if (animated instanceof Entity)
    // return ((Entity) animated).getEntityWorld().isRemote;
    // else if (animated instanceof TileEntity)
    // return ((TileEntity) animated).getWorld().isRemote;
    // else
    // return false;
    // }
}
