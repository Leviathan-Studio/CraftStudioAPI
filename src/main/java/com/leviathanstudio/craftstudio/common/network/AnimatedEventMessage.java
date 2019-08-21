package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;

/**
 * Base class for
 * {@link com.leviathanstudio.craftstudio.common.animation.IAnimated IAnimated}
 * event messages.
 *
 * @author Timmypote
 * @since 0.3.0
 */
public class AnimatedEventMessage {
    /**
     * The id of the event. See for more info {@link EnumIAnimatedEvent}.
     */
    public short event;
    /**
     * The id of the primary animation.
     */
    public short animId;
    /**
     * The id of the secondary animation, used for stopStart message.
     */
    public short optAnimId = -1;
    /**
     * A float used to transmit keyframe related informations.
     */
    public float keyframeInfo = -1;
    /**
     * The object that is animated
     */
    public IAnimated animated;
    /**
     * Variable that transmit part of the UUID of an Entity.
     */
    public long most, least;
    /**
     * Variable that transmit the position of a TileEntity.
     */
    public int x, y, z;
    /**
     * True, if on message receiving the animated object is an entity.
     */
    public boolean hasEntity;

    /**
     * Simple empty constructor for packets system.
     */
    public AnimatedEventMessage() {
    }

    /**
     * Constructor
     */
    public AnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId) {
        if (event != null)
            this.event = event.getId();
        this.animated = animated;
        this.animId = animId;
    }

    /**
     * Constructor
     */
    public AnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId, float keyframeInfo) {
        this(event, animated, animId);
        this.keyframeInfo = keyframeInfo;
    }

    /**
     * Constructor
     */
    public AnimatedEventMessage(EnumIAnimatedEvent event, IAnimated animated, short animId, float keyframeInfo, short optAnimId) {
        this(event, animated, animId, keyframeInfo);
        this.optAnimId = optAnimId;
    }

    /**
     * Constructor
     */
    public AnimatedEventMessage(AnimatedEventMessage eventObj) {
        this(null, eventObj.animated, eventObj.animId, eventObj.keyframeInfo, eventObj.optAnimId);
        this.event = eventObj.event;
    }
}
