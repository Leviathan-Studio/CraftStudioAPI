package com.leviathanstudio.craftstudio.dev;

import com.leviathanstudio.craftstudio.dev.command.CommandCSList;
import com.leviathanstudio.craftstudio.dev.command.CommandCSUVMap;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class of the dev mod of the CraftStudioAPI
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
@Mod(CraftStudioApiDev.API_ID)
public class CraftStudioApiDev
{

    private static final Logger LOGGER = LogManager.getLogger("CraftStudioAPIDev");
    public static final String  API_ID = "craftstudioapidev";
    public static final String  NAME   = "CraftStudio API Dev";

    public CraftStudioApiDev() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStartingEvent);
    }
    
 	private void serverStartingEvent(FMLServerStartingEvent event) {
 		CommandCSUVMap.register(event.getCommandDispatcher());
 		CommandCSList.register(event.getCommandDispatcher());
    }

    public static Logger getLogger() {
        return CraftStudioApiDev.LOGGER;
    }
}
