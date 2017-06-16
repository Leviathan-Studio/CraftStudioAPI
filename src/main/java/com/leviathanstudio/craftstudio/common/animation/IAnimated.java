package com.leviathanstudio.craftstudio.common.animation;

import java.util.UUID;

import net.minecraft.util.ITickable;

/**
 * Implement object that must be animated with the api.
 * 
 * @author Timmypote
 * @author ZeAmateis
 */
public interface IAnimated
{
    /**
     * Getter to call custom {@link AnimationHandler} class to call useful
     * methods <br>
     *
     * @see AnimationHandler#addAnim addAnim()
     * @see AnimationHandler#startAnimation startAnimation()
     * @see AnimationHandler#stopAnimation stopAnimation()
     * @see AnimationHandler#isAnimationActive isAnimationActive()
     * 
     * @param <T>
     */
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler();
    
    public int getDimension();
    
    public double getX();
    
    public double getY();
    
    public double getZ();
    
    public boolean isWorldRemote();
}