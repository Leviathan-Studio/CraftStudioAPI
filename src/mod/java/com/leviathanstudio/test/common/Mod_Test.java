package com.leviathanstudio.test.common;

import java.awt.Color;

import com.leviathanstudio.test.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(name = "TestMod", modid = Mod_Test.MODID, dependencies = "required-after:craftstudioapi")
public class Mod_Test {
	public static final String MODID = "testmod";

	@SidedProxy(clientSide = "com.leviathanstudio.test.proxy.ClientProxy", serverSide = "com.leviathanstudio.test.proxy.CommonProxy")
	private static CommonProxy proxy;

	@Instance(Mod_Test.MODID)
	private static Mod_Test instance;

    public static Mod_Test getInstance() {
        return Mod_Test.instance;
    }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Mod_Test.proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		EntityRegistry.registerModEntity(EntityTest.class, "entityTest", 420, Mod_Test.instance, 40, 1, true,
				new Color(0, 255, 0).getRGB(), new Color(255, 0, 0).getRGB());
		EntityRegistry.registerModEntity(EntityTest2.class, "entityTest2", 421, Mod_Test.instance, 40, 1, true,
				new Color(255, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
		EntityRegistry.registerModEntity(EntityTest3.class, "entityTest3", 422, Mod_Test.instance, 40, 1, true,
				new Color(0, 0, 255).getRGB(), new Color(0, 0, 0).getRGB());
		EntityRegistry.registerModEntity(EntityTest4.class, "entityTest4", 423, Mod_Test.instance, 40, 1, true,
				new Color(255, 255, 0).getRGB(), new Color(0, 0, 0).getRGB());

		MinecraftForge.EVENT_BUS.register(this);
	}

}