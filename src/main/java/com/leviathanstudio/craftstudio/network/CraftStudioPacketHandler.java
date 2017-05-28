package com.leviathanstudio.craftstudio.network;

import java.util.List;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class CraftStudioPacketHandler
{
    private static int                       id       = 0;

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CraftStudioApi.API_ID);

    public static int getNewId() {
        return CraftStudioPacketHandler.id++;
    }

    static void writeStringtoBuffer(ByteBuf buf, String str) {
        buf.writeInt(str.length());
        for (char c : str.toCharArray())
            buf.writeChar(c);
    }

    static String readStringFromBuffer(ByteBuf buf) {
        String str = "";
        int length = buf.readInt();
        for (int i = 0; i < length; i++)
            str = str + buf.readChar();
        return str;
    }

    static Entity getEntityByUUID(List<Entity> list, String uuid) {
        for (Entity e : list)
            if (e.getPersistentID().toString().equals(uuid))
                return e;
        return null;
    }
}
