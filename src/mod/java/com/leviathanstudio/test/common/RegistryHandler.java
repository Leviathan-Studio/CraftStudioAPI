package com.leviathanstudio.test.common;

import java.awt.Color;

import com.leviathanstudio.test.common.block.BlockTest;
import com.leviathanstudio.test.common.entity.EntityTest;
import com.leviathanstudio.test.common.entity.EntityTest2;
import com.leviathanstudio.test.common.entity.EntityTest3;
import com.leviathanstudio.test.common.entity.EntityTest4;
import com.leviathanstudio.test.common.item.ItemTest;
import com.leviathanstudio.test.common.tileEntity.TileEntityTest;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@EventBusSubscriber(modid = Mod_Test.MODID)
public class RegistryHandler
{
    @ObjectHolder(Mod_Test.MODID + ":block_test")
    private static final Block BLOCK_TEST = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockTest());
        GameRegistry.registerTileEntity(TileEntityTest.class, "tile_entity_test");
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        System.out.println(BLOCK_TEST);
        event.getRegistry().register(new ItemBlock(BLOCK_TEST).setRegistryName("item_block_test"));
        event.getRegistry().register(new ItemTest().setRegistryName("item_test"));
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest"), EntityTest.class, "entityTest", 420, Mod_Test.getInstance(),
                40, 1, true, new Color(0, 255, 0).getRGB(), new Color(255, 0, 0).getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest2"), EntityTest2.class, "entityTest2", 421,
                Mod_Test.getInstance(), 40, 1, true, new Color(255, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest3"), EntityTest3.class, "entityTest3", 422,
                Mod_Test.getInstance(), 40, 1, true, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0).getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest4"), EntityTest4.class, "entityTest4", 423,
                Mod_Test.getInstance(), 40, 1, true, new Color(255, 255, 0).getRGB(), new Color(0, 0, 0).getRGB());
        Mod_Test.getProxy().registerEntityRender();
        Mod_Test.getProxy().bindTESR();
    }
}
