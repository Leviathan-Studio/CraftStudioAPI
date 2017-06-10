package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityTest extends TileEntity implements IAnimated
{
    static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityTest.class);

    public TileEntityTest(World worldIn) {
        this.world = worldIn;
        animHandler.addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
        animHandler.startAnimation(Mod_Test.MODID, "position", this);
    }

    @Override
    public AnimationHandler getAnimationHandler() {
        return this.animHandler;
    }

    @Override
    public UUID getUUID() {
        return this.getUUID();
    }

}