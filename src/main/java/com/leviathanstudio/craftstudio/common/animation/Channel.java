package com.leviathanstudio.craftstudio.common.animation;

/**
 * Class that hold basic informations on animations channel.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 * @author ZeAmateis
 */
public class Channel
{
    /** The name of the animation channel */
    public String  name;
    /** The speed of the whole channel (frames per second). */
    public float   fps;
    /** Number of the frames of this channel. */
    public int     totalFrames = -1;
    /** Is the animation is animated or not */
    public boolean looped      = false;

    public Channel(String channelName) {
        this.name = channelName;
    }

    public Channel(String channelName, float fps, boolean looped) {
        this(channelName);
        this.fps = fps;
        this.looped = looped;
    }

}