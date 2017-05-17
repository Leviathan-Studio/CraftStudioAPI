package com.leviathanstudio.craftstudio;

import java.util.Map;

import com.leviathanstudio.craftstudio.client.CSAnimMesher;
import com.leviathanstudio.craftstudio.client.CSModelMesher;
import com.leviathanstudio.craftstudio.client.CraftStudioModelNotFound;
import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.util.VersionChecker;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Main class of the CraftStudioApi
 * @author ZeAmateis
 * @author Timmypote
 */
@Mod(modid = CraftStudioApi.API_ID, name = CraftStudioApi.NAME, version = CraftStudioApi.ACTUAL_VERSION)
public class CraftStudioApi
{
    private static final Logger LOGGER         = LogManager.getLogger("CraftStudio");

    public static final String  API_ID         = "craftstudioapi";
    public static final String  NAME           = "CraftStudio API";
    public static final String  ACTUAL_VERSION = "1.1";

    private VersionChecker      versionChecker = new VersionChecker();

    @EventHandler
    void preInit(FMLPreInitializationEvent event) throws Exception
    {
        this.versionChecker.preInit();
    }

    @EventHandler
    void load(FMLInitializationEvent event)
    {
        ProgressManager.ProgressBar progressBar = ProgressManager.push("Registry Models", CSModelMesher.models.size());
        for (Map.Entry<String, CSReadedModel> models : CSModelMesher.models.entrySet())
            progressBar.step("[" + models.getValue().modid + ":" + models.getValue().name + "]");
        ProgressManager.pop(progressBar);
    }

    /**
     * Register a new model with the name given.
     * @param resourceIn The location of the .csjsmodel.
     * @param modelNameIn The name given to the model.
     */
    public static void registerModel(ResourceLocation resourceIn, String modelNameIn)
    {
        CSJsonReader jsonReader;
        try
        {
            jsonReader = new CSJsonReader(resourceIn);

            if (resourceIn.getResourceDomain() != CraftStudioApi.API_ID)
                CSModelMesher.models.put(modelNameIn, jsonReader.readModel());
            else
                CraftStudioApi.LOGGER
                        .fatal("Your not allowed to use the \"craftstudioapi\" to register CraftStudio models.");
        } catch (CraftStudioModelNotFound e)
        {
            CraftStudioApi.LOGGER.error(e.getMessage());
        }
    }
    
    /**
     * Register a new animation with the name given.
     * @param resourceIn The location of the .csjsmodelanim.
     * @param animNameIn The name given to the animation.
     */
    public static void registerAnim(ResourceLocation resourceIn, String animNameIn){
    	CSJsonReader jsonReader;
        try
        {
            jsonReader = new CSJsonReader(resourceIn);

            if (resourceIn.getResourceDomain() != CraftStudioApi.API_ID)
                CSAnimMesher.animations.put(animNameIn, jsonReader.readAnim());
            else
                CraftStudioApi.LOGGER
                        .fatal("Your not allowed to use the \"craftstudioapi\" to register CraftStudio animations.");
        } catch (CraftStudioModelNotFound e)
        {
            CraftStudioApi.LOGGER.error(e.getMessage());
        }
    }

    public static Logger getLogger()
    {
        return CraftStudioApi.LOGGER;
    }
}