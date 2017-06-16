package com.leviathanstudio.craftstudio.client.exception;

import java.io.FileNotFoundException;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Exception raised when opening a .csjsmodel or .csjsmodelanim file has failed.
 *
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public class CSResourceNotFoundException extends FileNotFoundException
{
    private static final long serialVersionUID = -3495512420502088386L;

    /**
     * Create an exception for a resource not found.
     *
     * @param resourceIn
     *            The resource that wasn't found.
     */
    public CSResourceNotFoundException(String resourceIn) {
        super("Resource not found: " + resourceIn);
    }
}
