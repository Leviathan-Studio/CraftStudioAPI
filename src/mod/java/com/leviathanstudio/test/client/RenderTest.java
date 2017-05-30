package com.leviathanstudio.test.client;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.test.common.EntityTest;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTest<T extends EntityTest> extends RenderLiving<T>
{
    public static final Factory FACTORY = new Factory();

    public RenderTest(RenderManager manager)
    {
        super(manager, new ModelCraftStudio(Mod_Test.MODID, "craftstudio_api_test", 64, 32), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
        return new ResourceLocation(Mod_Test.MODID, "textures/entity/craftstudio_api_test.png");
    }

    public static class Factory<T extends EntityTest> implements IRenderFactory<T>
    {
        @Override
        public Render<? super T> createRenderFor(RenderManager manager)
        {
            return new RenderTest(manager);
        }
    }
}