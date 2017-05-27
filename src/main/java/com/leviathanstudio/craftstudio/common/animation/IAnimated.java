package com.leviathanstudio.craftstudio.common.animation;

import java.util.UUID;

public interface IAnimated
{
    /**
     * Getter to call custom {@link AnimationHandler} class, useful methods
     */
    public AnimationHandler getAnimationHandler();

    public UUID getUUID();
}