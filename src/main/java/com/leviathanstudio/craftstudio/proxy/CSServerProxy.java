package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.server.animation.ServerAnimationHandler;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Server proxy of th CraftStudioApi
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 * @author ZeAmateis
 */
public class CSServerProxy extends CSCommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return new ServerAnimationHandler<>();
    }

}
