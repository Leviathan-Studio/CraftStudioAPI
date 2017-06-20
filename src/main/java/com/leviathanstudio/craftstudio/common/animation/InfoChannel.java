package com.leviathanstudio.craftstudio.common.animation;

/**
 * Class that should hold render informations about the animations.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
public abstract class InfoChannel extends Channel
{

    public InfoChannel(String channelName) {
        super(channelName);
        this.totalFrames = 0;
    }

    public InfoChannel(String channelName, float fps, boolean looped) {
        super(channelName, fps, looped);
    }

}
