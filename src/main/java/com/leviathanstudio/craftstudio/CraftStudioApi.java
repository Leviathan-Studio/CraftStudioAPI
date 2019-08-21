package com.leviathanstudio.craftstudio;

import com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.proxy.CSClientProxy;
import com.leviathanstudio.craftstudio.proxy.CSCommonProxy;
import com.leviathanstudio.craftstudio.proxy.CSServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Main class of the CraftStudioApi
 *
 * @author ZeAmateis
 * @author Timmypote
 * @since 0.3.0
 */
@Mod(CraftStudioApi.API_ID)
public class CraftStudioApi {
    public static final String API_ID = "craftstudioapi";

    private static final Logger LOGGER = LogManager.getLogger("CraftStudio");
    private static CSCommonProxy proxy = DistExecutor.runForDist(() -> CSClientProxy::new, () -> CSServerProxy::new);

    public CraftStudioApi() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }

    /**
     * Helper to create an AnimationHandler to registry animation to your
     * entity/block
     *
     * @param <T>
     * @param animatedClass which implements IAnimated (Entity or TileEntity)
     */
    public static <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return CraftStudioApi.proxy.getNewAnimationHandler(animatedClass);

    }

    public void clientSetup(FMLClientSetupEvent event) {
        CraftStudioApi.proxy.clientSetup(event);
        loadCraftStudioLoaders(event);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        CraftStudioApi.proxy.commonSetup(event);
    }

    public void loadCraftStudioLoaders(FMLClientSetupEvent event) {
        String methodName, className;
        Method method;

        RegistryHandler.init();


        //TODO Work on annotations or other way to register models
        /*ASMDataTable dataTable = event
        Set<ASMData> datas = dataTable.getAll("com.leviathanstudio.craftstudio.client.registry.CraftStudioLoader");
        for (ASMData data : datas) {
            className = data.getClassName();
            methodName = data.getObjectName().substring(0, data.getObjectName().indexOf("("));
            try {
                method = Class.forName(className).getMethod(methodName);
                method.invoke(null);
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
                e1.printStackTrace();
                CraftStudioApi.getLogger().error("Error loading @CraftStudioLoader in class " + className + " for method " + methodName + "().");
                CraftStudioApi.getLogger().error("Does that method has arguments ? Because it should have none.");
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e1) {
                e1.printStackTrace();
                CraftStudioApi.getLogger().error("Error loading craftstudio assets in class " + className + " for method " + methodName + "().");
                CraftStudioApi.getLogger().error("Is that method 'static' ? Because it should.");
            }
        }*/

        CSRegistryHelper.loadModels();
        CSRegistryHelper.loadAnims();
    }

}