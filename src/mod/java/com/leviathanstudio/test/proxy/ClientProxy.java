package com.leviathanstudio.test.proxy;

import com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.util.EnumRenderType;
import com.leviathanstudio.craftstudio.client.util.EnumResourceType;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.CSTileEntitySpecialRenderer;
import com.leviathanstudio.test.client.entityRender.RenderTest;
import com.leviathanstudio.test.client.entityRender.RenderTest2;
import com.leviathanstudio.test.client.entityRender.RenderTest3;
import com.leviathanstudio.test.client.entityRender.RenderTest4;
import com.leviathanstudio.test.common.ModTest;
import com.leviathanstudio.test.common.entity.EntityTest;
import com.leviathanstudio.test.common.entity.EntityTest2;
import com.leviathanstudio.test.common.entity.EntityTest3;
import com.leviathanstudio.test.common.entity.EntityTest4;
import com.leviathanstudio.test.common.tileEntity.TileEntityTest;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    public void registerCraftStudioAssets() {
        CSRegistryHelper registry = new CSRegistryHelper(ModTest.MODID);
        registry.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "craftstudio_api_test2");
        registry.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "craftstudio_api_test");
        registry.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "model_dead_corpse");
        registry.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "dragon_brun");
        registry.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "peacock");
        
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "position");
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "rotation");
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "offset");
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "streching");
        registry.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "fly");
        registry.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "idle");
        registry.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "close_fan");
    }

    @Override
    public void registerEntityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, RenderTest.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTest2.class, RenderTest2.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTest3.class, RenderTest3.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTest4.class, RenderTest4.FACTORY);
    }

    @Override
    public void bindTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTest.class, new CSTileEntitySpecialRenderer(ModTest.MODID, "craftstudio_api_test", 64,
                32, new ResourceLocation(ModTest.MODID, "textures/entity/craftstudio_api_test.png")));
    }
}