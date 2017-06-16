package com.leviathanstudio.craftstudio.common.animation;

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
