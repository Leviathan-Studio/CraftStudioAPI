package com.leviathanstudio.test.client.entityRender;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.test.common.ModTest;
import com.leviathanstudio.test.common.entity.EntityTest3;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTest3<T extends EntityTest3> extends LivingRenderer<T, ModelCraftStudio<T>>
{
    public static final Factory<EntityTest3> FACTORY = new Factory<>();

    public RenderTest3(EntityRendererManager manager) {
        super(manager, new ModelCraftStudio<T>(ModTest.MODID, "dragon_brun", 256), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation(ModTest.MODID, "textures/entity/dragon_brun.png");
    }

    public static class Factory<T extends EntityTest3> implements IRenderFactory<T>
    {
        @Override
        public EntityRenderer<? super T> createRenderFor(EntityRendererManager manager) {
            return new RenderTest3<T>(manager);
        }
    }
}
