package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import fr.zeamateis.test.anim.client.RenderTest;
import fr.zeamateis.test.anim.common.EntityTest;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void preInit()
    {
    	super.preInit();
        CraftStudioApi.registerModel(new ResourceLocation("testmod", "craftstudio/models/model_dead_corpse.csjsmodel"),
                "ModelDeadCorpse");
        CraftStudioApi.registerModel(
                new ResourceLocation("testmod", "craftstudio/models/craftstudio_api_test.csjsmodel"),
                "CraftStudioAPITest");
        CraftStudioApi.registerModel(new ResourceLocation("testmod", "craftstudio/models/dragon_brun.csjsmodel"),
                "Dragon_Brun");
    }

    @Override
    public void init()
    {
    	super.init();
        // Registry Entity
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest());
        // Registry TESR
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTest.class,
        // new TileEntityBlockTestRenderer());
    }
}