package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import net.minecraft.util.ResourceLocation;

public class CommonProxy
{
    public void preInit()
    {
    	CraftStudioApi.registerAnim(new ResourceLocation("testmod", "craftstudio/animations/position.csjsmodelanim"),
                "Position");
    	CraftStudioApi.registerAnim(new ResourceLocation("testmod", "craftstudio/animations/rotation.csjsmodelanim"),
                "Rotation");
    	CraftStudioApi.registerAnim(new ResourceLocation("testmod", "craftstudio/animations/fly.csjsmodelanim"),
                "Fly");
    	CraftStudioApi.registerAnim(new ResourceLocation("testmod", "craftstudio/animations/idle.csjsmodelanim"),
                "Idle");
    	CraftStudioApi.registerAnim(new ResourceLocation("testmod", "craftstudio/animations/closefan.csjsmodelanim"),
                "closefan");
    }

    public void init()
    {
    }
}