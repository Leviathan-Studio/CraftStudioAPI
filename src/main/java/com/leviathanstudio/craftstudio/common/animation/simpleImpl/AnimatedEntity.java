package com.leviathanstudio.craftstudio.common.animation.simpleImpl;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

/**
 * An abstract class that represent an animated entity. You can extends it or
 * use it as a model to create your own animated entity.
 *
 * @author Timmypote
 * @since 0.3.0
 */
public abstract class AnimatedEntity extends CreatureEntity implements IAnimated {
    /**
     * The animation handler of this type of entity.
     */
    // It should be different for every entity class, unless child classes have
    // the same models.
    // You should declare a new one in your extended classes.
    protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(AnimatedEntity.class);

    // Here you should add all the needed animations in the animationHandler.
    static {
        //AnimatedEntity.animHandler.addAnim("yourModId", "yourAnimation", "yourModel", false);
    }

    /**
     * The constructor of the entity.
     */
    public AnimatedEntity(EntityType<? extends CreatureEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    // You must call super.onLivingUpdate(), in your entity, or call the
    // animationsUpdate() method like here.

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void livingTick() {
        super.livingTick();
        this.getAnimationHandler().animationsUpdate(this);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
        // Be careful to return the right animation handler.
        return AnimatedEntity.animHandler;
    }

    @Override
    public DimensionType getDimension() {
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
