package com.leviathanstudio.craftstudio.client.registry;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that handle registry for the CraftStudioApi.
 * 
 * @since 1.0.0
 * 
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class RegistryHandler
{
    public static final RegistrySimple<ResourceLocation, CSReadedModel> MODEL_REGISTRY = new RegistrySimple<>();
    public static final RegistrySimple<ResourceLocation, CSReadedAnim>  ANIMATION_REGISTRY = new RegistrySimple<>();


    /**
     * Register a CSReadedModel.
     * 
     * @param res
     *            The name of the model.
     * @param model
     *            The model.
     */
    public static void register(ResourceLocation res, CSReadedModel model) {
        MODEL_REGISTRY.putObject(res, model);
    }

    /**
     * Register a CSReadedAnim.
     * 
     * @param res
     *            The name of the animation.
     * @param anim
     *            The animation.
     */
    public static void register(ResourceLocation res, CSReadedAnim anim) {
        ANIMATION_REGISTRY.putObject(res, anim);
    }
}
