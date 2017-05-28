package com.leviathanstudio.test.client;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.test.common.EntityTest4;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTest4<T extends EntityTest4> extends RenderLiving<T>
{
    public RenderTest4(RenderManager manager) {
        super(manager, new ModelCraftStudio(Mod_Test.MODID, "craftstudio_api_test2", 64, 32), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation("testmod", "textures/entity/craftstudio_api_test.png");
    }
}
