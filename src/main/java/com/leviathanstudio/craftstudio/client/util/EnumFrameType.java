package com.leviathanstudio.craftstudio.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Enumeration of the different type of keyframe field type.
 *
 * @author ZeAmateis
 * @author Phenix246
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public enum EnumFrameType {
    POSITION, ROTATION, OFFSET, SIZE, STRETCH;
}
