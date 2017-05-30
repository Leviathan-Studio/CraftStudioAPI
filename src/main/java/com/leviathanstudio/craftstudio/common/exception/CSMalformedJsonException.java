package com.leviathanstudio.craftstudio.common.exception;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSMalformedJsonException extends Exception
{
    private static final long serialVersionUID = -3495512445212088386L;

    public CSMalformedJsonException(String field, String type, String ress)
    {
        super("Missing field " + field + " of type " + type + " in " + ress);
    }

    public CSMalformedJsonException(String element, String ress)
    {
        super("Malformation of " + element + " in " + ress);
    }
}
