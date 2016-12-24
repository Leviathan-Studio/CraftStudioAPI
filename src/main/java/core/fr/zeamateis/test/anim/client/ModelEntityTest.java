package fr.zeamateis.test.anim.client;

import java.util.HashMap;

import lib.craftstudio.client.CSModelRenderer;
import lib.craftstudio.common.IAnimated;
import lib.craftstudio.common.animation.AnimationHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelEntityTest extends ModelBase
{
    private HashMap<String, CSModelRenderer> parts = new HashMap<>();

    private CSModelRenderer                  Headwear;

    private CSModelRenderer                  LeftEar;

    private CSModelRenderer                  RightEar;

    private CSModelRenderer                  Head;

    private CSModelRenderer                  Hand_Right;

    private CSModelRenderer                  Forearm_Right;

    private CSModelRenderer                  Elbow_Right;

    private CSModelRenderer                  Hand_Left;

    private CSModelRenderer                  Forearm_Left;

    private CSModelRenderer                  Elbow_Left;

    private CSModelRenderer                  Neck;

    private CSModelRenderer                  Upper_Arm_Right;

    private CSModelRenderer                  Upper_Arm_Left;

    private CSModelRenderer                  Chest;

    private CSModelRenderer                  Lower_Leg_Right;

    private CSModelRenderer                  Knee_Right;

    private CSModelRenderer                  Lower_Leg_Left;

    private CSModelRenderer                  Knee_Left;

    private CSModelRenderer                  Spine;

    private CSModelRenderer                  Upper_Leg_Right;

    private CSModelRenderer                  Upper_Leg_Left;

    private CSModelRenderer                  Pelvis;

    public ModelEntityTest() {
        this.textureHeight = 64;
        this.textureWidth = 64;
        this.Pelvis = new CSModelRenderer(this, "Pelvis", 16, 24);
        this.Pelvis.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4);
        this.Pelvis.setDefaultRotationPoint(0.0F, -11.0F, -0.0F);
        this.Pelvis.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Pelvis.boxName, this.Pelvis);
        this.Headwear = new CSModelRenderer(this, "Headwear", 32, 0);
        this.Headwear.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, true);
        this.Headwear.setDefaultRotationPoint(0.0F, -0.0F, -0.0F);
        this.Headwear.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Headwear.boxName, this.Headwear);
        this.LeftEar = new CSModelRenderer(this, "LeftEar", 24, 0);
        this.LeftEar.addBox(-3.0F, -3.0F, -0.5F, 6, 6, 1);
        this.LeftEar.setDefaultRotationPoint(6.0F, -6.0F, -0.0F);
        this.LeftEar.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.LeftEar.boxName, this.LeftEar);
        this.RightEar = new CSModelRenderer(this, "RightEar", 24, 0);
        this.RightEar.addBox(-3.0F, -3.0F, -0.5F, 6, 6, 1);
        this.RightEar.setDefaultRotationPoint(-6.0F, -6.0F, -0.0F);
        this.RightEar.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.RightEar.boxName, this.RightEar);
        this.Head = new CSModelRenderer(this, "Head", 0, 0);
        this.Head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        this.Head.setDefaultRotationPoint(0.0F, -4.5F, -0.0F);
        this.Head.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Head.boxName, this.Head);
        this.Hand_Right = new CSModelRenderer(this, "Hand_Right", 40, 29);
        this.Hand_Right.addBox(-2.0F, 1.5F, -2.0F, 4, 3, 4);
        this.Hand_Right.setDefaultRotationPoint(0.0F, 3.5F, -4.0F);
        this.Hand_Right.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Hand_Right.boxName, this.Hand_Right);
        this.Forearm_Right = new CSModelRenderer(this, "Forearm_Right", 40, 21);
        this.Forearm_Right.addBox(-2.0F, 1.0F, -6.0F, 4, 4, 4);
        this.Forearm_Right.setDefaultRotationPoint(0.0F, 2.0F, 0.0F);
        this.Forearm_Right.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Forearm_Right.boxName, this.Forearm_Right);
        this.Elbow_Right = new CSModelRenderer(this, "Elbow_Right", 40, 20);
        this.Elbow_Right.addBox(-2.0F, 1.0F, -6.0F, 4, 2, 4);
        this.Elbow_Right.setDefaultRotationPoint(-1.0F, 1.0F, 4.0F);
        this.Elbow_Right.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Elbow_Right.boxName, this.Elbow_Right);
        this.Hand_Left = new CSModelRenderer(this, "Hand_Left", 40, 29);
        this.Hand_Left.addBox(-2.0F, 1.5F, -2.0F, 4, 3, 4);
        this.Hand_Left.setDefaultRotationPoint(0.0F, 3.5F, -4.0F);
        this.Hand_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Hand_Left.boxName, this.Hand_Left);
        this.Forearm_Left = new CSModelRenderer(this, "Forearm_Left", 40, 21);
        this.Forearm_Left.addBox(-2.0F, 1.0F, -6.0F, 4, 4, 4);
        this.Forearm_Left.setDefaultRotationPoint(0.0F, 2.0F, 0.0F);
        this.Forearm_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Forearm_Left.boxName, this.Forearm_Left);
        this.Elbow_Left = new CSModelRenderer(this, "Elbow_Left", 40, 22);
        this.Elbow_Left.addBox(-2.0F, 1.0F, -6.0F, 4, 2, 4);
        this.Elbow_Left.setDefaultRotationPoint(1.0F, 1.0F, 4.0F);
        this.Elbow_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Elbow_Left.boxName, this.Elbow_Left);
        this.Neck = new CSModelRenderer(this, "Neck", 15, -1);
        this.Neck.addBox(-2.0F, -0.5F, -1.5F, 4, 1, 3);
        this.Neck.setDefaultRotationPoint(0.0F, -6.0F, -3.0F);
        this.Neck.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Neck.boxName, this.Neck);
        this.Upper_Arm_Right = new CSModelRenderer(this, "Upper_Arm_Right", 40, 16);
        this.Upper_Arm_Right.addBox(-3.0F, -2.0F, -2.0F, 4, 4, 4);
        this.Upper_Arm_Right.setDefaultRotationPoint(-5.0F, -4.0F, -3.0F);
        this.Upper_Arm_Right.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Upper_Arm_Right.boxName, this.Upper_Arm_Right);
        this.Upper_Arm_Left = new CSModelRenderer(this, "Upper_Arm_Left", 40, 16);
        this.Upper_Arm_Left.addBox(-1.0F, -2.0F, -2.0F, 4, 4, 4, true);
        this.Upper_Arm_Left.setDefaultRotationPoint(5.0F, -4.0F, -3.0F);
        this.Upper_Arm_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Upper_Arm_Left.boxName, this.Upper_Arm_Left);
        this.Chest = new CSModelRenderer(this, "Chest", 16, 16);
        this.Chest.addBox(-4.0F, -6.0F, -5.0F, 8, 4, 4);
        this.Chest.setDefaultRotationPoint(0.0F, -4.0F, 0.0F);
        this.Chest.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Chest.boxName, this.Chest);
        this.Lower_Leg_Right = new CSModelRenderer(this, "Lower_Leg_Right", 0, 24);
        this.Lower_Leg_Right.addBox(-2.0F, 2.0F, 2.0F, 4, 4, 4);
        this.Lower_Leg_Right.setDefaultRotationPoint(0.0F, 1.0F, 0.0F);
        this.Lower_Leg_Right.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Lower_Leg_Right.boxName, this.Lower_Leg_Right);
        this.Knee_Right = new CSModelRenderer(this, "Knee_Right", 0, 22);
        this.Knee_Right.addBox(-2.0F, 1.0F, 2.0F, 4, 2, 4);
        this.Knee_Right.setDefaultRotationPoint(0.0F, 5.0F, -4.0F);
        this.Knee_Right.setInitialRotationMatrix(-2.584458E-6F, -0.0F, -0.0F);
        this.parts.put(this.Knee_Right.boxName, this.Knee_Right);
        this.Lower_Leg_Left = new CSModelRenderer(this, "Lower_Leg_Left", 0, 24);
        this.Lower_Leg_Left.addBox(-2.0F, 2.0F, 2.0F, 4, 4, 4);
        this.Lower_Leg_Left.setDefaultRotationPoint(0.0F, 1.0F, 0.0F);
        this.Lower_Leg_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Lower_Leg_Left.boxName, this.Lower_Leg_Left);
        this.Knee_Left = new CSModelRenderer(this, "Knee_Left", 0, 22);
        this.Knee_Left.addBox(-2.0F, 1.0F, 2.0F, 4, 2, 4);
        this.Knee_Left.setDefaultRotationPoint(0.0F, 5.0F, -4.0F);
        this.Knee_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Knee_Left.boxName, this.Knee_Left);
        this.Spine = new CSModelRenderer(this, "Spine", 16, 20);
        this.Spine.addBox(-4.0F, -6.0F, -5.0F, 8, 4, 4);
        this.Spine.setDefaultRotationPoint(0.0F, 4.0F, 3.0F);
        this.Spine.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Spine.boxName, this.Spine);
        this.Upper_Leg_Right = new CSModelRenderer(this, "Upper_Leg_Right", 0, 16);
        this.Upper_Leg_Right.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.Upper_Leg_Right.setDefaultRotationPoint(-2.0F, 6.0F, -0.0F);
        this.Upper_Leg_Right.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Upper_Leg_Right.boxName, this.Upper_Leg_Right);
        this.Upper_Leg_Left = new CSModelRenderer(this, "Upper_Leg_Left", 0, 16);
        this.Upper_Leg_Left.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.Upper_Leg_Left.setDefaultRotationPoint(2.0F, 6.0F, -0.0F);
        this.Upper_Leg_Left.setInitialRotationMatrix(0.0F, -0.0F, -0.0F);
        this.parts.put(this.Upper_Leg_Left.boxName, this.Upper_Leg_Left);
        this.Head.addChild(this.Headwear);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.RightEar);
        this.Neck.addChild(this.Head);
        this.Forearm_Right.addChild(this.Hand_Right);
        this.Elbow_Right.addChild(this.Forearm_Right);
        this.Upper_Arm_Right.addChild(this.Elbow_Right);
        this.Forearm_Left.addChild(this.Hand_Left);
        this.Elbow_Left.addChild(this.Forearm_Left);
        this.Upper_Arm_Left.addChild(this.Elbow_Left);
        this.Chest.addChild(this.Neck);
        this.Chest.addChild(this.Upper_Arm_Right);
        this.Chest.addChild(this.Upper_Arm_Left);
        this.Spine.addChild(this.Chest);
        this.Knee_Right.addChild(this.Lower_Leg_Right);
        this.Upper_Leg_Right.addChild(this.Knee_Right);
        this.Knee_Left.addChild(this.Lower_Leg_Left);
        this.Upper_Leg_Left.addChild(this.Knee_Left);
        this.Pelvis.addChild(this.Spine);
        this.Pelvis.addChild(this.Upper_Leg_Right);
        this.Pelvis.addChild(this.Upper_Leg_Left);
    }

    // ModelSpider
    @Override
    public void render(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        AnimationHandler.performAnimationInModel(this.parts, (IAnimated) entityIn);
        this.Pelvis.render(scale);
    }

    public CSModelRenderer getModelRendererFromName(String name) {
        return this.parts.get(name);
    }
}
