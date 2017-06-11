package com.leviathanstudio.craftstudio.common.animation;

import java.util.UUID;

public interface IAnimated
{
    /**
     * Getter to call custom {@link AnimationHandler} class to call useful
     * methods <br>
     * <br>
     *
     * {@link AnimationHandler#addAnim addAnim()} <br>
     *
     * {@link AnimationHandler#startAnimation startAnimation()} <br>
     *
     * {@link AnimationHandler#stopAnimation stopAnimation()} <br>
     *
     * {@link AnimationHandler#isAnimationActive isAnimationActive()}
     * 
     * @param <T>
     */
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler();

    /**
     * Getter of Entity/Block for multiplayer sync compatibility
     */
    public UUID getUUID();
}