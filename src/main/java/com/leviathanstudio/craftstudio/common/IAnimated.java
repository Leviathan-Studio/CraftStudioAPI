package com.leviathanstudio.craftstudio.common;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;

public interface IAnimated
{

    /**
     * Getter to call custom {@link AnimationHandler} class, useful methods
     */
    public AnimationHandler getAnimationHandler();
}