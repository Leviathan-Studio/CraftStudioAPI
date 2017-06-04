package com.leviathanstudio.craftstudio.proxy;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

public class CSClientProxy extends CSCommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
    	PersistentRegistryManager.createRegistry(new ResourceLocation(CraftStudioApi.API_ID, "cs_models"),
    			CSReadedModel.class, null, 0, 4096, false, null, null, null);
    	PersistentRegistryManager.createRegistry(new ResourceLocation(CraftStudioApi.API_ID, "model_animations"),
    			CSReadedAnim.class, null, 0, 4096, false, null, null, null);
        super.preInit(e);
        
    }

    @Override
    public AnimationHandler getNewAnimationHandler(IAnimated animated)
    {
        return new ClientAnimationHandler(animated);
    }
}
