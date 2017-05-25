package com.leviathanstudio.craftstudio.common.exceptions;

import java.io.FileNotFoundException;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Exception raised when opening a .csjsmodel or .csjsmodelanim file has failed.
 *
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public class CSResourceNotFoundException extends FileNotFoundException {
	private static final long serialVersionUID = -3495512420502088386L;

	public CSResourceNotFoundException(String resourceIn) {
		super("Resource not found: " + resourceIn);
	}
}
