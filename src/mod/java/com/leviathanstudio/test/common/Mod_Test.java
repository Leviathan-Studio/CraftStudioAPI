package com.leviathanstudio.test.common;

import com.leviathanstudio.test.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(Mod_Test.MODID)
public class Mod_Test
{
    public static final String MODID = "testmod";

    @SidedProxy(clientSide = "com.leviathanstudio.test.proxy.ClientProxy", serverSide = "com.leviathanstudio.test.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Instance(Mod_Test.MODID)
    private static Mod_Test    instance;

    public static Mod_Test getInstance() {
        return instance;
    }

    public static CommonProxy getProxy() {
        return proxy;
    }
}