package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.common.CSRegistryHelper;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;

public class CommonProxy
{
    public void preInit() {
        CSRegistryHelper.register(ResourceType.ANIM, RenderType.BLOCK, "position");
        CSRegistryHelper.register(ResourceType.ANIM, RenderType.BLOCK, "rotation");
        CSRegistryHelper.register(ResourceType.ANIM, RenderType.ENTITY, "fly");
        CSRegistryHelper.register(ResourceType.ANIM, RenderType.ENTITY, "idle");
        CSRegistryHelper.register(ResourceType.ANIM, RenderType.ENTITY, "close_fan");
    }

    public void init() {}
}