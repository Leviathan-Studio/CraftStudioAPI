package com.leviathanstudio.craftstudio.client.model;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.client.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.PositionTextureVertex;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent a CraftStudio model in Minecraft.
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelCraftStudio<T extends Entity> extends EntityModel<T> {

    private List<CSModelRenderer> parentBlocks = new ArrayList<>();

    /**
     * @param modid       The ID of your mod
     * @param modelNameIn The name of your craftstudio model your have registered with
     *                    {@link com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper#register
     *                    CraftStudioRegistry#register}
     * @param textureSize The size of your texture if it's the same width/height
     */
    public ModelCraftStudio(String modid, String modelNameIn, int textureSize) {
        this(modid, modelNameIn, textureSize, textureSize);
    }

    /**
     * @param modid         The ID of your mod
     * @param modelNameIn   The name of your craftstudio model your have registered with
     *                      {@link com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper#register
     *                      CraftStudioRegistry#register}
     * @param textureWidth  The width texture of your model
     * @param textureHeight The height texture of your model
     */
    public ModelCraftStudio(String modid, String modelNameIn, int textureWidth, int textureHeight) {
        this(new ResourceLocation(modid, modelNameIn), textureWidth, textureHeight);
    }

    private ModelCraftStudio(ResourceLocation modelIn, int textureWidth, int textureHeight) {

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        CSReadedModel rModel = RegistryHandler.modelRegistry
        		.getValue(modelIn)
        		.orElseThrow(() -> new CSResourceNotRegisteredException(modelIn.toString()));
        CSModelRenderer modelRend;

        for (CSReadedModelBlock rBlock : rModel.getParents()) {
            modelRend = this.generateCSModelRend(rBlock);
            this.parentBlocks.add(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    /**
     * Return CSModelRenderer by his name and parts
     */
    public static CSModelRenderer getModelRendererFromNameAndBlock(String name, CSModelRenderer block) {
        CSModelRenderer childModel, result;

        if (block.boxName.equals(name))
            return block;

        for (RendererModel child : block.childModels)
            if (child instanceof CSModelRenderer) {
                childModel = (CSModelRenderer) child;
                result = getModelRendererFromNameAndBlock(name, childModel);
                if (result != null)
                    return result;
            }

        return null;
    }

    /**
     * Generate childs part of a model
     */
    private void generateChild(CSReadedModelBlock rParent, CSModelRenderer parent) {
        CSModelRenderer modelRend;
        for (CSReadedModelBlock rBlock : rParent.getChilds()) {
            modelRend = this.generateCSModelRend(rBlock);
            parent.addChild(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    /**
     * Generate CSModelRenderer from readed model block
     */
    private CSModelRenderer generateCSModelRend(CSReadedModelBlock rBlock) {
        CSModelRenderer modelRend = new CSModelRenderer(this, rBlock.getName(), rBlock.getTexOffset()[0], rBlock.getTexOffset()[1]);
        if (rBlock.getVertex() != null) {
            PositionTextureVertex vertices[] = new PositionTextureVertex[8];
            for (int i = 0; i < 8; i++)
                vertices[i] = new PositionTextureVertex(rBlock.getVertex()[i][0], rBlock.getVertex()[i][1], rBlock.getVertex()[i][2], 0.0F, 0.0F);
            modelRend.addBox(vertices, CSModelBox.getTextureUVsForRect(rBlock.getTexOffset()[0], rBlock.getTexOffset()[1], rBlock.getSize().x,
                    rBlock.getSize().y, rBlock.getSize().z));
        } else
            modelRend.addBox(-rBlock.getSize().x / 2, -rBlock.getSize().y / 2, -rBlock.getSize().z / 2, rBlock.getSize().x, rBlock.getSize().y,
                    rBlock.getSize().z);
        modelRend.setDefaultRotationPoint(rBlock.getRotationPoint().x, rBlock.getRotationPoint().y, rBlock.getRotationPoint().z);
        modelRend.setInitialRotationMatrix(rBlock.getRotation().x, rBlock.getRotation().y, rBlock.getRotation().z);
        modelRend.setDefaultOffset(rBlock.getOffset().x, rBlock.getOffset().y, rBlock.getOffset().z);
        modelRend.setDefaultStretch(rBlock.getStretch().x, rBlock.getStretch().y, rBlock.getStretch().z);
        modelRend.setTextureSize(this.textureWidth, this.textureHeight);
        return modelRend;
    }

    /**
     * Render function for an animated block<br>
     * Must be called in a
     * {@link net.minecraft.client.renderer.tileentity.TileEntityRenderer#render
     * renderTileEntityAt} method
     *
     * @param tileEntityIn The TileEntity who implements {@link IAnimated}
     */
    public void render(TileEntity tileEntityIn) {
        float modelScale = 0.0625F;
        ClientAnimationHandler.performAnimationInModel(this.parentBlocks, (IAnimated) tileEntityIn);
        for (int i = 0; i < this.parentBlocks.size(); i++)
            this.parentBlocks.get(i).render(modelScale);
    }

    /**
     * Render function for a non-animated block<br>
     * Must be called in a
     * {@link net.minecraft.client.renderer.tileentity.TileEntityRenderer#render
     * renderTileEntityAt} method
     */
    public void render() {
        float modelScale = 0.0625F;
        for (int i = 0; i < this.parentBlocks.size(); i++)
            this.parentBlocks.get(i).render(modelScale);
    }


    /**
     * Render methods for an Entity
     */
    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        ClientAnimationHandler.performAnimationInModel(this.parentBlocks, (IAnimated) entityIn);
        for (int i = 0; i < this.parentBlocks.size(); i++)
            this.parentBlocks.get(i).render(scale);
    }

    /**
     * Return CSModelRenderer by his name
     */
    public CSModelRenderer getModelRendererFromName(String name) {
        CSModelRenderer result;
        for (CSModelRenderer parent : this.parentBlocks) {
            result = getModelRendererFromNameAndBlock(name, parent);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * Getter
     */
    public List<CSModelRenderer> getParentBlocks() {
        return this.parentBlocks;
    }
}