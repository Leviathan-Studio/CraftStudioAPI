package com.leviathanstudio.craftstudio.client.animation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that store informations about a model at a specific time of an
 * animation.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class KeyFrame implements Cloneable {
    /**
     * Map of quaternions for block rotation
     */
    protected Map<String, Quat4f> modelRenderersRotations = new HashMap<>();
    /**
     * Map of vector for block translation
     */
    protected Map<String, Vector3f> modelRenderersTranslations = new HashMap<>();
    /**
     * Map of vector for block offset
     */
    protected Map<String, Vector3f> modelRenderersOffsets = new HashMap<>();
    /**
     * Map of vector for block stretch
     */
    protected Map<String, Vector3f> modelRenderersStretchs = new HashMap<>();

    /**
     * Check if box is in rotation.
     *
     * @param boxName The name of the box.
     * @return True if is in rotation, false if not.
     */
    public boolean useBoxInRotations(String boxName) {
        return this.modelRenderersRotations.get(boxName) != null;
    }

    /**
     * Check if box is in translation.
     *
     * @param boxName The name of the box.
     * @return True if is in translation, false if not.
     */
    public boolean useBoxInTranslations(String boxName) {
        return this.modelRenderersTranslations.get(boxName) != null;
    }

    /**
     * Check if box has offset modification.
     *
     * @param boxName The name of the box.
     * @return True if has offset modification, false if not.
     */
    public boolean useBoxInOffsets(String boxName) {
        return this.modelRenderersOffsets.get(boxName) != null;
    }

    /**
     * Check if box has stretch modification.
     *
     * @param boxName The name of the box.
     * @return True if has stretch modification, false if not.
     */
    public boolean useBoxInStretchs(String boxName) {
        return this.modelRenderersStretchs.get(boxName) != null;
    }

    @Override
    public KeyFrame clone() {
        KeyFrame kf = new KeyFrame();
        kf.modelRenderersRotations = this.modelRenderersRotations;
        kf.modelRenderersTranslations = this.modelRenderersTranslations;
        kf.modelRenderersOffsets = this.modelRenderersOffsets;
        kf.modelRenderersStretchs = this.modelRenderersStretchs;
        return kf;
    }
}