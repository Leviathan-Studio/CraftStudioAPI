package com.leviathanstudio.craftstudio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

/**
 * Main class of the CraftStudioApi
 *
 * @author ZeAmateis
 * @author Timmypote
 */
@Mod.EventBusSubscriber
@Mod(modid = CraftStudioApi.API_ID, name = CraftStudioApi.NAME, version = "0.1-alpha", updateJSON = "https://leviathan-studio.com/craftstudioapi/update.json", acceptedMinecraftVersions = "1.11.2")
public class CraftStudioApi
{
    private static final Logger                LOGGER = LogManager.getLogger("CraftStudio");
    public static final String                 API_ID = "craftstudioapi";
    static final String                        NAME   = "CraftStudio API";

    private static ProgressManager.ProgressBar progressBarModels;

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event) {
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
    public static void endProgressBar(RegistryEvent.Register<CSReadedModel> e) {
        CSRegistryHelper.loadModels();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerAnims(RegistryEvent.Register<CSReadedAnim> e) {
        CSRegistryHelper.loadAnims();
    }

    @EventHandler
    void preInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    void init(FMLInitializationEvent event) {}

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }
}