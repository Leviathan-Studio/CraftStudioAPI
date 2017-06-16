package com.leviathanstudio.craftstudio.client.animation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Enumeration of the possible animation mode.
 *
 * @author Timmypote
 * @author Phenix246
 */
@SideOnly(Side.CLIENT)
public enum EnumAnimationMode {
    /** An animation that play just once */
    LINEAR,
    /** An animation that play once and hold the last keyframe */
    HOLD,
    /** An animation that restart everytime it end */
    LOOP;
}
