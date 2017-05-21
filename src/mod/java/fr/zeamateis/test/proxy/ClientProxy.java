package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.CraftStudioApi;

import fr.zeamateis.test.anim.client.RenderTest;
import fr.zeamateis.test.anim.client.RenderTest2;
import fr.zeamateis.test.anim.client.RenderTest3;
import fr.zeamateis.test.anim.client.RenderTest4;
import fr.zeamateis.test.anim.common.EntityTest;
import fr.zeamateis.test.anim.common.EntityTest2;
import fr.zeamateis.test.anim.common.EntityTest3;
import fr.zeamateis.test.anim.common.EntityTest4;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void preInit()
    {
    	super.preInit();
    	//Registry Model
        CraftStudioApi.registerModel(new ResourceLocation("testmod", "craftstudio/models/model_dead_corpse.csjsmodel"),
                "ModelDeadCorpse");
        CraftStudioApi.registerModel(
                new ResourceLocation("testmod", "craftstudio/models/craftstudio_api_test2.csjsmodel"),
                "CraftStudioAPITest2");
        CraftStudioApi.registerModel(
                new ResourceLocation("testmod", "craftstudio/models/craftstudio_api_test.csjsmodel"),
                "CraftStudioAPITest");
        CraftStudioApi.registerModel(new ResourceLocation("testmod", "craftstudio/models/dragon_brun.csjsmodel"),
                "Dragon_Brun");
        CraftStudioApi.registerModel(new ResourceLocation("testmod", "craftstudio/models/peacock.csjsmodel"),
                "peacock");
        
        //Registry Entity
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory(){
			@Override
			public Render createRenderFor(RenderManager manager){
				return new RenderTest(manager);
			}
    	});
        RenderingRegistry.registerEntityRenderingHandler(EntityTest2.class, new IRenderFactory(){
			@Override
			public Render createRenderFor(RenderManager manager){
				return new RenderTest2(manager);
			}
    	});
        RenderingRegistry.registerEntityRenderingHandler(EntityTest3.class, new IRenderFactory(){
			@Override
			public Render createRenderFor(RenderManager manager){
				return new RenderTest3(manager);
			}
    	});
        RenderingRegistry.registerEntityRenderingHandler(EntityTest4.class, new IRenderFactory(){
			@Override
			public Render createRenderFor(RenderManager manager){
				return new RenderTest4(manager);
			}
    	});
    }

    @Override
    public void init()
    {
    	super.init();
        // Registry Entity
        // RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest());
        // Registry TESR
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTest.class,
        // new TileEntityBlockTestRenderer());
    }
}