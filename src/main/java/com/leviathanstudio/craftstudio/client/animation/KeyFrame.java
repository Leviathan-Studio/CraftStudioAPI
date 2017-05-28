package com.leviathanstudio.craftstudio.client.animation;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.util.math.Quaternion;
import com.leviathanstudio.craftstudio.util.math.Vector3f;

public class KeyFrame implements Cloneable
{
    protected Map<String, Quaternion> modelRenderersRotations    = new HashMap<>();
    protected Map<String, Vector3f>   modelRenderersTranslations = new HashMap<>();

    /** Check if box is in rotations */
    public boolean useBoxInRotations(String boxName) {
        return this.modelRenderersRotations.get(boxName) != null;
    }

    /** Check if box is in translations */
    public boolean useBoxInTranslations(String boxName) {
        return this.modelRenderersTranslations.get(boxName) != null;
    }

    /** Copy the keyframe */
    @Override
    public KeyFrame clone() {
        KeyFrame kf = new KeyFrame();
        kf.modelRenderersRotations = this.modelRenderersRotations;
        kf.modelRenderersTranslations = this.modelRenderersTranslations;
        return kf;
    }
}