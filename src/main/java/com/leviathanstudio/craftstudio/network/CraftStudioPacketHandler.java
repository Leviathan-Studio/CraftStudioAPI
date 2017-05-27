package com.leviathanstudio.craftstudio.network;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class CraftStudioPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CraftStudioApi.API_ID);

}
