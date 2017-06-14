package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class EntityTest4 extends EntityCreature implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityTest4.class);

    static {
        EntityTest4.animHandler.addAnim(Mod_Test.MODID, "rotation", "craftstudio_api_test2", true);
    }

    public EntityTest4(World par1World) {
        super(par1World);
    }

    // Getter for animation handler
    @Override
    public AnimationHandler getAnimationHandler() {
        return EntityTest4.animHandler;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.getAnimationHandler().animationsUpdate(this);
        
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "rotation", this))
            this.getAnimationHandler().clientStartAnimation(Mod_Test.MODID, "rotation", this);
    }

    @Override
    public UUID getUUID() {
        return this.getPersistentID();
    }
}
