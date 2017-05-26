package fr.zeamateis.test.anim.common.animations;

import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.Channel;

import fr.zeamateis.test.anim.common.Mod_Test;

/**
 * The Animation handler class to register all animations you want
 */
public class AnimationHandlerTest extends AnimationHandler
{

    /** Register the animation(s) */
    public AnimationHandlerTest(IAnimated entity) {
        super(entity);
        addAnim(Mod_Test.MODID, "close_fan", "peacock", false);
        addAnim(Mod_Test.MODID, "open_fan", "testmod:close_fan");
        addAnim(Mod_Test.MODID, "fly", "dragon_brun", true);
        addAnim(Mod_Test.MODID, "idle", "dragon_brun", true);
        addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
        addAnim(Mod_Test.MODID, "rotation", "craftstudio_api_test2", true);
        addAnim(Mod_Test.MODID, "custom", "peacock", new TestCustomAnimation("customTesting"));
    }

    @Override
    public void startAnimationAt(String modid, String name, float startingFrame) {
        super.startAnimation(AnimationHandler.animChannels, modid, name, startingFrame);
    }

    @Override
    public void startAnimation(String modid, String name) {
        super.startAnimation(AnimationHandler.animChannels, modid, name, 0.0F);
    }

    @Override
    public void stopAnimation(String modid, String name) {
        super.stopAnimation(AnimationHandler.animChannels, modid, name);
    }

    @Override
    public void fireAnimationEventClientSide(Channel anim, float prevFrame, float frame) {}

    @Override
    public void fireAnimationEventServerSide(Channel anim, float prevFrame, float frame) {}

}