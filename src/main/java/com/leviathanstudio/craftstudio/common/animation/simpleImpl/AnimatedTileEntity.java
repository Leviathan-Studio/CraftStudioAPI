package com.leviathanstudio.craftstudio.common.animation.simpleImpl;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.test.common.TileEntityTest;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.DimensionManager;

public abstract class AnimatedTileEntity extends TileEntity implements IAnimated, ITickable
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(AnimatedTileEntity.class);
    
    static {
        //AnimatedTileEntity.animHandler.addAnim("yourModId", "yourAnimation", "yourModel", false);
    }
    
    public AnimatedTileEntity() {
        super();
    }
    
    @Override
    public void update() {
        AnimatedTileEntity.animHandler.animationsUpdate(this);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
        return AnimatedTileEntity.animHandler;
    }

    @Override
    public int getDimension() {
        Integer[] ids = DimensionManager.getIDs();
        for(int i: ids)
            if (DimensionManager.getWorld(i) == this.world)
                return i;
        return 0;
    }

    @Override
    public double getX() {
        return this.pos.getX();
    }

    @Override
    public double getY() {
        return this.pos.getY();
    }

    @Override
    public double getZ() {
        return this.pos.getZ();
    }

    @Override
    public boolean isWorldRemote() {
        return this.world.isRemote;
    }

}
