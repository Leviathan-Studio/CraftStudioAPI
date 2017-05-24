package com.leviathanstudio.craftstudio.common;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.CSAnimMesher;
import com.leviathanstudio.craftstudio.client.CSModelMesher;
import com.leviathanstudio.craftstudio.client.json.CSJsonReader;
import com.leviathanstudio.craftstudio.common.animation.CraftStudioResourceNotFound;

import net.minecraft.util.ResourceLocation;

public class RegistryCraftStudio {

	private static String modid;

	public static void register(ResourceType resourceTypeIn, RenderType renderTypeIn, String resourceNameIn) {
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
	 * Register a new resource with the name given.
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
		} catch (CraftStudioResourceNotFound e) {
			CraftStudioApi.getLogger().error(e.getMessage());
		}
	}
}