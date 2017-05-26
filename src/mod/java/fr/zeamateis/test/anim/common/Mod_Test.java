package fr.zeamateis.test.anim.common;

import java.awt.Color;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import fr.zeamateis.test.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
@Mod(name = "TestMod", modid = Mod_Test.MODID)
public class Mod_Test
{

    public static final String MODID = "testmod";

    @SidedProxy(clientSide = "fr.zeamateis.test.proxy.ClientProxy", serverSide = "fr.zeamateis.test.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Instance("testmod")
    private static Mod_Test    instance;

    public static Mod_Test getInstance() {
        return Mod_Test.instance;
    }

    public static Item itemTest = new ItemTest();

    @SubscribeEvent()
    public static void registerModels(RegistryEvent.Register<CSReadedModel> e) {
        proxy.registerModels();
    }

    @SubscribeEvent()
    public static void registerAnims(RegistryEvent.Register<CSReadedAnim> e) {
        proxy.registerAnims();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception {
        Mod_Test.proxy.preInit();
        GameRegistry.registerTileEntity(TileEntityBlockTest.class, "TileEntityBlockTest");

        Block blockTest = new BlockTest();
        // GameRegistry.registerBlock(blockTest, "blockTest");
        // GameRegistry.registerItem(Mod_Test.itemTest, "itemTest");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Mod_Test.proxy.init();

        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest"), EntityTest.class, "entityTest", 420, Mod_Test.instance, 40, 1,
                true, new Color(0, 255, 0).getRGB(), new Color(255, 0, 0).getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest2"), EntityTest2.class, "entityTest2", 421, Mod_Test.instance, 40,
                1, true, new Color(255, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest3"), EntityTest3.class, "entityTest3", 422, Mod_Test.instance, 40,
                1, true, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0).getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation("testmod", "entityTest4"), EntityTest4.class, "entityTest4", 423, Mod_Test.instance, 40,
                1, true, new Color(255, 255, 0).getRGB(), new Color(0, 0, 0).getRGB());

        MinecraftForge.EVENT_BUS.register(this);
    }

}