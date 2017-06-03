package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityTest3 extends EntityCreature implements IAnimated
{
    protected AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(this);
    boolean                    fly         = false;

    public EntityTest3(World par1World)
    {
        super(par1World);
        this.animHandler.addAnim(Mod_Test.MODID, "fly", "dragon_brun", true);
        this.animHandler.addAnim(Mod_Test.MODID, "idle", "dragon_brun", true);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
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
    public boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack stack)
    {
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "fly"))
            this.fly = true;
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        // Activate the animation in ticking method
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "fly") && this.fly)
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "fly");

    }

    @Override
    public UUID getUUID()
    {
        return this.getPersistentID();
    }
}
