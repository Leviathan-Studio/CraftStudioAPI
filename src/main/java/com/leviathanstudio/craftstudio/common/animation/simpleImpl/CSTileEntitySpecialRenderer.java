package com.leviathanstudio.craftstudio.common.animation.simpleImpl;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.craftstudio.client.util.MathHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class CSTileEntitySpecialRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T>
{
    public static final FloatBuffer ROTATION_CORRECTOR;
    static {
        Matrix4f mat = new Matrix4f();
        mat.set(MathHelper.quatFromEuler(180, 0, 0));
        ROTATION_CORRECTOR = MathHelper.makeFloatBuffer(mat);
    }

    protected ModelCraftStudio model;
    protected ResourceLocation texture;

    public CSTileEntitySpecialRenderer(String modid, String modelNameIn, int textureWidth, int textureHeigth, ResourceLocation texture) {
        this.model = new ModelCraftStudio(modid, modelNameIn, textureWidth, textureHeigth);
        this.texture = texture;
    }

    @Override
    public void renderTileEntityAt(T te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 1.5D, z + 0.5D);
        GlStateManager.multMatrix(CSTileEntitySpecialRenderer.ROTATION_CORRECTOR);
        this.bindTexture(this.texture);
        this.model.render(te);
        GlStateManager.popMatrix();
    }
}
