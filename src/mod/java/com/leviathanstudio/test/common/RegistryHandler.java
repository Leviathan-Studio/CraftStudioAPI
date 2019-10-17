package com.leviathanstudio.test.common;

import com.leviathanstudio.test.common.block.BlockTest;
import com.leviathanstudio.test.common.entity.EntityTest;
import com.leviathanstudio.test.common.entity.EntityTest2;
import com.leviathanstudio.test.common.entity.EntityTest3;
import com.leviathanstudio.test.common.entity.EntityTest4;
import com.leviathanstudio.test.common.item.ItemTest;
import com.leviathanstudio.test.common.tileEntity.TileEntityTest;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = ModTest.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegistryHandler
{
    public static final Block block_test = new BlockTest(Block.Properties.create(Material.ROCK));
    public static final TileEntityType<?> tile_test = register("tileTest", TileEntityType.Builder.create(TileEntityTest::new, block_test));
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(block_test.setRegistryName("block_test"));
    }
    
    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {
    	event.getRegistry().register(tile_test.setRegistryName("tile_test"));
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(block_test, (new Item.Properties())).setRegistryName("block_test"));
        event.getRegistry().register(new ItemTest(new Item.Properties()).setRegistryName("item_test"));
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
    	EntityType<EntityTest> entityTest = EntityType.Builder.<EntityTest>create(EntityTest::new, EntityClassification.MISC).build("entityTest");
    	EntityType<EntityTest2> entityTest2 = EntityType.Builder.<EntityTest2>create(EntityTest2::new, EntityClassification.MISC).build("entityTest2");
    	EntityType<EntityTest3> entityTest3 = EntityType.Builder.<EntityTest3>create(EntityTest3::new, EntityClassification.MISC).build("entityTest3");
    	EntityType<EntityTest4> entityTest4 = EntityType.Builder.<EntityTest4>create(EntityTest4::new, EntityClassification.MISC).build("entityTest4");

    	
    	event.getRegistry().register(entityTest.setRegistryName("test_1"));
    	event.getRegistry().register(entityTest2.setRegistryName("test_2"));
    	event.getRegistry().register(entityTest3.setRegistryName("test_3"));
    	event.getRegistry().register(entityTest4.setRegistryName("test_4"));

        ModTest.PROXY.registerEntityRender();
        
    }
    
    private static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder) {
        Type<?> type = null;

        try {
            type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(1631)).getChoiceType(TypeReferences.BLOCK_ENTITY, ModTest.MODID + ":" + id);
        }
        catch(IllegalArgumentException e) {
            if(SharedConstants.developmentMode) {
                throw e;
            }
        }

        TileEntityType<T> tileEntityType = builder.build(type);

        return tileEntityType;
    }

}
