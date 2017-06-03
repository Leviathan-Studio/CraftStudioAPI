package com.leviathanstudio.test.proxy;

import com.leviathanstudio.test.client.RenderBlockTest;
import com.leviathanstudio.test.common.TileEntityTest;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CommonProxy
{
    public void registerModels() {}

    public void registerAnims() {}

    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTest.class, new RenderBlockTest());
    }
}