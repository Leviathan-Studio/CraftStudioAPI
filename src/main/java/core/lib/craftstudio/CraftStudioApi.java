package lib.craftstudio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lib.craftstudio.utils.Version;

public class CraftStudioApi
{
    private static final Logger LOGGER         = LogManager.getLogger("CraftStudio");

    private static Version      versionChecker = new Version();

    public static void preInit() throws Exception {
        CraftStudioApi.getVersion().preInit();
    }

    public static Version getVersion() {
        return CraftStudioApi.versionChecker;
    }

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }
}