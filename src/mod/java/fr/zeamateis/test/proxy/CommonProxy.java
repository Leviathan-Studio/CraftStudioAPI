package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.common.RegistryCraftStudio;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;

public class CommonProxy
{
    public void preInit() {
        RegistryCraftStudio.register(ResourceType.ANIM, RenderType.BLOCK, "position");
        RegistryCraftStudio.register(ResourceType.ANIM, RenderType.BLOCK, "rotation");
        RegistryCraftStudio.register(ResourceType.ANIM, RenderType.ENTITY, "fly");
        RegistryCraftStudio.register(ResourceType.ANIM, RenderType.ENTITY, "idle");
        RegistryCraftStudio.register(ResourceType.ANIM, RenderType.ENTITY, "close_fan");
    }

    public void init() {}
}