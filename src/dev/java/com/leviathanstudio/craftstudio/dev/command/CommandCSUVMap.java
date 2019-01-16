package com.leviathanstudio.craftstudio.dev.command;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.leviathanstudio.craftstudio.client.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.dev.util.UVMapCreator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Command to generate a UV map for a specified model.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CommandCSUVMap extends CommandBase implements IClientCommand
{

    private static String name      = "csuvmap";
    private static String usage     = "/csuvmap model";
    private static int    permLevel = 0;

    @Override
    public String getName() {
        return CommandCSUVMap.name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return CommandCSUVMap.usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
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
    }

    @Override
    public int getRequiredPermissionLevel() {
        return CommandCSUVMap.permLevel;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, RegistryHandler.modelRegistry.getKeys()) : Collections.<String> emptyList();
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
}
