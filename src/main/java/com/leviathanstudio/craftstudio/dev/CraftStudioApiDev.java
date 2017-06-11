package com.leviathanstudio.craftstudio.dev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.dev.command.CommandCSList;
import com.leviathanstudio.craftstudio.dev.command.CommandCSUVMap;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = CraftStudioApiDev.API_ID, name = CraftStudioApiDev.NAME, version = "0.2-beta", acceptedMinecraftVersions = "1.11.2", clientSideOnly = true)
public class CraftStudioApiDev
{

    private static final Logger LOGGER = LogManager.getLogger("CraftStudioAPIDev");
    public static final String  API_ID = "craftstudioapidev";
    public static final String  NAME   = "CraftStudio API Dev";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandCSUVMap());
        ClientCommandHandler.instance.registerCommand(new CommandCSList());
    }

    public static Logger getLogger() {
        return CraftStudioApiDev.LOGGER;
    }
}