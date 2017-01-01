package lib.craftstudio.common.animation;

import java.util.HashMap;

import lib.craftstudio.common.math.Quaternion;
import lib.craftstudio.common.math.Vector3f;

public class KeyFrame
{
    public HashMap<String, Quaternion> modelRenderersRotations    = new HashMap<String, Quaternion>();
    public HashMap<String, Vector3f>   modelRenderersTranslations = new HashMap<String, Vector3f>();

    public boolean useBoxInRotations(String boxName) {
        return this.modelRenderersRotations.get(boxName) != null;
    }

    public boolean useBoxInTranslations(String boxName) {
        return this.modelRenderersTranslations.get(boxName) != null;
    }
}