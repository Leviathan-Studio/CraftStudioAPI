package fr.zeamateis.test.anim.common;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import fr.zeamateis.test.anim.common.animations.AnimationHandlerTest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityBlockTest extends TileEntity implements IAnimated, ITickable
{
    protected ClientAnimationHandler animHandler = new AnimationHandlerTest(this);

    @Override
    public ClientAnimationHandler getAnimationHandler() {
        return this.animHandler;
    }

    @Override
    public void update() {
        if (!this.getAnimationHandler().isAnimationActive("block"))
            this.getAnimationHandler().startAnimation(Mod_Test.MODID, "block");
    }

}
