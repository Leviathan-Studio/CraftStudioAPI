package com.leviathanstudio.craftstudio.client;

import java.io.FileNotFoundException;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Exception raised when opening a .csjsmodel file has failed.
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public class CraftStudioModelNotFound extends FileNotFoundException
{
    private static final long serialVersionUID = -3495512420502088386L;

    public CraftStudioModelNotFound(String model)
    {
        super("Model not found: " + model);
    }
}
