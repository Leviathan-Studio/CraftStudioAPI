package com.leviathanstudio.craftstudio.common.animation.simpleImpl;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public abstract class AnimatedEntity extends EntityCreature implements IAnimated
{
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(AnimatedEntity.class);
    
    static {
        //AnimatedEntity.animHandler.addAnim("yourModId", "yourAnimation", "yourModel", false);
    }
    
    public AnimatedEntity(World worldIn) {
        super(worldIn);
    }
    
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        this.getAnimationHandler().animationsUpdate(this);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
        return AnimatedEntity.animHandler;
    }

    @Override
    public int getDimension() {
        return this.dimension;
    }

    @Override
    public double getX() {
        return this.posX;
    }

    @Override
    public double getY() {
        return this.posY;
    }

    @Override
    public double getZ() {
        return this.posZ;
    }

    @Override
    public boolean isWorldRemote() {
        return this.world.isRemote;
    }
}
