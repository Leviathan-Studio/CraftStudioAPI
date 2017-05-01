package fr.zeamateis.test.anim.common;

import java.awt.Color;

import fr.zeamateis.test.proxy.CommonProxy;
import lib.craftstudio.CraftStudioApi;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(name = "TestMod", modid = "testmod")
public class Mod_Test
{
    @SidedProxy(clientSide = "fr.zeamateis.test.proxy.ClientProxy", serverSide = "fr.zeamateis.test.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Instance("testmod")
    private static Mod_Test    instance;

    public static Mod_Test getInstance() {
        return Mod_Test.instance;
    }

    public static Item itemTest = new ItemTest();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception {
        CraftStudioApi.preInit();

        CraftStudioApi.registerModel(new ResourceLocation("testmod", "model/craftstudio/ModelDeadCorpse.csjsmodel"), "ModelDeadCorpse");

        GameRegistry.registerTileEntity(TileEntityBlockTest.class, "TileEntityBlockTest");

        Block blockTest = new BlockTest();
        GameRegistry.registerBlock(blockTest, "blockTest");

        GameRegistry.registerItem(Mod_Test.itemTest, "itemTest");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Mod_Test.proxy.init();

        EntityRegistry.registerModEntity(EntityTest.class, "entityTest", 420, Mod_Test.instance, 40, 1, true, new Color(0, 255, 0).getRGB(),
                new Color(255, 0, 0).getRGB());

        MinecraftForge.EVENT_BUS.register(this);
    }

}