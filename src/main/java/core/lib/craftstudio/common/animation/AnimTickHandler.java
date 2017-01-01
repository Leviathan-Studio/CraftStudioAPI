package lib.craftstudio.common.animation;

import java.util.LinkedList;

import lib.craftstudio.common.IAnimated;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimTickHandler
{
    private final LinkedList<IAnimated> activeEntities    = new LinkedList<IAnimated>();
    private final LinkedList<IAnimated> removableEntities = new LinkedList<IAnimated>();

    public AnimTickHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addEntity(IAnimated entity) {
        this.activeEntities.add(entity);
    }

    // Called when the client ticks.
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!this.activeEntities.isEmpty())
            if (event.phase == Phase.START) {
                for (final IAnimated entity : this.activeEntities) {
                    entity.getAnimationHandler().animationsUpdate();

                    if (entity instanceof Entity)
                        if (((Entity) entity).isDead)
                            this.removableEntities.add(entity);
                }
            }
        for (final IAnimated entity : this.removableEntities)
            this.activeEntities.remove(entity);
        this.removableEntities.clear();
    }

    // Called when the server ticks. Usually 20 ticks a second.
    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (!this.activeEntities.isEmpty())
            if (event.phase == Phase.START) {
                for (final IAnimated entity : this.activeEntities) {
                    entity.getAnimationHandler().animationsUpdate();

                    if (entity instanceof Entity)
                        if (((Entity) entity).isDead)
                            this.removableEntities.add(entity);
                }
                for (final IAnimated entity : this.removableEntities)
                    this.activeEntities.remove(entity);
                this.removableEntities.clear();
            }
    }

    // Called when a new frame is displayed (See fps)
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderTick(TickEvent.RenderTickEvent event) {}

    // Called when the world ticks
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {}
}