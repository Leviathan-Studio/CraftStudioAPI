package com.leviathanstudio.craftstudio.dev.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
/**
 * Command to generate a UV map for a specified model.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
@OnlyIn(Dist.CLIENT)
public class CommandCSUVMap {

    private static String name      = "csuvmap";
    private static String usage     = "/csuvmap model";
    private static int    permLevel = 0;

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
    	//getListOfStringsMatchingLastWord(RegistryHandler.modelRegistry.keySet());
    	//EntityArgument.entities()
    	dispatcher.register(Commands.literal(name)
    			.requires(src -> src.hasPermissionLevel(permLevel))
    			);
    	
    }

   /* public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            boolean succes = false;
            try {
                sender.sendMessage(new TextComponentString("Starting UVMap creation ..."));
                UVMapCreator uvc = new UVMapCreator(new ResourceLocation(args[0]));
                succes = uvc.createUVMap();
                if (!succes)
                    throw new CommandException("Fail to write the file");
                else
                    sender.sendMessage(new TextComponentString("UVMap finished."));
            } catch (CSResourceNotRegisteredException e) {
                throw new CommandException("Model not registered");
            }
            if (!succes)
                throw new CommandException("Unknown error");
        }
        else
            throw new WrongUsageException(CommandCSUVMap.usage);
    }*/


}
