package com.leviathanstudio.craftstudio;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.client.CSAnimMesher;
import com.leviathanstudio.craftstudio.client.CSModelMesher;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.common.RegistryCraftStudio;
import com.leviathanstudio.craftstudio.util.VersionChecker;

import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Main class of the CraftStudioApi
 *
 * @author ZeAmateis
 * @author Timmypote
 */
public class CraftStudioApi {
	private static final Logger LOGGER = LogManager.getLogger("CraftStudio");

	public static final String API_ID = "craftstudioapi";
	public static final String NAME = "CraftStudio API";
	public static final String ACTUAL_VERSION = "1.1";

	private static VersionChecker versionChecker = new VersionChecker();

	public static void preInit(FMLPreInitializationEvent event) {
		CraftStudioApi.versionChecker.preInit();
	}

	public static void init(FMLInitializationEvent event) {
		ProgressManager.ProgressBar progressBarModels;
		progressBarModels = ProgressManager.push("Registry Models", CSModelMesher.models.size());
		for (Map.Entry<String, CSReadedModel> models : CSModelMesher.models.entrySet())
			progressBarModels.step("[" + models.getValue().modid + ":" + models.getValue().name + "]");
		ProgressManager.pop(progressBarModels);

		ProgressManager.ProgressBar progressBarAnim;
		progressBarAnim = ProgressManager.push("Registry Animations", CSAnimMesher.animations.size());
		for (Map.Entry<String, CSReadedAnim> anims : CSAnimMesher.animations.entrySet())
			progressBarAnim.step("[" + anims.getValue().modid + ":" + anims.getValue().name + "]");
		ProgressManager.pop(progressBarAnim);

		CraftStudioApi.LOGGER.info(String.format("Loaded %s resources (%s models / %s animations) from '%s'",
				CSModelMesher.models.size() + CSAnimMesher.animations.size(), CSModelMesher.models.size(),
				CSAnimMesher.animations.size(), RegistryCraftStudio.getModid()));
	}

	public static Logger getLogger() {
		return CraftStudioApi.LOGGER;
	}
}