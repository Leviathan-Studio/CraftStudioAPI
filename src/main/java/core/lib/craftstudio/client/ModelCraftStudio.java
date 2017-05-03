package lib.craftstudio.client;

import java.util.ArrayList;
import java.util.List;

import lib.craftstudio.client.json.CSReadedModel;
import lib.craftstudio.client.json.CSReadedModelBlock;
import lib.craftstudio.common.IAnimated;
import lib.craftstudio.common.animation.AnimationHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class ModelCraftStudio extends ModelBase
{
    private List<CSModelRenderer> parentBlocks = new ArrayList<>();

    public ModelCraftStudio(String modelNameIn, int textureWidth, int textureHeight) {

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        CSReadedModel rModel = CSModelMesher.models.get(modelNameIn);
        CSModelRenderer modelRend;
        
        for (CSReadedModelBlock rBlock : rModel.parents) {
            modelRend = new CSModelRenderer(this, rBlock.name, rBlock.texOffset[0], rBlock.texOffset[1]);
            if (rBlock.faceSize == null)
                modelRend.addBox(rBlock.boxSetup.x, rBlock.boxSetup.y, rBlock.boxSetup.z, rBlock.size.x, rBlock.size.y, rBlock.size.z);
            else
                modelRend.addBox(rBlock.boxSetup.x, rBlock.boxSetup.y, rBlock.boxSetup.z, rBlock.size.x, rBlock.size.y, rBlock.size.z);
            modelRend.setDefaultRotationPoint(rBlock.rotationPoint.x, rBlock.rotationPoint.y, rBlock.rotationPoint.z);
            modelRend.setInitialRotationMatrix(rBlock.rotation.x, rBlock.rotation.y, rBlock.rotation.z);
            modelRend.setTextureSize(this.textureWidth, this.textureHeight);
            this.parentBlocks.add(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    private void generateChild(CSReadedModelBlock rParent, CSModelRenderer parent) {
        CSModelRenderer modelRend;
        for (CSReadedModelBlock rBlock : rParent.childs) {
            modelRend = new CSModelRenderer(this, rBlock.name, rBlock.texOffset[0], rBlock.texOffset[1]);
            if (rBlock.faceSize == null)
                modelRend.addBox(rBlock.boxSetup.x, rBlock.boxSetup.y, rBlock.boxSetup.z, rBlock.size.x, rBlock.size.y, rBlock.size.z);
            else
                modelRend.addBox(rBlock.boxSetup.x, rBlock.boxSetup.y, rBlock.boxSetup.z, rBlock.size.x, rBlock.size.y, rBlock.size.z);
            modelRend.setDefaultRotationPoint(rBlock.rotationPoint.x, rBlock.rotationPoint.y, rBlock.rotationPoint.z);
            modelRend.setInitialRotationMatrix(rBlock.rotation.x, rBlock.rotation.y, rBlock.rotation.z);
            modelRend.setTextureSize(this.textureWidth, this.textureHeight);
            parent.addChild(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    // Render method for blocks
    public void render(TileEntity tileEntityIn) {
        float modelScale = 0.0625F;
        AnimationHandler.performAnimationInModel(this.parentBlocks, (IAnimated) tileEntityIn);
        for (CSModelRenderer block : this.parentBlocks)
            block.render(modelScale);
    }

    // Render method for entities
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        AnimationHandler.performAnimationInModel(this.parentBlocks, (IAnimated) entityIn);
        for (CSModelRenderer block : this.parentBlocks)
            block.render(scale);
    }

    public CSModelRenderer getModelRendererFromName(String name) {
        CSModelRenderer result;
        for (CSModelRenderer parent : this.parentBlocks) {
            result = getModelRendererFromNameAndBlock(name, parent);
            if (result != null)
                return result;
        }

        return null;

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
}