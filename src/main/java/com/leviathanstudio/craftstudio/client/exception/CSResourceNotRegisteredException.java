package com.leviathanstudio.craftstudio.client.exception;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSResourceNotRegisteredException extends RuntimeException
{
    private static final long serialVersionUID = -3495512420502365486L;

    public CSResourceNotRegisteredException(String resourceNameIn) {
        super("You are trying to acces \"" + resourceNameIn + "\", but it's not registered");
    }
}