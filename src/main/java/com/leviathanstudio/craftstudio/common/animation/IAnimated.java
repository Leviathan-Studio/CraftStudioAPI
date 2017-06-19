package com.leviathanstudio.craftstudio.common.animation;

/**
 * Implement object that must be animated with the api.
 *
 * @since 0.3.0
 *
 * @author Timmypote
 * @author ZeAmateis
 */
public interface IAnimated
{
    /**
     * Getter to call custom {@link AnimationHandler} class to call useful
     * methods.
     *
     * @see AnimationHandler#addAnim addAnim()
     * @see AnimationHandler#startAnimation startAnimation()
     * @see AnimationHandler#stopAnimation stopAnimation()
     * @see AnimationHandler#isAnimationActive isAnimationActive()
     *
     * @param <T>
     *            The animated object class'.
     */
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler();

    /**
     * Getter for the dimension.
     * 
     * @return The dimension of the object as a int.
     */
    public int getDimension();

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