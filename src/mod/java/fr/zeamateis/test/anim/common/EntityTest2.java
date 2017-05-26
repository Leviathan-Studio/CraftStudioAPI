package fr.zeamateis.test.anim.common;

import com.leviathanstudio.craftstudio.common.IAnimated;

import fr.zeamateis.test.anim.common.animations.AnimationHandlerTest;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityTest2 extends EntityAnimal implements IAnimated
{
    protected AnimationHandlerTest animHandler;
    protected boolean              fanOpen = true;

    public EntityTest2(World par1World) {
        super(par1World);
        this.setSize(1.0F, 1.5F);
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIPanic(this, 2.2));
        this.getAnimationHandler().startAnimation(Mod_Test.MODID, "custom");
        this.initEntityAI();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
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
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "close_fan")
                && !this.getAnimationHandler().isAnimationActive(Mod_Test.MODID, "open_fan"))
            if (this.fanOpen) {
                this.getAnimationHandler().startAnimation(Mod_Test.MODID, "close_fan");
                this.fanOpen = false;
            }
            else {
                this.getAnimationHandler().startAnimation(Mod_Test.MODID, "open_fan");
                this.fanOpen = true;
            }

        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}
