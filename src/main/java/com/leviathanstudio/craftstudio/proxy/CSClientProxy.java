package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Client proxy of th CraftStudioApi
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 * @author ZeAmateis
 */
public class CSClientProxy extends CSCommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return new ClientAnimationHandler<>();
    }

}
