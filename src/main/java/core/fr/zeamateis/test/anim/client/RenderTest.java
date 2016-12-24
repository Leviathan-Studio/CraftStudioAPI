package fr.zeamateis.test.anim.client;

import fr.zeamateis.test.anim.common.EntityTest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTest extends RenderLiving<EntityTest>
{
    public RenderTest() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelEntityTest(), 0.5F);
    }

    @Override
    public void preRenderCallback(EntityTest entity, float partialTicks) {
        GlStateManager.translate(0.0D, 1.05D, 0.0D);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTest entity) {
        return new ResourceLocation("testmod", "textures/entity/deadmau5.png");
    }
}