package com.leviathanstudio.craftstudio.client.json;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Enumeration of the different render type.
 *
 * @author ZeAmateis
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public enum EnumRenderType {

    BLOCK("blocks/"), ENTITY("entity/");

    String folderName;

    private EnumRenderType(String folderNameIn) {
        this.folderName = folderNameIn;
    }

    /**
     * Get the folder associated with this render type.
     *
     * @return The folder as a string.
     */
    public String getFolderName() {
        return this.folderName;
    }
}
