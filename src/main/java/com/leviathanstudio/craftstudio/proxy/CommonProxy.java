package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.network.CraftStudioPacketHandler;
import com.leviathanstudio.craftstudio.network.FireAnimationMessage;
import com.leviathanstudio.craftstudio.network.FireAnimationMessage.FireAnimationHandler;
import com.leviathanstudio.craftstudio.network.RFireAnimationMessage;
import com.leviathanstudio.craftstudio.network.RFireAnimationMessage.RFireAnimationHandler;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e) {
        CraftStudioPacketHandler.INSTANCE.registerMessage(FireAnimationHandler.class, FireAnimationMessage.class, CraftStudioPacketHandler.getNewId(),
                Side.CLIENT);
        CraftStudioPacketHandler.INSTANCE.registerMessage(RFireAnimationHandler.class, RFireAnimationMessage.class,
                CraftStudioPacketHandler.getNewId(), Side.SERVER);
    }

    public void init(FMLInitializationEvent e) {

    }

    public abstract AnimationHandler getNewAnimationHandler(IAnimated animated);
}
