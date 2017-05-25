package fr.zeamateis.test.anim.common.animations;

import java.util.HashMap;

import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.CSAnimChannel;
import com.leviathanstudio.craftstudio.common.animation.Channel;

import fr.zeamateis.test.anim.common.Mod_Test;
import net.minecraft.util.ResourceLocation;

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
		addAnim(Mod_Test.MODID, "close_fan", "peacock", false);
		addAnim(Mod_Test.MODID, "open_fan", AnimationHandlerTest.animChannels.get("testmod:close_fan").getInvertedChannel("open_fan"));
		addAnim(Mod_Test.MODID, "fly", "dragon_brun", true);
		addAnim(Mod_Test.MODID, "idle", "dragon_brun", true);
		addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
		addAnim(Mod_Test.MODID, "rotation", "craftstudio_api_test2", true);
	}
	
	private static void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped){
		ResourceLocation anim = new ResourceLocation(modid, animNameIn),
				model = new ResourceLocation(modid, modelNameIn);
		AnimationHandlerTest.animChannels.put(anim.toString(), new CSAnimChannel(anim, model, false));
	}
	
	private static void addAnim(String modid, String animNameIn, Channel channel){
		ResourceLocation anim = new ResourceLocation(modid, animNameIn);
		channel.name = anim.toString();
		AnimationHandlerTest.animChannels.put(anim.toString(), channel);
	}
	
	public boolean isAnimationActive(String modid, String name){
		return this.isAnimationActive(modid + ":" + name);
	}

	public void executeAnimation(String modid, String name, float startingFrame) {
        this.executeAnimation(modid + ":" + name, startingFrame);
    }
	
    @Override
    public void executeAnimation(String name, float startingFrame) {
        super.executeAnimation(AnimationHandlerTest.animChannels, name, startingFrame);
    }

    public void stopAnimation(String modid, String name) {
        this.stopAnimation(modid + ":" + name);
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