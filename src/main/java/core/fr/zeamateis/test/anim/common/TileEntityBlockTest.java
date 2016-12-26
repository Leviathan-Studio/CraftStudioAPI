package fr.zeamateis.test.anim.common;

import lib.craftstudio.common.IAnimated;
import lib.craftstudio.common.animation.AnimationHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityBlockTest extends TileEntity implements IAnimated, ITickable
{
    protected AnimationHandler animHandler = new Mod_Test.AnimationHandlerTest(this);

    @Override
    public AnimationHandler getAnimationHandler() {
        return this.animHandler;
    }

    @Override
    public void update() {
        if (!this.getAnimationHandler().isAnimationActive("block"))
            this.getAnimationHandler().executeAnimation("block", 0);
    }

}
