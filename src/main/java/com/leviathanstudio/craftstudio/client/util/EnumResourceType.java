package com.leviathanstudio.craftstudio.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Enumeration of the different resource type.
 *
 * @author ZeAmateis
 * @author Phenix246
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
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