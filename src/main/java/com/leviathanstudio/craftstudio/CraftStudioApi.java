package com.leviathanstudio.craftstudio;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.proxy.CSCommonProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class of the CraftStudioApi
 *
 * @author ZeAmateis
 * @author Timmypote
 */
@Mod.EventBusSubscriber
@Mod(modid = CraftStudioApi.API_ID, name = CraftStudioApi.NAME, version = "0.1-beta", updateJSON = "https://leviathan-studio.com/craftstudioapi/update.json", acceptedMinecraftVersions = "1.11.2")
public class CraftStudioApi
{
    private static final Logger              LOGGER  = LogManager.getLogger("CraftStudio");
    public static final String               API_ID  = "craftstudioapi";
    static final String                      NAME    = "CraftStudio API";

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CraftStudioApi.API_ID);

    @SidedProxy(clientSide = "com.leviathanstudio.craftstudio.proxy.CSClientProxy", serverSide = "com.leviathanstudio.craftstudio.proxy.CSServerProxy")
    private static CSCommonProxy             proxy;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void createRegistries(RegistryEvent.NewRegistry event)
    {
        RegistryBuilder builder = new RegistryBuilder<CSReadedModel>();
        builder.setName(new ResourceLocation(CraftStudioApi.API_ID, "cs_models"));
        builder.setType(CSReadedModel.class);
        builder.setIDRange(0, 4096);
        builder.create();
        builder = new RegistryBuilder<CSReadedAnim>();
        builder.setName(new ResourceLocation(CraftStudioApi.API_ID, "model_animations"));
        builder.setType(CSReadedAnim.class);
        builder.setIDRange(0, 4096);
        builder.create();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    @SideOnly(Side.CLIENT)
    public static void registerModels(RegistryEvent.Register<CSReadedModel> e)
    {
        CSRegistryHelper.loadModels();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    @SideOnly(Side.CLIENT)
    public static void registerAnims(RegistryEvent.Register<CSReadedAnim> e)
    {
        CSRegistryHelper.loadAnims();
    }

    @EventHandler
    void preInit(FMLPreInitializationEvent event)
    {
        CraftStudioApi.proxy.preInit(event);
    }

    public static Logger getLogger()
    {
        return CraftStudioApi.LOGGER;
    }

    /**
     * Helper to create an AnimationHandler to registry animation to your
     * entity/block
     *
     * @param animated
     *            Class whiches implements IAnimated (Entity or TileEntity)
     */
    public static AnimationHandler getNewAnimationHandler(IAnimated animated)
    {
        return CraftStudioApi.proxy.getNewAnimationHandler(animated);

    }
}