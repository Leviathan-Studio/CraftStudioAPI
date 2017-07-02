package com.leviathanstudio.craftstudio.client.registry;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.exception.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.client.exception.CSResourceNotFoundException;
import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.client.util.EnumRenderType;
import com.leviathanstudio.craftstudio.client.util.EnumResourceType;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class containing useful methods to register models and animations.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 * @author ZeAmateis
 */
@SideOnly(Side.CLIENT)
public class CSRegistryHelper
{
    private String                   modid;

    private static List<LoadElement> loadModelList = new ArrayList();
    private static List<LoadElement> loadAnimList  = new ArrayList();

    /**
     * Constructor for the registry
     *
     * @param modid
     *            Define the ID of your mod
     */
    public CSRegistryHelper(String modid) {
        this.modid = modid;
    }

    /**
     * Pre-register your resource.
     *
     * @param resourceTypeIn
     *            Set your resource type, <br>
     *            {@link EnumResourceType#ANIM} for animation,<br>
     *            {@link EnumResourceType#MODELS} for models <br>
     *            <br>
     * @param renderTypeIn
     *            Set your render type, <br>
     *            {@link EnumRenderType#BLOCK} for a block<br>
     *            {@link EnumRenderType#ENTITY} for an entity<br>
     *            <br>
     *
     * @param resourceNameIn
     *            The name of your resource in assets without extension
     */
    public void register(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn) {
        capitalCheck(resourceNameIn);
        CSRegistryHelper.register(resourceTypeIn,
                new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()),
                resourceNameIn);
    }

    /**
     * Pre-register your resource.
     *
     * @param resourceTypeIn
     *            Set your resource type, <br>
     *            {@link EnumResourceType#ANIM} for animation,<br>
     *            {@link EnumResourceType#MODELS} for models <br>
     *            <br>
     * @param resourceLocationIn
     *            Custom location of your resource
     *
     * @param resourceNameIn
     *            The name of your resource in assets without extension
     */
    public static void register(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        switch (resourceTypeIn) {
            case MODEL:
                if (CSRegistryHelper.loadModelList != null)
                    CSRegistryHelper.loadModelList.add(new LoadElement(resourceLocationIn, resourceNameIn));
                else
                    CraftStudioApi.getLogger()
                            .error("Unable to load model outside of the RegistryEvent.Register<CSReadedModel> event, use forceRegister instead");
                break;
            case ANIM:
                if (CSRegistryHelper.loadAnimList != null)
                    CSRegistryHelper.loadAnimList.add(new LoadElement(resourceLocationIn, resourceNameIn));
                else
                    CraftStudioApi.getLogger()
                            .error("Unable to load animations outside of the RegistryEvent.Register<CSReadedAnim> event, use forceRegister instead");
                break;
        }
    }

    /**
     * Load all the pre-registered models. Used internally.
     */
    public static void loadModels() {
        if (loadModelList == null)
            return;
        ProgressManager.ProgressBar progressBarModels;
        progressBarModels = ProgressManager.push("Registry Models", CSRegistryHelper.loadModelList.size());

        for (LoadElement el : CSRegistryHelper.loadModelList) {
            progressBarModels.step("[" + el.resourceLoc.getResourceDomain() + ":" + el.ressourceName + "]");
            registry(EnumResourceType.MODEL, el.resourceLoc, el.ressourceName);
        }
        ProgressManager.pop(progressBarModels);

        CraftStudioApi.getLogger().info(String.format("CraftStudioAPI loaded %s models", CSRegistryHelper.loadModelList.size()));
        CSRegistryHelper.loadModelList = null;
    }

    /**
     * Load all the pre-registered animations. Used internally.
     */
    public static void loadAnims() {
        if (loadAnimList == null)
            return;
        ProgressManager.ProgressBar progressBarAnim;
        progressBarAnim = ProgressManager.push("Registry Animations", CSRegistryHelper.loadAnimList.size());
        for (LoadElement el : CSRegistryHelper.loadAnimList) {
            progressBarAnim.step("[" + el.resourceLoc.getResourceDomain() + ":" + el.ressourceName + "]");
            registry(EnumResourceType.ANIM, el.resourceLoc, el.ressourceName);
        }
        ProgressManager.pop(progressBarAnim);

        CraftStudioApi.getLogger().info(String.format("CraftStudioAPI loaded %s animations", CSRegistryHelper.loadAnimList.size()));
        CSRegistryHelper.loadAnimList = null;
    }

    /**
     * Register an assets.
     * 
     * @param resourceTypeIn
     *            The resource type.
     * @param resourceLocationIn
     *            Location of the resource.
     * @param resourceNameIn
     *            The name of your resource.
     */
    private static void registry(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        CSJsonReader jsonReader;
        try {
            jsonReader = new CSJsonReader(resourceLocationIn);
            if (resourceLocationIn.getResourceDomain() != CraftStudioApi.API_ID)
                switch (resourceTypeIn) {
                    case MODEL:
                        RegistryHandler.register(new ResourceLocation(resourceLocationIn.getResourceDomain(), resourceNameIn),
                                jsonReader.readModel());
                        break;
                    case ANIM:
                        RegistryHandler.register(new ResourceLocation(resourceLocationIn.getResourceDomain(), resourceNameIn), jsonReader.readAnim());
                        break;
                }
            else
                CraftStudioApi.getLogger().fatal("You're not allowed to use the \"craftstudioapi\" to register CraftStudio resources.");
        } catch (CSResourceNotFoundException | CSMalformedJsonException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if there is capital letter in a String, and send a warning message.
     * 
     * @param str
     *            The String to test.
     */
    private static void capitalCheck(String str) {
        if (!str.toLowerCase().equals(str)) {
            CraftStudioApi.getLogger().warn("The resource name \"" + str + "\" contains capitals letters, which is not supported.");
            CraftStudioApi.getLogger().warn("A CSResourceNotFoundException could be raised !");
        }
    }

    /**
     * An object containing informations about a pre-registered object to load
     * later.
     * 
     * @author Timmypote
     */
    private static class LoadElement
    {
        ResourceLocation resourceLoc;
        String           ressourceName;

        LoadElement(ResourceLocation resourceLoc, String ressourceName) {
            this.resourceLoc = resourceLoc;
            this.ressourceName = ressourceName;
        }
    }
}