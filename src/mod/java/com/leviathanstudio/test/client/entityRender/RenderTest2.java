package com.leviathanstudio.test.client.entityRender;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.test.common.ModTest;
import com.leviathanstudio.test.common.entity.EntityTest2;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTest2<T extends EntityTest2> extends LivingRenderer<T, ModelCraftStudio<T>>
{
    public static final Factory<EntityTest2> FACTORY = new Factory<>();

    public RenderTest2(EntityRendererManager manager) {
        super(manager, new ModelCraftStudio<T>(ModTest.MODID, "peacock", 128, 64), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation(ModTest.MODID, "textures/entity/peacock.png");
    }

    public static class Factory<T extends EntityTest2> implements IRenderFactory<T>
    {
        @Override
        public EntityRenderer<? super T> createRenderFor(EntityRendererManager manager) {
            return new RenderTest2<T>(manager);
        }
    }
}
