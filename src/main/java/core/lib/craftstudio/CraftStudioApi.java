package lib.craftstudio;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lib.craftstudio.client.CSModelMesher;
import lib.craftstudio.client.json.CSJsonReader;
import lib.craftstudio.client.json.CSReadedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = CraftStudioApi.API_ID, name = CraftStudioApi.NAME, version = CraftStudioApi.VERSION, updateJSON = "http://leviathan-studio.com/amateis/craftstudio-converter/update.json")
public class CraftStudioApi
{
    private static final Logger LOGGER = LogManager.getLogger("CraftStudio");

    public static final String  API_ID = "craftstudioapi", NAME = "CraftStudio API", VERSION = "1.1";

    @EventHandler
    void load(FMLInitializationEvent event) {
        ProgressManager.ProgressBar progressBar = ProgressManager.push("Registry Models", CSModelMesher.models.size());
        for (Map.Entry<String, CSReadedModel> models : CSModelMesher.models.entrySet())
            progressBar.step("[" + models.getValue().modid + ":" + models.getValue().name + "]");
        ProgressManager.pop(progressBar);
    }

    public static void registerModel(ResourceLocation resourceIn, String modelNameIn) {
        CSJsonReader jsonReader = new CSJsonReader(resourceIn);
        if (resourceIn.getResourceDomain() != CraftStudioApi.API_ID)
            CSModelMesher.models.put(modelNameIn, jsonReader.readModel());
        else
            CraftStudioApi.LOGGER.fatal("Your not allowed to use the \"craftstudioapi\" to register CraftStudio models.");
    }

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }
}