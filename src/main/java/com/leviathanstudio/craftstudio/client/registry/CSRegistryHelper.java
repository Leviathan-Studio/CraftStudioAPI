package com.leviathanstudio.craftstudio.client.registry;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.exception.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.client.exception.CSResourceNotFoundException;
import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.client.util.EnumRenderType;
import com.leviathanstudio.craftstudio.client.util.EnumResourceType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing useful methods to register models and animations.
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSRegistryHelper {
    private String modid;


    public CSRegistryHelper(String modid) {
    	this.modid = modid;
    }

    /**
     * Pre-register your resource.
     *
     * @param resourceTypeIn     Set your resource type, <br>
     *                           {@link EnumResourceType#ANIM} for animation,<br>
     *                           {@link EnumResourceType#MODEL} for models <br>
     *                           <br>
     * @param resourceLocationIn Custom location of your resource
     * @param resourceNameIn     The name of your resource in assets without extension
     */
    public static void register(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        switch (resourceTypeIn) {
            case MODEL:
            	registry(EnumResourceType.MODEL, resourceLocationIn, resourceNameIn);
                break;
            case ANIM:
            	registry(EnumResourceType.ANIM, resourceLocationIn, resourceNameIn);
                break;
        }
    }



    /**
     * Register an assets.
     *
     * @param resourceTypeIn     The resource type.
     * @param resourceLocationIn Location of the resource.
     * @param resourceNameIn     The name of your resource.
     */
    private static void registry(EnumResourceType resourceTypeIn, ResourceLocation resourceLocationIn, String resourceNameIn) {
        CSJsonReader jsonReader;
        try {
            jsonReader = new CSJsonReader(resourceLocationIn);
            if (resourceLocationIn.getNamespace() != CraftStudioApi.API_ID)
                switch (resourceTypeIn) {
                    case MODEL:
                        RegistryHandler.register(new ResourceLocation(resourceLocationIn.getNamespace(), resourceNameIn),
                                jsonReader.readModel());
                        break;
                    case ANIM:
                        RegistryHandler.register(new ResourceLocation(resourceLocationIn.getNamespace(), resourceNameIn), jsonReader.readAnim());
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
     * @param str The String to test.
     */
    private static void capitalCheck(String str) {
        if (!str.toLowerCase().equals(str)) {
            CraftStudioApi.getLogger().warn("The resource name \"" + str + "\" contains capitals letters, which is not supported.");
            CraftStudioApi.getLogger().warn("A CSResourceNotFoundException could be raised !");
        }
    }


    /**
     * Pre-register your resource.
     *
     * @param resourceTypeIn Set your resource type, <br>
     *                       {@link EnumResourceType#ANIM} for animation,<br>
     *                       {@link EnumResourceType#MODEL} for models <br>
     *                       <br>
     * @param renderTypeIn   Set your render type, <br>
     *                       {@link EnumRenderType#BLOCK} for a block<br>
     *                       {@link EnumRenderType#ENTITY} for an entity<br>
     *                       <br>
     * @param resourceNameIn The name of your resource in assets without extension
     */
    public void register(EnumResourceType resourceTypeIn, EnumRenderType renderTypeIn, String resourceNameIn) {
        capitalCheck(resourceNameIn);
        CSRegistryHelper.register(resourceTypeIn,
                new ResourceLocation(modid, resourceTypeIn.getPath() + renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()),
                resourceNameIn);
    }

}