package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.server.animation.ServerAnimationHandler;

/**
 * Server proxy of the CraftStudioApi
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
public class CSServerProxy extends CSCommonProxy {

    @Override
    public <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return new ServerAnimationHandler<>();
    }

}
