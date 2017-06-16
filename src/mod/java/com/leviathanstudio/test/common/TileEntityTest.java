package com.leviathanstudio.test.common;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedTileEntity;

import net.minecraft.world.World;

public class TileEntityTest extends AnimatedTileEntity
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityTest.class);

    static {
        TileEntityTest.animHandler.addAnim(Mod_Test.MODID, "position", "craftstudio_api_test", true);
    }

    public TileEntityTest() {
        super();
    }

    public TileEntityTest(World worldIn) {
        this();
        this.world = worldIn;
    }

    @Override
    public AnimationHandler getAnimationHandler() {
        return TileEntityTest.animHandler;
    }

    @Override
    public void update() {
        super.update();

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "position", this))
            this.getAnimationHandler().clientStartAnimation(Mod_Test.MODID, "position", this);

    }
}