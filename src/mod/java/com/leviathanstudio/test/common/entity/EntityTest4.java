package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedEntity;
import com.leviathanstudio.test.common.ModTest;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityTest4 extends AnimatedEntity
{
    protected static AnimationHandler<EntityTest4> animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest4.class);

    static {
        EntityTest4.animHandler.addAnim(ModTest.MODID, "rotation", "craftstudio_api_test2", true);
    }

    public EntityTest4(EntityType<? extends AnimatedEntity> type, World par1World) {
        super(type, par1World);
    }

    @SuppressWarnings("unchecked")
	@Override
    public AnimationHandler<EntityTest4> getAnimationHandler() {
        return EntityTest4.animHandler;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(ModTest.MODID, "rotation", this))
            this.getAnimationHandler().startAnimation(ModTest.MODID, "rotation", this);
    }
}
