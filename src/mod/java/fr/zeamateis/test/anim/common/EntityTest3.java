package fr.zeamateis.test.anim.common;

import com.leviathanstudio.craftstudio.common.IAnimated;

import fr.zeamateis.test.anim.common.animations.AnimationHandlerTest;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class EntityTest3 extends EntityCreature implements IAnimated
{
    protected AnimationHandlerTest animHandler;

    public EntityTest3(World par1World) {
        super(par1World);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    // Getter for animation handler
    @Override
    public AnimationHandlerTest getAnimationHandler() {
        if (this.animHandler == null)
            this.animHandler = new AnimationHandlerTest(this);
        return this.animHandler;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        // Activate the animation in ticking method
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "fly"))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "fly");
    }
}
