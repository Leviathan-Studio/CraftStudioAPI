package com.leviathanstudio.craftstudio.client.json;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Enumeration of the different type of keyframe field type.
 *
 * @author ZeAmateis
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public enum EnumFrameType {
    POSITION, ROTATION, OFFSET, SIZE, STRETCH;
}
