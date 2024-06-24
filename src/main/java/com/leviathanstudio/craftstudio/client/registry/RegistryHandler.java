package com.leviathanstudio.craftstudio.client.registry;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Class that handle registry for the CraftStudioApi.
 *
 * @author Timmypote
 * @since 1.0.0
 */
@OnlyIn(Dist.CLIENT)
public class RegistryHandler {
    public static SimpleRegistry<CSReadedModel> modelRegistry;
    public static SimpleRegistry<CSReadedAnim> animationRegistry;

    static {
    	modelRegistry = new SimpleRegistry<>();
        animationRegistry = new SimpleRegistry<>();
    }

    /**
     * Register a CSReadedModel.
     *
     * @param res   The name of the model.
     * @param model The model.
     */
    public static void register(ResourceLocation res, CSReadedModel model) {
        modelRegistry.register(res, model);
    }

    /**
     * Register a CSReadedAnim.
     *
     * @param res  The name of the animation.
     * @param anim The animation.
     */
    public static void register(ResourceLocation res, CSReadedAnim anim) {
        animationRegistry.register(res, anim);
    }
}
