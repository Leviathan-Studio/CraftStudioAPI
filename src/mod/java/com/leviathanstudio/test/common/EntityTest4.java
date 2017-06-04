package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class EntityTest4 extends EntityCreature implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest4.class);
    
    static{
    	animHandler.addAnim(Mod_Test.MODID, "rotation", "craftstudio_api_test2", true);
    }

    public EntityTest4(World par1World) {
        super(par1World);
        animHandler.addAnimated(this);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    // Getter for animation handler
    @Override
    public AnimationHandler getAnimationHandler() {
        return this.animHandler;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        // Activate the animation in ticking method
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "rotation", this))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "rotation", this);
    }

    @Override
    public UUID getUUID() {
        return this.getPersistentID();
    }
}
