package com.leviathanstudio.test.common;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityTest3 extends AnimatedEntity
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest3.class);
    boolean                           fly         = false;

    static {
        EntityTest3.animHandler.addAnim(Mod_Test.MODID, "fly", "dragon_brun", true);
        EntityTest3.animHandler.addAnim(Mod_Test.MODID, "idle", "dragon_brun", true);
    }

    public EntityTest3(World par1World) {
        super(par1World);
    }

    @Override
    public AnimationHandler getAnimationHandler() {
        return EntityTest3.animHandler;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!this.fly)
            this.fly = true;
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "fly", this) && this.fly)
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "fly", this);

    }
}
