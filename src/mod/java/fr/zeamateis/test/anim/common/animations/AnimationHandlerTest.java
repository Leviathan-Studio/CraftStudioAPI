package fr.zeamateis.test.anim.common.animations;

import java.util.HashMap;

import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.CSAnimChannel;
import com.leviathanstudio.craftstudio.common.animation.Channel;

/**
 * The Animation handler class to register all animations you want
 */
public class AnimationHandlerTest extends AnimationHandler
{
    /** Map with all the animations. */
    public static HashMap<String, Channel> animChannels = new HashMap<>();

	/** Register the animation(s) */
	public AnimationHandlerTest(IAnimated entity) {
		super(entity);
		AnimationHandlerTest.animChannels.put("close_fan", new CSAnimChannel("close_fan", "peacock", false));
		AnimationHandlerTest.animChannels.put("open_fan", AnimationHandlerTest.animChannels.get("close_fan").getInvertedChannel("open_fan"));
		AnimationHandlerTest.animChannels.put("fly", new CSAnimChannel("fly", "dragon_brun", true));
		AnimationHandlerTest.animChannels.put("idle", new CSAnimChannel("idle", "dragon_brun", true));
		AnimationHandlerTest.animChannels.put("position",
			new CSAnimChannel("position", "craftstudio_api_test", true));
		AnimationHandlerTest.animChannels.put("rotation",
			new CSAnimChannel("rotation", "craftstudio_api_test2", true));
	}

    @Override
    public void executeAnimation(String name, float startingFrame) {
        super.executeAnimation(AnimationHandlerTest.animChannels, name, startingFrame);
    }

    @Override
    public void stopAnimation(String name) {
        super.stopAnimation(AnimationHandlerTest.animChannels, name);
    }

    @Override
    public void fireAnimationEventClientSide(Channel anim, float prevFrame, float frame) {}

    @Override
    public void fireAnimationEventServerSide(Channel anim, float prevFrame, float frame) {}

}