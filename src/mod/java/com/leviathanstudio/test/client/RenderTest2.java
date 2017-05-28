package com.leviathanstudio.test.client;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.test.common.EntityTest2;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTest2<T extends EntityTest2> extends RenderLiving<T>
{
    public static final Factory FACTORY = new Factory();

    public RenderTest2(RenderManager manager) {
        super(manager, new ModelCraftStudio(Mod_Test.MODID, "peacock", 128, 64), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation(Mod_Test.MODID, "textures/entity/peacock.png");
    }

    public static class Factory<T extends EntityTest2> implements IRenderFactory<T>
    {
        @Override
        public Render<? super T> createRenderFor(RenderManager manager) {
            return new RenderTest2(manager);
        }
    }
}
