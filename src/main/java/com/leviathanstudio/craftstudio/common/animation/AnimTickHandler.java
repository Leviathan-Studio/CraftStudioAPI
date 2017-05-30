package com.leviathanstudio.craftstudio.common.animation;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimTickHandler
{
    private final List<IAnimated> activeAnimated    = new LinkedList<>();
    private final List<IAnimated> removableAnimated = new LinkedList<>();

    public AnimTickHandler()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addAnimated(IAnimated entity)
    {
        this.activeAnimated.add(entity);
    }

    // Called when the client ticks.
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (!this.activeAnimated.isEmpty())
            if (event.phase == Phase.START)
                for (final IAnimated entity : this.activeAnimated)
                {
                    entity.getAnimationHandler().animationsUpdate();

                    if (entity instanceof Entity)
                        if (((Entity) entity).isDead)
                            this.removableAnimated.add(entity);
                }
        for (final IAnimated entity : this.removableAnimated)
            this.activeAnimated.remove(entity);
        this.removableAnimated.clear();
    }

    // Called when the server ticks. Usually 20 ticks a second.
    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if (!this.activeAnimated.isEmpty())
            if (event.phase == Phase.START)
            {
                for (final IAnimated entity : this.activeAnimated)
                {
                    entity.getAnimationHandler().animationsUpdate();

                    if (entity instanceof Entity)
                        if (((Entity) entity).isDead)
                            this.removableAnimated.add(entity);
                }
                for (final IAnimated entity : this.removableAnimated)
                    this.activeAnimated.remove(entity);
                this.removableAnimated.clear();
            }
    }

    // // Called when a new frame is displayed (See fps)
    // @SubscribeEvent
    // @SideOnly(Side.CLIENT)
    // public void onRenderTick(TickEvent.RenderTickEvent event) {}
    //
    // // Called when the world ticks
    // @SubscribeEvent
    // public void onWorldTick(TickEvent.WorldTickEvent event) {}
}