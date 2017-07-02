package com.leviathanstudio.craftstudio.client.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Enumeration of the different resource type.
 * 
 * @since 0.3.0
 *
 * @author ZeAmateis
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public enum EnumResourceType {

    MODEL("craftstudio/models/", ".csjsmodel"), ANIM("craftstudio/animations/", ".csjsmodelanim");

    String path, extension;

    private EnumResourceType(String pathIn, String extensionIn) {
        this.path = pathIn;
        this.extension = extensionIn;
    }

    public String getPath() {
        return this.path;
    }

    public String getExtension() {
        return this.extension;
    }
}