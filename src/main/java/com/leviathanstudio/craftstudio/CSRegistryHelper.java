package com.leviathanstudio.craftstudio;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.client.exception.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.client.exception.CSResourceNotFoundException;
import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.client.json.EnumRenderType;
import com.leviathanstudio.craftstudio.client.json.EnumResourceType;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
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
     * Register your resources with the {@link IForgeRegistry}, the right way
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
        CSRegistryHelper.register(resourceTypeIn, renderTypeIn, resourceNameIn, this.modid);
    }

    /**
     * Register your resources with the {@link IForgeRegistry}, the right way
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
     *
     * @param modid
     *            The ID of your mod
     */
    private static void register(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn, String modid) {
        capitalCheck(resourceNameIn);
        register(resourceTypeIn,
                new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()),
                resourceNameIn);
    }

    /**
     * Register your resources with the {@link IForgeRegistry}, the right way
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

    static void loadModels() {
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

    static void loadAnims() {
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
    private void registry(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn) {
        CSRegistryHelper.registry(resourceTypeIn, renderTypeIn, resourceNameIn, this.modid);
    }

    /**
     * @param resourceTypeIn
     *            Set your resource type, <br>
     *            {@link EnumResourceType#ANIM} for animation,<br>
     *            {@link EnumResourceType#MODELS} for models <br>
     *            <br>
     *
     * @param renderTypeIn
     *            Set your render type, <br>
     *            {@link EnumRenderType#BLOCK} for a block<br>
     *            {@link EnumRenderType#ENTITY} for an entity<br>
     *            <br>
     *
     * @param resourceNameIn
     *            The name of your resource in assets without extension
     *
     * @param modid
     *            The ID of your mod
     */
    private static void registry(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn, String modid) {
        capitalCheck(resourceNameIn);
        registry(resourceTypeIn,
                new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()),
                resourceNameIn);
    }

    /**
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
    private static void registry(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        CSJsonReader jsonReader;
        try {
            jsonReader = new CSJsonReader(resourceLocationIn);
            if (resourceLocationIn.getResourceDomain() != CraftStudioApi.API_ID) {
                ModContainer activeMod = Loader.instance().activeModContainer();
                ModContainer mod = FMLCommonHandler.instance().findContainerFor(resourceLocationIn.getResourceDomain());
                if (activeMod != mod)
                    Loader.instance().setActiveModContainer(mod);
                switch (resourceTypeIn) {
                    case MODEL:
                        GameRegistry.register(jsonReader.readModel().setRegistryName(resourceNameIn));
                        break;
                    case ANIM:
                        GameRegistry.register(jsonReader.readAnim().setRegistryName(resourceNameIn));
                        break;
                }
                Loader.instance().setActiveModContainer(activeMod);
            }
            else
                CraftStudioApi.getLogger().fatal("You're not allowed to use the \"craftstudioapi\" to register CraftStudio resources.");
        } catch (CSResourceNotFoundException | CSMalformedJsonException e) {
            // CraftStudioApi.getLogger().error(e.getMessage());
            e.printStackTrace();

        }
    }

    private static void capitalCheck(String str) {
        if (!str.toLowerCase().equals(str)) {
            CraftStudioApi.getLogger().warn("The resource name \"" + str + "\" contains capitals letters, which is not supported.");
            CraftStudioApi.getLogger().warn("A CSResourceNotFoundException could be raised !");
        }
    }

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