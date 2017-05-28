package com.leviathanstudio.test.proxy;

import com.leviathanstudio.craftstudio.CraftStudioRegistry;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;
import com.leviathanstudio.test.client.RenderTest;
import com.leviathanstudio.test.client.RenderTest2;
import com.leviathanstudio.test.client.RenderTest3;
import com.leviathanstudio.test.client.RenderTest4;
import com.leviathanstudio.test.common.EntityTest;
import com.leviathanstudio.test.common.EntityTest2;
import com.leviathanstudio.test.common.EntityTest3;
import com.leviathanstudio.test.common.EntityTest4;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerModels() {
        super.registerModels();
        CraftStudioRegistry registry = new CraftStudioRegistry(Mod_Test.MODID);
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "model_dead_corpse");
        registry.register(ResourceType.MODEL, RenderType.BLOCK, "craftstudio_api_test2");
        registry.register(ResourceType.MODEL, RenderType.BLOCK, "craftstudio_api_test");
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "dragon_brun");
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "peacock");
    }

    @Override
    public void registerAnims() {
        super.registerAnims();
        CraftStudioRegistry registry = new CraftStudioRegistry(Mod_Test.MODID);
        registry.register(ResourceType.ANIM, RenderType.BLOCK, "position");
        registry.register(ResourceType.ANIM, RenderType.BLOCK, "rotation");
        registry.register(ResourceType.ANIM, RenderType.ENTITY, "fly");
        registry.register(ResourceType.ANIM, RenderType.ENTITY, "idle");
        registry.register(ResourceType.ANIM, RenderType.ENTITY, "close_fan");
    }

    @Override
    public void preInit() {
        super.preInit();
        // Registry Entity
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityTest2.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest2(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityTest3.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest3(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityTest4.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest4(manager);
            }
        });
    }
}