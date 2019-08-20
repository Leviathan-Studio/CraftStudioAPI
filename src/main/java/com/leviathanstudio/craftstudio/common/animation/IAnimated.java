package com.leviathanstudio.craftstudio.common.animation;

import net.minecraft.world.dimension.DimensionType;

/**
 * Implement object that must be animated with the api.
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
public interface IAnimated {
    /**
     * Getter to call custom {@link AnimationHandler} class to call useful
     * methods.
     *
     * @param <T> The animated object class'.
     * @see AnimationHandler#addAnim addAnim()
     * @see AnimationHandler#startAnimation startAnimation()
     * @see AnimationHandler#stopAnimation stopAnimation()
     * @see AnimationHandler#isAnimationActive isAnimationActive()
     */
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler();

    /**
     * Getter for the dimension.
     *
     * @return The dimension of the object as a int.
     */
    public DimensionType getDimension();

    /**
     * Getter for the x coordinate.
     *
     * @return The position on the x axis of the object.
     */
    public double getX();

    /**
     * Getter for the y coordinate.
     *
     * @return The position on the y axis of the object.
     */
    public double getY();

    /**
     * Getter for the z coordinate.
     *
     * @return The position on the z axis of the object.
     */
    public double getZ();

    /**
     * Check if the world of this object is remote or not.
     *
     * @return True, if the world is remote. False, otherwise.
     */
    public boolean isWorldRemote();
}