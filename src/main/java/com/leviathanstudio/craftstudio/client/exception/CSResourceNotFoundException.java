package com.leviathanstudio.craftstudio.client.exception;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.FileNotFoundException;

/**
 * Exception raised when opening a .csjsmodel or .csjsmodelanim file has failed.
 *
 * @author Phenix246
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSResourceNotFoundException extends FileNotFoundException {
    private static final long serialVersionUID = -3495512420502088386L;

    /**
     * Create an exception for a resource not found.
     *
     * @param resourceIn The resource that wasn't found.
     */
    public CSResourceNotFoundException(String resourceIn) {
        super("Resource not found: " + resourceIn);
    }
}
