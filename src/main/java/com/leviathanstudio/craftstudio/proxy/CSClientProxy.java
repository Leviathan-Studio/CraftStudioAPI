package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client proxy of the CraftStudioApi
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
public class CSClientProxy extends CSCommonProxy {


    @Override
    public void clientSetup(FMLClientSetupEvent e) {
        super.clientSetup(e);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return new ClientAnimationHandler<>();
    }

}
