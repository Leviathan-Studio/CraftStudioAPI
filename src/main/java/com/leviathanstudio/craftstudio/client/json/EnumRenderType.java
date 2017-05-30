package com.leviathanstudio.craftstudio.client.json;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum EnumRenderType
{

    BLOCK("blocks/"), ENTITY("entity/");

    String folderName;

    private EnumRenderType(String folderNameIn)
    {
        this.folderName = folderNameIn;
    }

    public String getFolderName()
    {
        return this.folderName;
    }
}
