package com.leviathanstudio.test.common;

import com.leviathanstudio.test.proxy.*;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(ModTest.MODID)
public class ModTest
{
    public static final String MODID = "testmod";

    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public ModTest( ) {
    	
    }
}