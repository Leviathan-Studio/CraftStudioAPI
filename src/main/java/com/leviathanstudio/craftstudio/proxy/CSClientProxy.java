package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.lang.reflect.Method;

/**
 * Client proxy of the CraftStudioApi
 *
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
public class CSClientProxy extends CSCommonProxy {


    @Override
    public void clientSetup(FMLClientSetupEvent e) {
        super.clientSetup(e);
        this.loadCraftStudioLoaders(e);
    }

    @Override
    public <T extends IAnimated> AnimationHandler<T> getNewAnimationHandler(Class<T> animatedClass) {
        return new ClientAnimationHandler<>();
    }

    private void loadCraftStudioLoaders(FMLClientSetupEvent e) {
        String methodName, className;
        Method method;

        RegistryHandler.init();

        //TODO Work on annotations or other way to register models
        /*ASMDataTable dataTable = e.getAsmData();
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
