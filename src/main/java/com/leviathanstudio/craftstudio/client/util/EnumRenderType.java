package com.leviathanstudio.craftstudio.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Enumeration of the different render type.
 *
 * @author ZeAmateis
 * @author Phenix246
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
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
