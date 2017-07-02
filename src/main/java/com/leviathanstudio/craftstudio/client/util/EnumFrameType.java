package com.leviathanstudio.craftstudio.client.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Enumeration of the different type of keyframe field type.
 * 
 * @since 0.3.0
 *
 * @author ZeAmateis
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public enum EnumFrameType {
    POSITION, ROTATION, OFFSET, SIZE, STRETCH;
}
