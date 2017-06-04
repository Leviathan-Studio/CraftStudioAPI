package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.profiler.Profiler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CSClientProxy extends CSCommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

	@Override
	public <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
		return new ClientAnimationHandler<T>();
	}

    
}
