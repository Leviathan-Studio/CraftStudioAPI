package com.leviathanstudio.craftstudio.common.animation.simpleImpl;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.dimension.DimensionType;

/**
 * An abstract class that represent a animated TileEntity. You should extends it
 * and not create your own, or be careful to implement all the methods of this
 * class.
 *
 * @author Timmypote
 * @since 0.3.0
 */
public abstract class AnimatedTileEntity extends TileEntity implements IAnimated, ITickableTileEntity {
    /**
     * The animation handler of this type of tile entity.
     */
    // It should be different for every entity class, unless child classes have
    // the same models.
    // You should declare a new one in your extended classes.
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(AnimatedTileEntity.class);

    // Here you should add all the needed animations in the animationHandler.
    static {
        //AnimatedTileEntity.animHandler.addAnim("yourModId", "yourAnimation", "yourModel", false);
    }

    public AnimatedTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    /**
     * The constructor of the tile entity.
     */


    // You must call super.update() at the beginning of your update() method,
    // or call the animationsUpdate() method like here.
    @Override
    public void tick() {
        this.getAnimationHandler().animationsUpdate(this);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
        // Be careful to return the right animation handler.
        return AnimatedTileEntity.animHandler;
    }

    @Override
    public DimensionType getDimension() {
        return this.world.getDimension().getType();
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

    // Here to prevent bugs on the integrated server.
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.pos.getX();
        result = prime * result + this.pos.getY();
        result = prime * result + this.pos.getZ();
        return result;
    }

    // Here to prevent bugs on the integrated server.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
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
