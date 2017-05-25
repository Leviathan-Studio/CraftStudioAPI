package fr.zeamateis.test.anim.client;

import com.leviathanstudio.craftstudio.client.ModelCraftStudio;

import fr.zeamateis.test.anim.common.EntityTest2;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTest2<T extends EntityTest2> extends RenderLiving<T>
{
    public RenderTest2(RenderManager manager) {
        super(manager, new ModelCraftStudio("peacock", 128, 64), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation("testmod", "textures/entity/peacock.png");
    }
}
