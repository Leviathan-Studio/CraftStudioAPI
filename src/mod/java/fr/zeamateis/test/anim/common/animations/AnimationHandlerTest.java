package fr.zeamateis.test.anim.common.animations;

import java.util.HashMap;

import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.CSAnimChannel;
import com.leviathanstudio.craftstudio.common.animation.Channel;

/**
 * The Animation handler class to register all animations you want in static
 * method
 */
public class AnimationHandlerTest extends AnimationHandler
{
    /** Map with all the animations. */
    public static HashMap<String, Channel> animChannels = new HashMap<>();
    
    /** Register the animation(s) */
    static
    {
//        AnimationHandlerTest.animChannels.put("block", new ChannelBlockAnimation("block", 30.0F));
//        AnimationHandlerTest.animChannels.put("idle", new ChannelIdleAnimation("idle", 30.0F));
    	
    }
	
	public AnimationHandlerTest(IAnimated entity)
    {
        super(entity);
        AnimationHandlerTest.animChannels.put("Idle", new CSAnimChannel("Idle", "Dragon_Brun", 60.0F, true));
        AnimationHandlerTest.animChannels.put("Fly", new CSAnimChannel("Fly", "Dragon_Brun", 60.0F, true));
    }

    @Override
    public void executeAnimation(String name, float startingFrame)
    {
    	super.executeAnimation(AnimationHandlerTest.animChannels, name, startingFrame);
    }

    @Override
    public void stopAnimation(String name)
    {
        super.stopAnimation(AnimationHandlerTest.animChannels, name);
    }

    @Override
    public void fireAnimationEventClientSide(Channel anim, float prevFrame, float frame)
    {
    }

    @Override
    public void fireAnimationEventServerSide(Channel anim, float prevFrame, float frame)
    {
    }

}