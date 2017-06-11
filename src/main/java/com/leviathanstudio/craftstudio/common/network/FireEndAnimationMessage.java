package com.leviathanstudio.craftstudio.common.network;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.client.animation.ClientChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FireEndAnimationMessage extends FireAnimationMessage
{
    public FireEndAnimationMessage() {}

    protected short endAnimId;

    public FireEndAnimationMessage(String animationToStart, IAnimated animated, float startingKeyframeIn, String animationToEnd) {
        super(animationToStart, animated, startingKeyframeIn);
        this.endAnimId = animated.getAnimationHandler().getAnimIdFromName(animationToEnd);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeShort(this.endAnimId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.endAnimId = buf.readShort();
    }

    public static class FireEndAnimationHandler implements IMessageHandler<FireEndAnimationMessage, RFireAnimationMessage>
    {
        @Override
        public RFireAnimationMessage onMessage(FireEndAnimationMessage message, MessageContext ctx) {
            Entity entity = message.getEntityByUUID(Minecraft.getMinecraft().theWorld.loadedEntityList, message.uuid);
            if (entity != null && entity instanceof IAnimated) {
                IAnimated animated = (IAnimated) entity;
                ClientAnimationHandler handler = (ClientAnimationHandler) animated.getAnimationHandler();
                String animToStop = animated.getAnimationHandler().getAnimNameFromId(message.endAnimId);
                String animToStart = animated.getAnimationHandler().getAnimNameFromId(message.animationId);
                handler.clientStopAnimation(animToStop, animated);
                handler.clientStartAnimation(animToStart, message.startingKeyframe, animated);
                Object infoChannel = ((ClientAnimationHandler) animated.getAnimationHandler()).getAnimChannels().get(animToStart);
                if (infoChannel instanceof ClientChannel)
                    return new RFireAnimationMessage(animToStart, animated, ((ClientChannel) infoChannel).totalFrames);
                return new RFireAnimationMessage(animToStart, animated, 0);
            }
            return null;
        }

    }
}
