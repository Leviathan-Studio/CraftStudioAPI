package com.leviathanstudio.craftstudio.common.animation;

public class Channel
{
    public String  name;
    /** The speed of the whole channel (frames per second). */
    public float   fps;
    /** Number of the frames of this channel. */
    public int     totalFrames = -1;

    public boolean looped      = false;

    public Channel(String name) {
        this.name = name;
    }

    public Channel(String name, float fps, boolean looped) {
        this(name);
        this.fps = fps;
        this.looped = looped;
    }
}
