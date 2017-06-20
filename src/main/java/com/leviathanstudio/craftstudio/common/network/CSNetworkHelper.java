package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * A helper class to easily send
 * {@link com.leviathanstudio.craftstudio.common.animation.IAnimated IAnimated}
 * event on client and server.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
public class CSNetworkHelper
{
    /**
     * The range around the
     * {@link com.leviathanstudio.craftstudio.common.animation.IAnimated
     * IAnimated} the message will be received by clients.
     */
    public static final double EVENT_RANGE = 128;

    /**
     * Send the given message through the network.
     * 
     * @param message
     *            The message to send.
     */
    public static void sendIAnimatedEvent(IAnimatedEventMessage message) {
        if (message.animated.isWorldRemote())
            // If we are on client, we send a server message to the server.
            CraftStudioApi.NETWORK.sendToServer(new ServerIAnimatedEventMessage(message));
        else
            // If we are on server, we send a client message to the clients in
            // range.
            CraftStudioApi.NETWORK.sendToAllAround(new ClientIAnimatedEventMessage(message), new TargetPoint(message.animated.getDimension(),
                    message.animated.getX(), message.animated.getY(), message.animated.getZ(), CSNetworkHelper.EVENT_RANGE));
    }
}
