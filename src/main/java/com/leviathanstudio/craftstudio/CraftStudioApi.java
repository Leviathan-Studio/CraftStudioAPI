package com.leviathanstudio.craftstudio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.proxy.CSCommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * Main class of the CraftStudioApi
 * 
 * @since 0.3.0
 *
 * @author ZeAmateis
 * @author Timmypote
 */
@Mod(modid = CraftStudioApi.API_ID, name = CraftStudioApi.NAME, updateJSON = "https://leviathan-studio.com/craftstudioapi/update.json",
    version = "1.0.0",
    acceptedMinecraftVersions = "1.12")
public class CraftStudioApi
{
    private static final Logger              LOGGER  = LogManager.getLogger("CraftStudio");
    public static final String               API_ID  = "craftstudioapi";
    static final String                      NAME    = "CraftStudio API";

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CraftStudioApi.API_ID);

    @SidedProxy(clientSide = "com.leviathanstudio.craftstudio.proxy.CSClientProxy", serverSide = "com.leviathanstudio.craftstudio.proxy.CSServerProxy")
    private static CSCommonProxy             proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CraftStudioApi.proxy.preInit(event);
    }

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }

    /**
     * Helper to create an AnimationHandler to registry animation to your
     * entity/block
     *
     * @param <T>
     *
     * @param animated
     *            Class which implements IAnimated (Entity or TileEntity)
     */
    public static <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return CraftStudioApi.proxy.getNewAnimationHandler(animatedClass);

    }
}