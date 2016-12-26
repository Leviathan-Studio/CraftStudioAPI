package fr.zeamateis.test.anim.client;

import fr.zeamateis.test.anim.common.TileEntityBlockTest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityBlockTestRenderer extends TileEntitySpecialRenderer<TileEntityBlockTest>
{
    private final ModelBlockTest modelTest = new ModelBlockTest();

    @Override
    public void renderTileEntityAt(TileEntityBlockTest tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        this.bindTexture(new ResourceLocation("testmod", "textures/block/blockTest.png"));
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 1, z + 0.5D);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        this.modelTest.render(tileEntity);
        GlStateManager.popMatrix();
    }
}