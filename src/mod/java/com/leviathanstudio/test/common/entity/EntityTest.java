package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedEntity;
import com.leviathanstudio.test.common.ModTest;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityTest extends AnimatedEntity
{
    protected static AnimationHandler<EntityTest> animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest.class);

    static {
        EntityTest.animHandler.addAnim(ModTest.MODID, "position", "craftstudio_api_test", true);
        EntityTest.animHandler.addAnim(ModTest.MODID, "offset", "craftstudio_api_test", true);
        EntityTest.animHandler.addAnim(ModTest.MODID, "streching", "craftstudio_api_test", true);
    }
    
    public EntityTest(EntityType<? extends CreatureEntity> type, World par1World) {
        super(type, par1World);
    }

    @SuppressWarnings("unchecked")
	@Override
    public AnimationHandler<EntityTest> getAnimationHandler() {
        return EntityTest.animHandler;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(ModTest.MODID, "streching", this))
            this.getAnimationHandler().startAnimation(ModTest.MODID, "streching", this);
    }
}