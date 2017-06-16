package com.leviathanstudio.test.proxy;

import com.leviathanstudio.craftstudio.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.json.EnumRenderType;
import com.leviathanstudio.craftstudio.client.json.EnumResourceType;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.CSTileEntitySpecialRenderer;
import com.leviathanstudio.test.client.RenderTest;
import com.leviathanstudio.test.client.RenderTest2;
import com.leviathanstudio.test.client.RenderTest3;
import com.leviathanstudio.test.client.RenderTest4;
import com.leviathanstudio.test.common.EntityTest;
import com.leviathanstudio.test.common.EntityTest2;
import com.leviathanstudio.test.common.EntityTest3;
import com.leviathanstudio.test.common.EntityTest4;
import com.leviathanstudio.test.common.Mod_Test;
import com.leviathanstudio.test.common.TileEntityTest;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerModels() {
        super.registerModels();
        CSRegistryHelper registry = new CSRegistryHelper(Mod_Test.MODID);
        registry.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "model_dead_corpse");
        registry.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "craftstudio_api_test2");
        registry.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "craftstudio_api_test");
        registry.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "dragon_brun");
        registry.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "peacock");
    }

    @Override
    public void registerAnims() {
        super.registerAnims();
        CSRegistryHelper registry = new CSRegistryHelper(Mod_Test.MODID);
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "position");
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "rotation");
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "offset");
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "streching");
        registry.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "fly");
        registry.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "idle");
        registry.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "close_fan");
    }

    @Override
    public void preInit() {
        super.preInit();
        // Registry Entity
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, RenderTest.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTest2.class, RenderTest2.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTest3.class, RenderTest3.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTest4.class, RenderTest4.FACTORY);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTest.class, new CSTileEntitySpecialRenderer(Mod_Test.MODID, "craftstudio_api_test", 64,
                32, new ResourceLocation(Mod_Test.MODID, "textures/entity/craftstudio_api_test.png")));
    }
}