package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedEntity;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityTest extends AnimatedEntity
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest.class);

    static {
        EntityTest.animHandler.addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
        EntityTest.animHandler.addAnim(Mod_Test.MODID, "offset", "craftstudio_api_test", true);
        EntityTest.animHandler.addAnim(Mod_Test.MODID, "streching", "craftstudio_api_test", true);
    }

    public EntityTest(World par1World) {
        super(null, par1World);
    }

    @Override
    public AnimationHandler getAnimationHandler() {
        return EntityTest.animHandler;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "streching", this))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "streching", this);
    }
}