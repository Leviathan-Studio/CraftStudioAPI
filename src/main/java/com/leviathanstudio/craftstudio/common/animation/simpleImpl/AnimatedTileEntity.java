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
        this.getAnimationHandler().animationsUpdate(this);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
        return AnimatedTileEntity.animHandler;
    }

    @Override
    public int getDimension() {
        return this.world.provider.getDimension();
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.pos.getX();
        result = prime * result + this.pos.getY();
        result = prime * result + this.pos.getZ();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnimatedTileEntity other = (AnimatedTileEntity) obj;
        if (this.pos.getX() != other.pos.getX())
            return false;
        if (this.pos.getY() != other.pos.getY())
            return false;
        if (this.pos.getZ() != other.pos.getZ())
            return false;
        return true;
    }

}
