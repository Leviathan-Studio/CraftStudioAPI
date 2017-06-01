package com.leviathanstudio.craftstudio.dev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.dev.util.UVMapCreator;
import com.leviathanstudio.test.common.Mod_Test;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CraftStudioApiDev.API_ID, name = CraftStudioApiDev.NAME, version = "0.1-beta", acceptedMinecraftVersions = "1.11.2", clientSideOnly = true)
public class CraftStudioApiDev {
	
	private static final Logger              LOGGER  = LogManager.getLogger("CraftStudioAPIDev");
    public static final String               API_ID  = "craftstudioapidev";
    public static final String               NAME    = "CraftStudio API Dev";

    @EventHandler
    void preInit(FMLPreInitializationEvent event)
    {
    	UVMapCreator c = new UVMapCreator(new ResourceLocation(Mod_Test.MODID, "craftstudio_api_test"));
    	c.createUVMap();
    	c = new UVMapCreator(new ResourceLocation(Mod_Test.MODID, "peacock"));
    	c.createUVMap();
    	c = new UVMapCreator(new ResourceLocation(Mod_Test.MODID, "dragon_brun"));
    	c.createUVMap();
    }

    public static Logger getLogger()
    {
        return CraftStudioApiDev.LOGGER;
    }
}
