package com.leviathanstudio.craftstudio.common.animation.simpleImpl;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.craftstudio.client.util.MathHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Matrix4f;
import java.nio.FloatBuffer;

/**
 * Renderer of animated TileEntity. If you only need one model to be render you
 * can directly use this class. Otherwise, or if you prefer, you can use this
 * class as a model to create your renderer.
 *
 * @param <T>
 * @author Timmypote
 * @since 0.3.0
 */
public class CSTileEntitySpecialRenderer<T extends TileEntity> extends TileEntityRenderer<T> {
    /**
     * Efficient rotation corrector, you can use it in your renderer.
     */
    public static final FloatBuffer ROTATION_CORRECTOR;

    static {
        Matrix4f mat = new Matrix4f();
        mat.set(MathHelper.quatFromEuler(180, 0, 0));
        ROTATION_CORRECTOR = MathHelper.makeFloatBuffer(mat);
    }

    /**
     * The model of the block.
     */
    protected ModelCraftStudio model;
    /**
     * The texture of the block
     */
    protected ResourceLocation texture;

    /**
     * The constructor that initialize the model and save texture.
     */
    public CSTileEntitySpecialRenderer(String modid, String modelNameIn, int textureWidth, int textureHeigth, ResourceLocation texture) {
        this.model = new ModelCraftStudio(modid, modelNameIn, textureWidth, textureHeigth);
        this.texture = texture;
    }
    
    

    @Override
    public void render(T te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.render(te, x, y, z, partialTicks, destroyStage);
      
        GlStateManager.pushMatrix();
        // Correction of the position.
        GlStateManager.translated(x + 0.5D, y + 1.5D, z + 0.5D);
        // Correction of the rotation.
        GlStateManager.multMatrix(CSTileEntitySpecialRenderer.ROTATION_CORRECTOR);
        this.bindTexture(this.texture); // Binding the texture.
        this.model.render(te); // Rendering the model.
        GlStateManager.popMatrix();
    }
}
