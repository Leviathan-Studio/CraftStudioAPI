package com.leviathanstudio.craftstudio.client;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.exceptions.CSResourceNotRegisteredException;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModelCraftStudio extends ModelBase
{
    private List<CSModelRenderer> parentBlocks = new ArrayList<>();

    public ModelCraftStudio(String modid, String modelNameIn, int textureSize) {
        this(modid, modelNameIn, textureSize, textureSize);
    }

    public ModelCraftStudio(String modid, String modelNameIn, int textureWidth, int textureHeight) {
        this(new ResourceLocation(modid, modelNameIn), textureWidth, textureHeight);
    }

    public ModelCraftStudio(ResourceLocation modelIn, int textureWidth, int textureHeight) {

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        CSReadedModel rModel = GameRegistry.findRegistry(CSReadedModel.class).getValue(modelIn);
        if (rModel == null)
            throw new CSResourceNotRegisteredException(modelIn.toString());
        CSModelRenderer modelRend;

        for (CSReadedModelBlock rBlock : rModel.parents) {
            modelRend = this.generateCSModelRend(rBlock);
            this.parentBlocks.add(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    private void generateChild(CSReadedModelBlock rParent, CSModelRenderer parent) {
        CSModelRenderer modelRend;
        for (CSReadedModelBlock rBlock : rParent.childs) {
            modelRend = this.generateCSModelRend(rBlock);
            parent.addChild(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    private CSModelRenderer generateCSModelRend(CSReadedModelBlock rBlock) {
        CSModelRenderer modelRend = new CSModelRenderer(this, rBlock.name, rBlock.texOffset[0], rBlock.texOffset[1]);
        if (rBlock.vertex != null) {
            PositionTextureVertex vertices[] = new PositionTextureVertex[8];
            for (int i = 0; i < 8; i++)
                vertices[i] = new PositionTextureVertex(rBlock.vertex[i][0], rBlock.vertex[i][1], rBlock.vertex[i][2], 0.0F, 0.0F);
            modelRend.addBox(vertices,
                    CSModelBox.getTextureUVsForRect(rBlock.texOffset[0], rBlock.texOffset[1], rBlock.size.x, rBlock.size.y, rBlock.size.z));
        }
        else
            modelRend.addBox(rBlock.boxSetup.x, rBlock.boxSetup.y, rBlock.boxSetup.z, rBlock.size.x, rBlock.size.y, rBlock.size.z);
        modelRend.setDefaultRotationPoint(rBlock.rotationPoint.x, rBlock.rotationPoint.y, rBlock.rotationPoint.z);
        modelRend.setInitialRotationMatrix(rBlock.rotation.x, rBlock.rotation.y, rBlock.rotation.z);
        modelRend.setTextureSize(this.textureWidth, this.textureHeight);
        return modelRend;
    }

    /**
     * Render function for an animated block<br>
     * Must be called in a
     * {@link net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#renderTileEntityAt
     * renderTileEntityAt} method
     *
     * @param tileEntityIn
     *            The TileEntity who implements {@link IAnimated}
     */
    public void render(TileEntity tileEntityIn) {
        float modelScale = 0.0625F;
        AnimationHandler.performAnimationInModel(this.parentBlocks, (IAnimated) tileEntityIn);
        for (CSModelRenderer block : this.parentBlocks)
            block.render(modelScale);
    }

    /**
     * Render function for a non-animated block<br>
     * Must be called in a
     * {@link net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#renderTileEntityAt
     * renderTileEntityAt} method
     */
    public void render() {
        float modelScale = 0.0625F;
        for (CSModelRenderer block : this.parentBlocks)
            block.render(modelScale);
    }

    /** Render methods for an Entity */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        AnimationHandler.performAnimationInModel(this.parentBlocks, (IAnimated) entityIn);
        for (CSModelRenderer block : this.parentBlocks)
            block.render(scale);
    }

    private static CSModelRenderer getModelRendererFromNameAndBlock(String name, CSModelRenderer block) {
        CSModelRenderer childModel, result;

        if (block.boxName.equals(name))
            return block;

        for (ModelRenderer child : block.childModels)
            if (child instanceof CSModelRenderer) {
                childModel = (CSModelRenderer) child;
                result = getModelRendererFromNameAndBlock(name, childModel);
                if (result != null)
                    return result;
            }

        return null;
    }

    private CSModelRenderer getModelRendererFromName(String name) {
        CSModelRenderer result;
        for (CSModelRenderer parent : this.parentBlocks) {
            result = getModelRendererFromNameAndBlock(name, parent);
            if (result != null)
                return result;
        }
        return null;
    }
}