package fr.zeamateis.test.anim.common;

import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;

import fr.zeamateis.test.anim.common.animations.AnimationHandlerTest;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class EntityTest extends EntityCreature implements IAnimated
{
    protected AnimationHandler animHandler = new AnimationHandlerTest(this);

    public EntityTest(World par1World)
    {
        super(par1World);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    // Getter for animation handler
    @Override
    public AnimationHandler getAnimationHandler()
    {
        return this.animHandler;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        // Activate the animation in ticking method
        if (!this.getAnimationHandler().isAnimationActive("idle"))
            this.getAnimationHandler().executeAnimation("idle", 0);
    }

}