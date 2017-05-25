package com.leviathanstudio.craftstudio.common;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.CSAnimMesher;
import com.leviathanstudio.craftstudio.client.CSModelMesher;
import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.common.exceptions.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.common.exceptions.CSResourceNotFoundException;

import net.minecraft.util.ResourceLocation;

public class RegistryCraftStudio
{

    private static String modid;

	public static void register(ResourceType resourceTypeIn, RenderType renderTypeIn, String resourceNameIn) {
		if (!resourceNameIn.toLowerCase().equals(resourceNameIn)){
			CraftStudioApi.getLogger().warn("The resource name \"" + resourceNameIn + "\" contains capitals letters, which is not supported.");
			CraftStudioApi.getLogger().warn("A CSResourceNotFoundException could be raised !");
			}
		register(resourceTypeIn, new ResourceLocation(getModid(), resourceTypeIn.getPath()
				+ renderTypeIn.getFolderName() + resourceNameIn + resourceTypeIn.getExtension()), resourceNameIn);
	}

    public static String getModid() {
        return RegistryCraftStudio.modid;
    }

    public static void setModid(String modidIn) {
        RegistryCraftStudio.modid = modidIn;
    }

	/**
	 * Register a new resource with the given name.
	 *
	 * @param resourceIn
	 *            The location of the .csjsmodel or .csjsmodelanim.
	 * @param modelNameIn
	 *            The name given to the model.
	 */
	public static void register(ResourceType resourceTypeIn, ResourceLocation resourceLocationIn,
			String resourceNameIn) {
		CSJsonReader jsonReader;
		try {
			jsonReader = new CSJsonReader(resourceLocationIn);

			if (resourceLocationIn.getResourceDomain() != CraftStudioApi.API_ID)
				switch (resourceTypeIn) {
				case MODEL:
					CSModelMesher.models.put(resourceNameIn, jsonReader.readModel());
					break;
				case ANIM:
					CSAnimMesher.animations.put(resourceNameIn, jsonReader.readAnim());
					break;
				}
			else
				CraftStudioApi.getLogger()
						.fatal("You're not allowed to use the \"craftstudioapi\" to register CraftStudio resources.");
		} catch (CSResourceNotFoundException | CSMalformedJsonException e) {
			//CraftStudioApi.getLogger().error(e.getMessage());
			e.printStackTrace();
			
		}
	}
}