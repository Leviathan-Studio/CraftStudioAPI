package com.leviathanstudio.craftstudio.common.animation;

import java.util.HashMap;

import com.leviathanstudio.craftstudio.common.math.Quaternion;
import com.leviathanstudio.craftstudio.common.math.Vector3f;

public class KeyFrame
{
    public HashMap<String, Quaternion> modelRenderersRotations    = new HashMap<>();
    public HashMap<String, Vector3f>   modelRenderersTranslations = new HashMap<>();

    public boolean useBoxInRotations(String boxName) {
        return this.modelRenderersRotations.get(boxName) != null;
    }

    public boolean useBoxInTranslations(String boxName) {
        return this.modelRenderersTranslations.get(boxName) != null;
    }
}