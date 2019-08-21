package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * A helper class to easily send
 * {@link com.leviathanstudio.craftstudio.common.animation.IAnimated IAnimated}
 * event on client and server.
 *
 * @author Timmypote
 * @since 0.3.0
 */
public class CSNetworkHelper {
    /**
     * The range around the
     * {@link com.leviathanstudio.craftstudio.common.animation.IAnimated
     * IAnimated} the message will be received by clients.
     */
    public static final double EVENT_RANGE = 128;

    public static final String PROTOCOL_VERSION = String.valueOf(1);

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(CraftStudioApi.API_ID, "channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    private static int id = 0;

    /**
     * Send the given message through the network.
     *
     * @param message The message to send.
     */
    public static void sendIAnimatedEvent(AnimatedEventMessage message) {
        if (message.animated.isWorldRemote())
            // If we are on client, we send a server message to the server.
            sendPacketToServer(new AnimatedEventMessage(message));
        else
            // If we are on server, we send a client message to the clients in
            // range.
            sendToAllAround(new AnimatedEventMessage(message), new PacketDistributor.TargetPoint(
                    message.animated.getX(), message.animated.getY(), message.animated.getZ(), CSNetworkHelper.EVENT_RANGE, message.animated.getDimension()));
    }


    public static <M> SimpleChannel.MessageBuilder<M> messageBuilder(Class<M> packetIn) {
        return CHANNEL.messageBuilder(packetIn, id++);
    }

    public static void sendPacketToServer(AnimatedEventMessage packetIn) {
        CHANNEL.sendToServer(packetIn);
    }

    public static void sendPacketTo(AnimatedEventMessage packetIn, ServerPlayerEntity player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packetIn);
    }

    public static void sendPacketToEveryone(AnimatedEventMessage packetIn) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packetIn);
    }

    public static void sendPacketToDimension(AnimatedEventMessage packetIn, DimensionType dimension) {
        CHANNEL.send(PacketDistributor.DIMENSION.with(() -> dimension), packetIn);
    }

    public static void sendToAllAround(AnimatedEventMessage packetIn, PacketDistributor.TargetPoint targetPoint) {
        CHANNEL.send(PacketDistributor.NEAR.with(() -> targetPoint), packetIn);

    }
}
