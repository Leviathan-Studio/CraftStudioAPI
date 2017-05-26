package fr.zeamateis.test.anim.client;

import com.leviathanstudio.craftstudio.client.ModelCraftStudio;

import fr.zeamateis.test.anim.common.Mod_Test;
import fr.zeamateis.test.anim.common.TileEntityBlockTest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityBlockTestRenderer<T extends TileEntityBlockTest> extends TileEntitySpecialRenderer<T>
{
    private final ModelCraftStudio modelTest = new ModelCraftStudio(Mod_Test.MODID, "model_block_test", 64, 32);

    @Override
    public void renderTileEntityAt(T tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        this.bindTexture(new ResourceLocation("testmod", "textures/block/null.png"));
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 1, z + 0.5D);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        this.modelTest.render(tileEntityIn);
        GlStateManager.popMatrix();
    }
}