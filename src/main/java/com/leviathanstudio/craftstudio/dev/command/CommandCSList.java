package com.leviathanstudio.craftstudio.dev.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CommandCSList extends CommandBase
{

    private static String       name      = "cslist";
    private static String       usage     = "cslist models/animations";
    private static int          permLevel = 0;
    private static List<String> autoC     = Arrays.<String> asList(new String[] { "models", "animations" });

    @Override
    public String getCommandName() {
        return CommandCSList.name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return CommandCSList.usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            Set<ResourceLocation> set;
            if (args[0].equals("models"))
                set = GameRegistry.findRegistry(CSReadedModel.class).getKeys();
            else if (args[0].equals("animations"))
                set = GameRegistry.findRegistry(CSReadedAnim.class).getKeys();
            else
                throw new SyntaxErrorException();
            String str = "";
            for (ResourceLocation res : set) {
                if (!str.equals(""))
                    str = str + ", ";
                str = str + res.toString();
            }
            sender.addChatMessage(new TextComponentString(str));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return CommandCSList.permLevel;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, CommandCSList.autoC) : Collections.<String> emptyList();
    }
}
