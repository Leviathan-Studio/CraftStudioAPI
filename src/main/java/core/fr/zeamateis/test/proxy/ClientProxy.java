package fr.zeamateis.test.proxy;

import fr.zeamateis.test.anim.client.RenderTest;
import fr.zeamateis.test.anim.common.EntityTest;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void init() {
        // Registry Entity
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest());
        // Registry TESR
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTest.class,
        // new TileEntityBlockTestRenderer());
    }
}