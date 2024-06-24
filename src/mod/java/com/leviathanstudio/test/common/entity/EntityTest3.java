package com.leviathanstudio.test.common.entity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedEntity;
import com.leviathanstudio.test.common.ModTest;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EntityTest3 extends AnimatedEntity
{
    protected static AnimationHandler<EntityTest3> animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest3.class);
    boolean                           fly         = false;

    static {
        EntityTest3.animHandler.addAnim(ModTest.MODID, "fly", "dragon_brun", true);
        EntityTest3.animHandler.addAnim(ModTest.MODID, "idle", "dragon_brun", true);
    }

    public EntityTest3(EntityType<? extends AnimatedEntity> type, World par1World) {
        super(type, par1World);
    }

    @SuppressWarnings("unchecked")
	@Override
    public AnimationHandler<EntityTest3> getAnimationHandler() {
        return EntityTest3.animHandler;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        if (!this.fly)
            this.fly = true;
        return true;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.getAnimationHandler().isAnimationActive(ModTest.MODID, "fly", this) && this.fly)
            this.getAnimationHandler().networkStartAnimation(ModTest.MODID, "fly", this);

    }
}
