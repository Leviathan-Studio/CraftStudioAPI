package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.test.common.ModTest;
import com.leviathanstudio.test.pack.animation.AnimationLootAt;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class EntityTest2 extends AnimalEntity implements IAnimated
{
    protected static AnimationHandler<EntityTest2> animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest2.class);
    protected boolean                 fanOpen     = true;

    static {
        EntityTest2.animHandler.addAnim(ModTest.MODID, "close_fan", "peacock", false);
        EntityTest2.animHandler.addAnim(ModTest.MODID, "open_fan", "close_fan");
        EntityTest2.animHandler.addAnim(ModTest.MODID, "lookat", new AnimationLootAt("Head"));
    }

    public EntityTest2(EntityType<? extends AnimalEntity> type, World par1World) {
        super(type, par1World);
        this.stepHeight = 1.5F;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 10));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @SuppressWarnings("unchecked")
	@Override
    public AnimationHandler<EntityTest2> getAnimationHandler() {
        return EntityTest2.animHandler;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        if (!this.getAnimationHandler().isAnimationActive(ModTest.MODID, "close_fan", this)
                && !this.getAnimationHandler().isAnimationActive(ModTest.MODID, "open_fan", this))
            if (this.fanOpen) {
                this.getAnimationHandler().networkStopStartAnimation(ModTest.MODID, "open_fan", "close_fan", this);
                this.fanOpen = false;
            }
            else {
                this.getAnimationHandler().networkStopStartAnimation(ModTest.MODID, "close_fan", "open_fan", this);
                this.fanOpen = true;
            }
        return true;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.getAnimationHandler().animationsUpdate(this);

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(ModTest.MODID, "lookat", this))
            this.getAnimationHandler().startAnimation(ModTest.MODID, "lookat", this);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public DimensionType getDimension() {
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
