package com.leviathanstudio.test.common;

import java.util.UUID;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityTest extends TileEntity implements IAnimated, ITickable
{
    static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityTest.class);

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
    public UUID getUUID() {
        // this.getPos();
        return null;
    }

    @Override
    public void update() {
        this.getAnimationHandler().animationsUpdate(this);
        
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "position", this))
            this.getAnimationHandler().clientStartAnimation(Mod_Test.MODID, "position", this);
        // Don't use startAnimation() on TileEntity for now
    }

}