package com.leviathanstudio.craftstudio.common.animation;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.common.math.Quaternion;
import com.leviathanstudio.craftstudio.common.math.Vector3f;

public class KeyFrame
{
    public Map<String, Quaternion> modelRenderersRotations    = new HashMap<>();
    public Map<String, Vector3f>   modelRenderersTranslations = new HashMap<>();

    public boolean useBoxInRotations(String boxName)
    {
        return this.modelRenderersRotations.get(boxName) != null;
    }

    public boolean useBoxInTranslations(String boxName)
    {
        return this.modelRenderersTranslations.get(boxName) != null;
    }
}