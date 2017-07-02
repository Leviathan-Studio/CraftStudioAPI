package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedEntity;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.world.World;

public class EntityTest4 extends AnimatedEntity
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest4.class);

    static {
        EntityTest4.animHandler.addAnim(Mod_Test.MODID, "rotation", "craftstudio_api_test2", true);
    }

    public EntityTest4(World par1World) {
        super(par1World);
    }

    @Override
    public AnimationHandler getAnimationHandler() {
        return EntityTest4.animHandler;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "rotation", this))
            this.getAnimationHandler().clientStartAnimation(Mod_Test.MODID, "rotation", this);
    }
}
