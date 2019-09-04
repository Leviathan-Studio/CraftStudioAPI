package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.test.common.Mod_Test;
import com.leviathanstudio.test.pack.animation.AnimationLootAt;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EntityTest2 extends AnimalEntity implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest2.class);
    protected boolean                 fanOpen     = true;

    static {
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "close_fan", "peacock", false);
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "open_fan", "close_fan");
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "lookat", new AnimationLootAt("Head"));
    }

    public EntityTest2(World par1World) {
        super(par1World);
        this.setSize(1.0F, 1.5F);
        this.tasks.addTask(2, new LookAtGoal(this, PlayerEntity.class, 10));
        this.initEntityAI();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public AnimationHandler getAnimationHandler() {
        return EntityTest2.animHandler;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "close_fan", this)
                && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "open_fan", this))
            if (this.fanOpen) {
                this.getAnimationHandler().networkStopStartAnimation(Mod_Test.MODID, "open_fan", "close_fan", this);
                this.fanOpen = false;
            }
            else {
                this.getAnimationHandler().networkStopStartAnimation(Mod_Test.MODID, "close_fan", "open_fan", this);
                this.fanOpen = true;
            }
        return true;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.getAnimationHandler().animationsUpdate(this);

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "lookat", this))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "lookat", this);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public int getDimension() {
        return this.dimension;
    }

    @Override
    public double getX() {
        return this.posX;
    }

    @Override
    public double getY() {
        return this.posY;
    }

    @Override
    public double getZ() {
        return this.posZ;
    }

    @Override
    public boolean isWorldRemote() {
        return this.world.isRemote;
    }
}
