package com.leviathanstudio.test.common;

import java.util.UUID;

import javax.annotation.Nullable;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.pack.animation.AnimationLootAt;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityTest2 extends EntityAnimal implements IAnimated
{
    protected AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(this);
    protected boolean          fanOpen     = true;

    public EntityTest2(World par1World)
    {
        super(par1World);
        this.animHandler.addAnim(Mod_Test.MODID, "close_fan", "peacock", false);
        this.animHandler.addAnim(Mod_Test.MODID, "open_fan", "close_fan");
        this.animHandler.addAnim(Mod_Test.MODID, "custom", "peacock", new AnimationLootAt(this, "Head"));
        this.setSize(1.0F, 1.5F);
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10));
        this.getAnimationHandler().startAnimation(Mod_Test.MODID, "custom");
        this.initEntityAI();
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    // Getter for animation handler
    @Override
    public AnimationHandler getAnimationHandler()
    {
        return this.animHandler;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
    {
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "close_fan")
                && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "open_fan"))
            if (this.fanOpen)
            {
                this.getAnimationHandler().stopAnimation(Mod_Test.MODID, "open_fan");
                this.getAnimationHandler().startAnimation(Mod_Test.MODID, "close_fan");
                this.fanOpen = false;
            }
            else
            {
                this.getAnimationHandler().stopAnimation(Mod_Test.MODID, "close_fan");
                this.getAnimationHandler().startAnimation(Mod_Test.MODID, "open_fan");
                this.fanOpen = true;
            }
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    @Override
    public UUID getUUID()
    {
        return this.getPersistentID();
    }
}
