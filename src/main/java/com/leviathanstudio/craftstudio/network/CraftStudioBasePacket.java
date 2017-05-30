package com.leviathanstudio.craftstudio.network;

import java.util.List;
import java.util.UUID;

import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import io.netty.buffer.ByteBuf;

public class CraftStudioBasePacket implements IMessage
{
    protected String animationName;
    protected UUID   uuid;

    public CraftStudioBasePacket()
    {
    }

    public CraftStudioBasePacket(String animationNameIn, IAnimated animated)
    {
        this.animationName = animationNameIn;
        this.uuid = animated.getUUID();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.animationName);
        buf.writeLong(this.uuid.getMostSignificantBits());
        buf.writeLong(this.uuid.getLeastSignificantBits());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.animationName);
        long most = buf.readLong();
        long least = buf.readLong();
        this.uuid = new UUID(most, least);
    }

    public Entity getEntityByUUID(List<Entity> list, UUID uuid)
    {
        for (Entity e : list)
            if (e.getPersistentID().equals(uuid))
                return e;
        return null;
    }

}
