package com.leviathanstudio.test.client;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.test.common.Mod_Test;
import com.leviathanstudio.test.common.TileEntityTest;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBlockTest<T extends TileEntityTest> extends TileEntitySpecialRenderer<T>
{
    private ModelCraftStudio modelBlock = new ModelCraftStudio(Mod_Test.MODID, "craftstudio_api_test", 64, 32);

    @Override
    public void renderTileEntityAt(T te, double x, double y, double z, float partialTicks, int destroyStage) {
        this.bindTexture(new ResourceLocation(Mod_Test.MODID, "textures/entity/craftstudio_api_test.png"));
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 1.5D, z + 0.5D);
        GlStateManager.rotate(180F, 0, 0, 1);
        this.modelBlock.render(te);
        GlStateManager.popMatrix();
    }
}