package com.leviathanstudio.craftstudio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    private static final Logger LOGGER = LogManager.getLogger("CraftStudio");
    public static final String  API_ID = "craftstudioapi";
    static final String         NAME   = "CraftStudio API";

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event) {
        RegistryBuilder builder = new RegistryBuilder<CSReadedModel>();
        builder.setName(new ResourceLocation(CraftStudioApi.API_ID, "models"));
        builder.setType(CSReadedModel.class);
        builder.setIDRange(0, 4096);
        builder.create();
        builder = new RegistryBuilder<CSReadedAnim>();
        builder.setName(new ResourceLocation(CraftStudioApi.API_ID, "animations"));
        builder.setType(CSReadedAnim.class);
        builder.setIDRange(0, 4096);
        builder.create();
    }

    @SubscribeEvent
    public static void registerModels(RegistryEvent.Register<CSReadedModel> e) {

    }

    @EventHandler
    void preInit(FMLPreInitializationEvent event) {}

    @EventHandler
    void init(FMLInitializationEvent event) {
        // ProgressManager.ProgressBar progressBarModels;
        // progressBarModels = ProgressManager.push("Registry Models",
        // CSModelMesher.models.size());
        // for (Map.Entry<String, CSReadedModel> models :
        // CSModelMesher.models.entrySet())
        // progressBarModels.step("[" + models.getValue().modid + ":" +
        // models.getValue().name + "]");
        // ProgressManager.pop(progressBarModels);
        //
        // ProgressManager.ProgressBar progressBarAnim;
        // progressBarAnim = ProgressManager.push("Registry Animations",
        // CSAnimMesher.animations.size());
        // for (Map.Entry<String, CSReadedAnim> anims :
        // CSAnimMesher.animations.entrySet())
        // progressBarAnim.step("[" + anims.getValue().modid + ":" +
        // anims.getValue().name + "]");
        // ProgressManager.pop(progressBarAnim);
        //
        // CraftStudioApi.LOGGER.info(String.format("Loaded %s resources (%s
        // models / %s animations) from '%s'",
        // CSModelMesher.models.size() + CSAnimMesher.animations.size(),
        // CSModelMesher.models.size(), CSAnimMesher.animations.size(),
        // RegistryCraftStudio.getModid()));
    }

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }
}