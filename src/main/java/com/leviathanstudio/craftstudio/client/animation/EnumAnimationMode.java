package com.leviathanstudio.craftstudio.client.animation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Enumeration of the possible animation mode.
 *
 * @author Timmypote
 * @author Phenix246
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public enum EnumAnimationMode {
    /**
     * An animation that play just once
     */
    LINEAR,
    /**
     * An animation that play once and hold the last keyframe
     */
    HOLD,
    /**
     * An animation that restart everytime it end
     */
    LOOP;
}
