package fr.zeamateis.test.anim.client;

import com.leviathanstudio.craftstudio.client.ModelCraftStudio;

import fr.zeamateis.test.anim.common.EntityTest;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTest<T extends EntityTest> extends RenderLiving<T>
{
    public RenderTest(RenderManager manager)
    {
        super(manager, new ModelCraftStudio("Dragon_Brun", 256, 256), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
        return new ResourceLocation("testmod", "textures/entity/dragon_brun.png");
    }
}