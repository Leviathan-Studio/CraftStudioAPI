package fr.zeamateis.test.anim.common;

import java.awt.Color;
import java.util.HashMap;

import fr.zeamateis.test.anim.client.TileEntityBlockTest;
import fr.zeamateis.test.anim.common.animations.ChannelBlockAnimation;
import fr.zeamateis.test.anim.common.animations.ChannelIdleAnimation;
import fr.zeamateis.test.proxy.CommonProxy;
import lib.craftstudio.CraftStudioApi;
import lib.craftstudio.common.IAnimated;
import lib.craftstudio.common.animation.AnimationHandler;
import lib.craftstudio.common.animation.Channel;
import net.minecraft.block.Block;
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
    @SidedProxy(clientSide = "fr.zeamateis.tesm.proxy.ClientProxy", serverSide = "fr.zeamateis.tesm.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Instance("testmod")
    private static Mod_Test    instance;

    public static Mod_Test getInstance() {
        return Mod_Test.instance;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception {
        CraftStudioApi.preInit();

        GameRegistry.registerTileEntity(TileEntityBlockTest.class, "TileEntityBlockTest");

        Block blockTest = new BlockTest();
        GameRegistry.registerBlock(blockTest, "blockTest");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Mod_Test.proxy.init();

        EntityRegistry.registerModEntity(EntityTest.class, "entityTest", 420, Mod_Test.instance, 40, 1, true, new Color(0, 255, 0).getRGB(),
                new Color(255, 0, 0).getRGB());

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * The Animation handler class to register all animations you want in static
     * method <br>
     * (The class is in Mod_Test class but it could be independent)
     */
    public static class AnimationHandlerTest extends AnimationHandler
    {
        public AnimationHandlerTest(IAnimated entity) {
            super(entity);
        }

        /** Map with all the animations. */
        public static HashMap<String, Channel> animChannels = new HashMap<>();

        /** Register the animation(s) */
        static {
            AnimationHandlerTest.animChannels.put("block", new ChannelBlockAnimation("block", 30.0F));
            AnimationHandlerTest.animChannels.put("idle", new ChannelIdleAnimation("idle", 30.0F));
        }

        @Override
        public void executeAnimation(String name, float startingFrame) {
            super.executeAnimation(AnimationHandlerTest.animChannels, name, startingFrame);
        }

        @Override
        public void stopAnimation(String name) {
            super.stopAnimation(AnimationHandlerTest.animChannels, name);
        }

    }
}