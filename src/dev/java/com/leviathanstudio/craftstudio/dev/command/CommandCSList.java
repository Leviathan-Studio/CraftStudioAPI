package com.leviathanstudio.craftstudio.dev.command;

import java.util.Arrays;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Command to list all the models or animations.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
@OnlyIn(Dist.CLIENT)
public class CommandCSList
{

    private static String       name      = "cslist";
    private static String       usage     = "/cslist models/animations";
    private static int          permLevel = 0;
    private static List<String> autoC     = Arrays.<String> asList(new String[] { "models", "animations" });

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
    	dispatcher.register(Commands.literal(name)
    			.requires(src -> src.hasPermissionLevel(permLevel)));

    }


    /*@Override
    public String getUsage(ICommandSender sender) {
        return CommandCSList.usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            Set<ResourceLocation> set;
            if (args[0].equals("models"))
                set = RegistryHandler.modelRegistry.getKeys();
            else if (args[0].equals("animations"))
                set = RegistryHandler.animationRegistry.getKeys();
            else
                throw new SyntaxErrorException();
            String str = "";
            for (ResourceLocation res : set) {
                if (!str.equals(""))
                    str = str + ", ";
                str = str + res.toString();
            }
            sender.sendMessage(new TextComponentString(str));
        }
        else
            throw new WrongUsageException(CommandCSList.usage);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, CommandCSList.autoC) : Collections.<String> emptyList();
    }*/
}
