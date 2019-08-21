package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.common.network.AnimatedEventMessage;
import com.leviathanstudio.craftstudio.common.network.AnimatedEventMessageUtil;
import com.leviathanstudio.craftstudio.common.network.CSNetworkHelper;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Common base for the proxies of the CraftStudioApi
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
public abstract class CSCommonProxy {

    public void clientSetup(FMLClientSetupEvent event) {
    }

    //TODO Register packets
    public void commonSetup(FMLCommonSetupEvent event) {
    	CSNetworkHelper.CHANNEL.registerMessage(1, AnimatedEventMessage.class, AnimatedEventMessageUtil.ENCODER, AnimatedEventMessageUtil.DECODER, AnimatedEventMessageUtil.HANDLER);
        //CSNetworkHelper.CHANNEL.registerMessage(ClientIAnimatedEventHandler.class, ClientIAnimatedEventMessage.class, 0, Side.CLIENT);
        //CraftStudioApi.NETWORK.registerMessage(ServerIAnimatedEventHandler.class, ServerIAnimatedEventMessage.class, 1, Side.SERVER);
    }

    public abstract <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass);
}
