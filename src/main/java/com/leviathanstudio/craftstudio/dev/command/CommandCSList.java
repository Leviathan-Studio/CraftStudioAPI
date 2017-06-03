package com.leviathanstudio.craftstudio.dev.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.common.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.dev.util.UVMapCreator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommandCSList extends CommandBase implements IClientCommand {
	
	private static String name = "cslist";
	private static String usage = "cslist models/animations";
	private static int permLevel = 0;
	private static List<String> autoC = Arrays.<String>asList(new String[] {"models",
			"animations"
	});
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return this.usage;
	}
	

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 1){
			Set<ResourceLocation> set;
			if (args[0].equals("models"))
				set = GameRegistry.findRegistry(CSReadedModel.class).getKeys();
			else if (args[0].equals("animations"))
				set = GameRegistry.findRegistry(CSReadedAnim.class).getKeys();
			else
				throw new SyntaxErrorException();
			String str = "";
			for (ResourceLocation res : set){
				if (!str.equals(""))
					str = str + ", ";
				str = str + res.toString();
			}
			sender.sendMessage(new TextComponentString(str));
		}
	}
	
	@Override
	public int getRequiredPermissionLevel()
    {
        return this.permLevel;
    }
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, this.autoC) : Collections.<String>emptyList();
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return false;
	}
}
