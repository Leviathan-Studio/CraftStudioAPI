package fr.zeamateis.test.anim.client;

import com.leviathanstudio.craftstudio.client.ModelCraftStudio;

import fr.zeamateis.test.anim.common.EntityTest3;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTest3<T extends EntityTest3> extends RenderLiving<T>
{
    public RenderTest3(RenderManager manager) {
        super(manager, new ModelCraftStudio("dragon_brun", 256), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation("testmod", "textures/entity/dragon_brun.png");
    }
}
