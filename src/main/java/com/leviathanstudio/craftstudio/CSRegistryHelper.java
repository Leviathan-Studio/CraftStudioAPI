package com.leviathanstudio.craftstudio;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;
import com.leviathanstudio.craftstudio.common.exceptions.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.common.exceptions.CSResourceNotFoundException;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSRegistryHelper
{
    private static String            modid;

    private static List<LoadElement> loadModelList = new ArrayList();
    private static List<LoadElement> loadAnimList  = new ArrayList();

    public CSRegistryHelper(String modid) {
        this.modid = modid;
    }

    public void register(ResourceType resourceTypeIn, RenderType renderTypeIn, String resourceNameIn) {
        this.register(resourceTypeIn, renderTypeIn, resourceNameIn, this.modid);
    }

    private static void register(ResourceType resourceTypeIn, RenderType renderTypeIn, String resourceNameIn, String modid) {
        capitalCheck(resourceNameIn);
        register(resourceTypeIn,
                new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()),
                resourceNameIn);
    }

    public static void register(ResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        switch (resourceTypeIn) {
            case MODEL:
                if (loadModelList != null)
                    loadModelList.add(new LoadElement(resourceLocationIn, resourceNameIn));
                else
                    CraftStudioApi.getLogger()
                            .error("Unable to load model outside of the RegistryEvent.Register<CSReadedModel> event, use forceRegister instead");
                break;
            case ANIM:
                if (loadAnimList != null)
                    loadAnimList.add(new LoadElement(resourceLocationIn, resourceNameIn));
                else
                    CraftStudioApi.getLogger()
                            .error("Unable to load animations outside of the RegistryEvent.Register<CSReadedAnim> event, use forceRegister instead");
                break;
        }
    }

    static void loadModels() {
        ProgressManager.ProgressBar progressBarModels;
        progressBarModels = ProgressManager.push("Registry Models", loadModelList.size());

        for (LoadElement el : loadModelList) {
            progressBarModels.step("[" + el.resourceLoc.getResourceDomain() + ":" + el.ressourceName + "]");
            forceRegister(ResourceType.MODEL, el.resourceLoc, el.ressourceName);
        }
        ProgressManager.pop(progressBarModels);

        CraftStudioApi.getLogger().info(String.format("CraftStudioAPI loaded %s models", loadModelList.size()));
        loadModelList = null;
    }

    static void loadAnims() {
        ProgressManager.ProgressBar progressBarAnim;
        progressBarAnim = ProgressManager.push("Registry Animations", loadAnimList.size());
        for (LoadElement el : loadAnimList) {
            progressBarAnim.step("[" + el.resourceLoc.getResourceDomain() + ":" + el.ressourceName + "]");
            forceRegister(ResourceType.ANIM, el.resourceLoc, el.ressourceName);
        }
        ProgressManager.pop(progressBarAnim);

        CraftStudioApi.getLogger().info(String.format("CraftStudioAPI loaded %s animations", loadAnimList.size()));
        loadAnimList = null;
    }

    public void forceRegister(ResourceType resourceTypeIn, RenderType renderTypeIn, String resourceNameIn) {
        this.forceRegister(resourceTypeIn, renderTypeIn, resourceNameIn, this.modid);
    }

    private static void forceRegister(ResourceType resourceTypeIn, RenderType renderTypeIn, String resourceNameIn, String modid) {
        capitalCheck(resourceNameIn);
        forceRegister(resourceTypeIn,
                new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()),
                resourceNameIn);
    }

    /**
     * Register a new resource with the given name.
     *
     * @param resourceIn
     *            The location of the .csjsmodel or .csjsmodelanim.
     * @param modelNameIn
     *            The name given to the model.
     */
    public static void forceRegister(ResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        CSJsonReader jsonReader;
        CSReadedModel model;
        CSReadedAnim anim;
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