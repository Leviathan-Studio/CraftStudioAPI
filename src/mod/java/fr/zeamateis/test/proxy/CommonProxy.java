package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.CSRegistryHelper;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;

import fr.zeamateis.test.anim.common.Mod_Test;

public class CommonProxy
{
    public void registerModels() {
        CSRegistryHelper registry = new CSRegistryHelper(Mod_Test.MODID);
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "model_dead_corpse");
        registry.register(ResourceType.MODEL, RenderType.BLOCK, "craftstudio_api_test2");
        registry.register(ResourceType.MODEL, RenderType.BLOCK, "craftstudio_api_test");
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "dragon_brun");
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "peacock");
    }

    public void registerAnims() {
        CSRegistryHelper registry = new CSRegistryHelper(Mod_Test.MODID);
        registry.register(ResourceType.ANIM, RenderType.BLOCK, "position");
        registry.register(ResourceType.ANIM, RenderType.BLOCK, "rotation");
        registry.register(ResourceType.ANIM, RenderType.ENTITY, "fly");
        registry.register(ResourceType.ANIM, RenderType.ENTITY, "idle");
        registry.register(ResourceType.ANIM, RenderType.ENTITY, "close_fan");
    }

    public void preInit() {}

    public void init() {}
}