package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class CSNetworkHelper
{
    public static final double EVENT_RANGE = 100;

    public static void sendIAnimatedEvent(IAnimatedEventMessage message) {
        if (message.animated.isWorldRemote())
            CraftStudioApi.NETWORK.sendToServer(new ServerIAnimatedEventMessage(message));
        else
            CraftStudioApi.NETWORK.sendToAllAround(new ClientIAnimatedEventMessage(message), new TargetPoint(message.animated.getDimension(),
                    message.animated.getX(), message.animated.getY(), message.animated.getZ(), EVENT_RANGE));
    }
}
