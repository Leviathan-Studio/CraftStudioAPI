package com.leviathanstudio.craftstudio.common.animation;

public abstract class InfoChannel extends Channel
{

    public InfoChannel(String channelName) {
        super(channelName);
    }

    public InfoChannel(String channelName, float fps, boolean looped) {
        super(channelName, fps, looped);
    }

}
