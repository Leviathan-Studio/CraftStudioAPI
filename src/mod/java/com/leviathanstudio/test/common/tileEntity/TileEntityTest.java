package com.leviathanstudio.test.common.tileEntity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedTileEntity;
import com.leviathanstudio.test.common.ModTest;
import com.leviathanstudio.test.common.RegistryHandler;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;

public class TileEntityTest extends AnimatedTileEntity
{
    protected static AnimationHandler<TileEntityTest> animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityTest.class);

    static {
        TileEntityTest.animHandler.addAnim(ModTest.MODID, "position", "craftstudio_api_test", true);
    }

    public TileEntityTest(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    
    public TileEntityTest() {
    	super(RegistryHandler.tile_test);
    }

    public TileEntityTest(World worldIn) {
        super(RegistryHandler.tile_test);
        this.world = worldIn;
    }

    @SuppressWarnings("unchecked")
	@Override
    public AnimationHandler<TileEntityTest> getAnimationHandler() {
        return TileEntityTest.animHandler;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isWorldRemote() && !this.getAnimationHandler().isAnimationActive(ModTest.MODID, "position", this))
            this.getAnimationHandler().startAnimation(ModTest.MODID, "position", this);

    }
}