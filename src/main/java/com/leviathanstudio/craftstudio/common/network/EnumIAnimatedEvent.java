package com.leviathanstudio.craftstudio.common.network;

public enum EnumIAnimatedEvent {
    
    START_ANIM((short) 0), //Event used to start an animation on both side
    ANSWER_START_ANIM((short) 1), //Event used on the client to send the lastKeyframe information to the server
    STOP_ANIM((short) 2), //Event used to stop an animation on both side
    STOP_START_ANIM((short) 3); //Event used to stop an animation and directly start on both side
    
    private short id;
    public static final short ID_COUNT = 4;
    //The current id number send througth the network is
    //    the id, if for an entity,
    //    the id + idCount, if for a block
    
    private EnumIAnimatedEvent(short id){
        this.id = id;
    }
    
    public short getId(){
        return this.id;
    }
    
    public static EnumIAnimatedEvent getEvent(short id){
        switch (id){
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
