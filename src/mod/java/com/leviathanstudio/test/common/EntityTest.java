package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class EntityTest extends EntityCreature implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest.class);
    
    static {
    	animHandler.addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
    	animHandler.addAnim(Mod_Test.MODID, "offset", "craftstudio_api_test", true);
    	animHandler.addAnim(Mod_Test.MODID, "streching", "craftstudio_api_test", true);
    }

    public EntityTest(World par1World) {
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
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "streching", this))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "streching", this);
    }

    @Override
    public UUID getUUID() {

        return this.getPersistentID();
    }

}