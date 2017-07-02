package com.leviathanstudio.craftstudio.client.registries;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class RegistryHandler
{
    public static RegistrySimple<ResourceLocation, CSReadedModel> modelRegistry;
    public static RegistrySimple<ResourceLocation, CSReadedAnim>  animationRegistry;

    public static void init() {
        modelRegistry = new RegistrySimple<>();
        animationRegistry = new RegistrySimple<>();
    }

    public static void register(ResourceLocation res, CSReadedModel model) {
        modelRegistry.putObject(res, model);
    }

    public static void register(ResourceLocation res, CSReadedAnim anim) {
        animationRegistry.putObject(res, anim);
    }
}
