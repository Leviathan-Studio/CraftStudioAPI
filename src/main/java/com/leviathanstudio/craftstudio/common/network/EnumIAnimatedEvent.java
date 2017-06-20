package com.leviathanstudio.craftstudio.common.network;

/**
 * Enumeration of the different IAnimated event used to synchronize the
 * animations over the network.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
public enum EnumIAnimatedEvent {

    /**
     * Event used to start an animation for everyone around, can be used on
     * client or server.
     */
    START_ANIM((short) 0),
    /**
     * Event used on the client to send the lastKeyframe information to the
     * server.
     */
    ANSWER_START_ANIM((short) 1),
    /**
     * Event used to stop an animation for everyone around, can be used on
     * client or server.
     */
    STOP_ANIM((short) 2),
    /**
     * Event used to stop an animation and directly start an other for everyone
     * around, can be used on client or server.
     */
    STOP_START_ANIM((short) 3);

    /** The id of the event. */
    private short             id;
    /** The number of ids used, must be the biggest id + 1. */
    public static final short ID_COUNT = 4;
    // The actual id number send through the network is
    // the id, if the event is for an entity,
    // the id + idCount, if the event is for a tile entity

    /** Private constructor. */
    private EnumIAnimatedEvent(short id) {
        this.id = id;
    }

    /**
     * Getter of the id.
     * 
     * @return The event id.
     */
    public short getId() {
        return this.id;
    }

    /**
     * Get the event that correspond to the id given.
     * 
     * @param id
     *            The id
     * @return The event with this id, null if there is none.
     */
    public static EnumIAnimatedEvent getEvent(short id) {
        switch (id) {
            case 0:
                return START_ANIM;
            case 1:
                return ANSWER_START_ANIM;
            case 2:
                return STOP_ANIM;
            case 3:
                return STOP_START_ANIM;
        }
        return null;
    }
}
