package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.tileentity.TileEntity;

public class TileEntityTest extends TileEntity implements IAnimated
{
    private AnimationHandler animHandler;

    public TileEntityTest() {
        this.animHandler = CraftStudioApi.getNewAnimationHandler(this, this.world);
        this.animHandler.addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
        this.animHandler.startAnimation(Mod_Test.MODID, "position");
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