package fr.zeamateis.test.anim.client;

import fr.zeamateis.test.anim.common.EntityTest;
import lib.craftstudio.client.ModelCraftStudio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTest<T extends EntityTest> extends RenderLiving<T>
{
    public RenderTest() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelCraftStudio("ModelDeadCorpse", 64, 32), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation("testmod", "textures/entity/testEntity.png");
    }
}