package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.test.pack.animation.AnimationLootAt;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class EntityTest2 extends EntityAnimal implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest2.class);
    protected boolean                 fanOpen     = true;

    static {
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "close_fan", "peacock", false);
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "open_fan", "close_fan");
        EntityTest2.animHandler.addAnim(Mod_Test.MODID, "lookat", "peacock", new AnimationLootAt("Head"));
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

    // Getter for animation handler
    @Override
    public AnimationHandler getAnimationHandler() {
        return EntityTest2.animHandler;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "close_fan", this)
                && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "open_fan", this))
            if (this.fanOpen) {
                this.getAnimationHandler().stopStartAnimation(Mod_Test.MODID, "open_fan", "close_fan", this);
                this.fanOpen = false;
            }
            else {
                this.getAnimationHandler().stopStartAnimation(Mod_Test.MODID, "close_fan", "open_fan", this);
                this.fanOpen = true;
            }
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.getAnimationHandler().animationsUpdate(this);
        
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "lookat", this))
            this.getAnimationHandler().clientStartAnimation(Mod_Test.MODID, "lookat", this);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    public UUID getUUID() {
        return this.getPersistentID();
    }
}
