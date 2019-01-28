package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.test.common.Mod_Test;
import com.leviathanstudio.test.pack.animation.AnimationLootAt;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityTest2 extends EntityAnimal implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest2.class);
    protected boolean                 fanOpen     = true;

    static {
        //EntityTest2.animHandler.addAnim(Mod_Test.MODID, "close_fan", "peacock", false);
        //EntityTest2.animHandler.addAnim(Mod_Test.MODID, "open_fan", "close_fan");
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "lookat", new AnimationLootAt("Head"));
    }

    public EntityTest2(World par1World) {
        super(par1World);
        this.setSize(1.0F, 1.5F);
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10));
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
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        /*if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "close_fan", this)
                && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "open_fan", this))
            if (this.fanOpen) {
                this.getAnimationHandler().networkStopStartAnimation(Mod_Test.MODID, "open_fan", "close_fan", this);
                this.fanOpen = false;
            }
            else {
                this.getAnimationHandler().networkStopStartAnimation(Mod_Test.MODID, "close_fan", "open_fan", this);
                this.fanOpen = true;
            }*/
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.getAnimationHandler().animationsUpdate(this);

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "lookat", this))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "lookat", this);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
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
