package com.leviathanstudio.craftstudio;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.EnumRenderType;
import com.leviathanstudio.craftstudio.client.json.EnumResourceType;
import com.leviathanstudio.craftstudio.common.exception.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.common.exception.CSResourceNotFoundException;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSRegistryHelper
{
    private String                   modid;

    /**
     * Constructor for the registry
     *
     * @param modid
     *            Define the ID of your mod
     */
    public CSRegistryHelper(String modid)
    {
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
    public void register(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn)
    {
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
    private static void register(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn,
            String modid)
    {
        capitalCheck(resourceNameIn);
        register(resourceTypeIn, new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName()
                + resourceNameIn + resourceTypeIn.getExtension()), resourceNameIn);
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
    public static void register(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn,
            String resourceNameIn)
    {
        switch (resourceTypeIn)
        {
            case MODEL:
            	registry(EnumResourceType.MODEL, resourceLocationIn, resourceNameIn);
                break;
            case ANIM:
            	registry(EnumResourceType.ANIM, resourceLocationIn, resourceNameIn);
                break;
        }
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
    private void registry(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn)
    {
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
    private static void registry(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn,
            String modid)
    {
        capitalCheck(resourceNameIn);
        registry(resourceTypeIn, new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName()
                + resourceNameIn + resourceTypeIn.getExtension()), resourceNameIn);
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
    private static void registry(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn,
            String resourceNameIn)
    {
        CSJsonReader jsonReader;
        CSReadedModel model;
        CSReadedAnim anim;
        try
        {
            jsonReader = new CSJsonReader(resourceLocationIn);
            if (resourceLocationIn.getResourceDomain() != CraftStudioApi.API_ID)
            {
                switch (resourceTypeIn)
                {
                    case MODEL:
                        GameRegistry.register(jsonReader.readModel().setRegistryName(resourceNameIn));
                        break;
                    case ANIM:
                        GameRegistry.register(jsonReader.readAnim().setRegistryName(resourceNameIn));
                        break;
                }
            }
            else
                CraftStudioApi.getLogger()
                        .fatal("You're not allowed to use the \"craftstudioapi\" to register CraftStudio resources.");
        } catch (CSResourceNotFoundException | CSMalformedJsonException e)
        {
            // CraftStudioApi.getLogger().error(e.getMessage());
            e.printStackTrace();

        }
    }

    private static void capitalCheck(String str)
    {
        if (!str.toLowerCase().equals(str))
        {
            CraftStudioApi.getLogger()
                    .warn("The resource name \"" + str + "\" contains capitals letters, which is not supported.");
            CraftStudioApi.getLogger().warn("A CSResourceNotFoundException could be raised !");
        }
    }
}