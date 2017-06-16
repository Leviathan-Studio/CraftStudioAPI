package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.common.network.ClientIAnimatedEventMessage;
import com.leviathanstudio.craftstudio.common.network.ClientIAnimatedEventMessage.ClientIAnimatedEventHandler;
import com.leviathanstudio.craftstudio.common.network.ServerIAnimatedEventMessage;
import com.leviathanstudio.craftstudio.common.network.ServerIAnimatedEventMessage.ServerIAnimatedEventHandler;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CSCommonProxy
{
    public void preInit(FMLPreInitializationEvent e) {
        CraftStudioApi.NETWORK.registerMessage(ClientIAnimatedEventHandler.class, ClientIAnimatedEventMessage.class, 0, Side.CLIENT);
        CraftStudioApi.NETWORK.registerMessage(ServerIAnimatedEventHandler.class, ServerIAnimatedEventMessage.class, 1, Side.SERVER);
    }

    public abstract <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass);
}
