package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.network.EndAnimationMessage;
import com.leviathanstudio.craftstudio.network.EndAnimationMessage.EndAnimationHandler;
import com.leviathanstudio.craftstudio.network.FireAnimationMessage;
import com.leviathanstudio.craftstudio.network.FireAnimationMessage.FireAnimationHandler;
import com.leviathanstudio.craftstudio.network.RFireAnimationMessage;
import com.leviathanstudio.craftstudio.network.RFireAnimationMessage.RFireAnimationHandler;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CSCommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        CraftStudioApi.NETWORK.registerMessage(FireAnimationHandler.class, FireAnimationMessage.class, 0, Side.CLIENT);
        CraftStudioApi.NETWORK.registerMessage(RFireAnimationHandler.class, RFireAnimationMessage.class, 1,
                Side.SERVER);
        CraftStudioApi.NETWORK.registerMessage(EndAnimationHandler.class, EndAnimationMessage.class, 2, Side.CLIENT);
    }

    public abstract AnimationHandler getNewAnimationHandler(IAnimated animated);
}
