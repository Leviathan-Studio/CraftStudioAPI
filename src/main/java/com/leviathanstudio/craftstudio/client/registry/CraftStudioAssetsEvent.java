package com.leviathanstudio.craftstudio.client.registry;

import net.minecraftforge.eventbus.api.Event;

public class CraftStudioAssetsEvent extends Event {


    /**
     * Determine if this function is cancelable at all.
     *
     * @return If access to setCanceled should be allowed
     * <p>
     * Note:
     * Events with the Cancelable annotation will have this method automatically added to return true.
     */
    @Override
    public boolean isCancelable() {
        return false;
    }
}
