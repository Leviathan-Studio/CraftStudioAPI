package com.leviathanstudio.craftstudio.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket<T> {

    void encode(T packet, PacketBuffer buffer);

    T decode(PacketBuffer buffer);

    void handle(T packet, Supplier<NetworkEvent.Context> ctxProvider);

}
