package fr.zeamateis.test.anim.client;

import java.util.HashMap;

import lib.craftstudio.client.CSModelRenderer;
import lib.craftstudio.common.IAnimated;
import lib.craftstudio.common.animation.AnimationHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.tileentity.TileEntity;

public class ModelBlockTest extends ModelBase
{
    private HashMap<String, CSModelRenderer> parts = new HashMap<>();

    private CSModelRenderer                  Block1;

    private CSModelRenderer                  Block2;

    private CSModelRenderer                  Block3;

    private CSModelRenderer                  Block4;

    private CSModelRenderer                  Block;

    public ModelBlockTest() {
        this.textureHeight = 64;
        this.textureWidth = 64;
        this.Block = new CSModelRenderer(this, "Block", 0, 0);
        this.Block.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.Block.setDefaultRotationPoint(0.0F, -8.0F, -0.0F);
        this.Block.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Block.boxName, this.Block);
        this.Block1 = new CSModelRenderer(this, "Block1", 32, 32);
        this.Block1.addBox(-2.0F, -8.0F, -2.0F, 4, 16, 4);
        this.Block1.setDefaultRotationPoint(0.0F, -16.0F, -0.0F);
        this.Block1.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Block1.boxName, this.Block1);
        this.Block2 = new CSModelRenderer(this, "Block2", 16, 32);
        this.Block2.addBox(-2.0F, -8.0F, -2.0F, 4, 16, 4);
        this.Block2.setDefaultRotationPoint(0.0F, -0.0F, -14.0F);
        this.Block2.setInitialRotationMatrix(90.0F, -0.0F, -0.0F);
        this.parts.put(this.Block2.boxName, this.Block2);
        this.Block3 = new CSModelRenderer(this, "Block3", 48, 32);
        this.Block3.addBox(-2.0F, -8.0F, -2.0F, 4, 16, 4);
        this.Block3.setDefaultRotationPoint(0.0F, 16.0F, -0.0F);
        this.Block3.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Block3.boxName, this.Block3);
        this.Block4 = new CSModelRenderer(this, "Block4", 0, 32);
        this.Block4.addBox(-2.0F, -8.0F, -2.0F, 4, 16, 4);
        this.Block4.setDefaultRotationPoint(0.0F, -0.0F, 16.0F);
        this.Block4.setInitialRotationMatrix(90.0F, -0.0F, -0.0F);
        this.parts.put(this.Block4.boxName, this.Block4);
        this.Block.addChild(this.Block1);
        this.Block.addChild(this.Block2);
        this.Block.addChild(this.Block3);
        this.Block.addChild(this.Block4);
    }

    void render(TileEntity tileEntityIn) {
        float modelScale = 0.0625F;
        AnimationHandler.performAnimationInModel(this.parts, (IAnimated) tileEntityIn);
        this.Block.render(modelScale);
    }

    public CSModelRenderer getModelRendererFromName(String name) {
        return this.parts.get(name);
    }
}
