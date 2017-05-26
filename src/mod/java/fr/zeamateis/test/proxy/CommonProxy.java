package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.CSRegistryHelper;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;

import fr.zeamateis.test.anim.common.Mod_Test;

public class CommonProxy
{
    public void registerModels() {}

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