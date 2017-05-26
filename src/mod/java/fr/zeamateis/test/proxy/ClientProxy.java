package fr.zeamateis.test.proxy;

import com.leviathanstudio.craftstudio.CSRegistryHelper;
import com.leviathanstudio.craftstudio.common.RenderType;
import com.leviathanstudio.craftstudio.common.ResourceType;

import fr.zeamateis.test.anim.client.RenderTest;
import fr.zeamateis.test.anim.client.RenderTest2;
import fr.zeamateis.test.anim.client.RenderTest3;
import fr.zeamateis.test.anim.client.RenderTest4;
import fr.zeamateis.test.anim.common.EntityTest;
import fr.zeamateis.test.anim.common.EntityTest2;
import fr.zeamateis.test.anim.common.EntityTest3;
import fr.zeamateis.test.anim.common.EntityTest4;
import fr.zeamateis.test.anim.common.Mod_Test;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerModels() {
        super.registerModels();
        CSRegistryHelper registry = new CSRegistryHelper(Mod_Test.MODID);
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "model_dead_corpse");
        registry.register(ResourceType.MODEL, RenderType.BLOCK, "craftstudio_api_test2");
        registry.register(ResourceType.MODEL, RenderType.BLOCK, "craftstudio_api_test");
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "dragon_brun");
        registry.register(ResourceType.MODEL, RenderType.ENTITY, "peacock");
    }

    @Override
    public void registerAnims() {
        super.registerAnims();
    }

    @Override
    public void preInit() {
        super.preInit();
        // Registry Entity
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityTest2.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest2(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityTest3.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest3(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityTest4.class, new IRenderFactory() {
            @Override
            public Render createRenderFor(RenderManager manager) {
                return new RenderTest4(manager);
            }
        });
    }

    @Override
    public void init() {
        super.init();
        // Registry Entity
        // RenderingRegistry.registerEntityRenderingHandler(EntityTest.class,
        // new RenderTest());
        // Registry TESR
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTest.class,
        // new TileEntityBlockTestRenderer());
    }
}