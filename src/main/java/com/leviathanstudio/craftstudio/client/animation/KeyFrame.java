package com.leviathanstudio.craftstudio.client.animation;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.client.util.math.Quaternion;
import com.leviathanstudio.craftstudio.client.util.math.Vector3f;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyFrame implements Cloneable
{
    protected Map<String, Quaternion> modelRenderersRotations    = new HashMap<>();
    protected Map<String, Vector3f>   modelRenderersTranslations = new HashMap<>();
    protected Map<String, Vector3f>   modelRenderersOffsets = new HashMap<>();
    protected Map<String, Vector3f>   modelRenderersStretchs = new HashMap<>();

    /** Check if box is in rotations */
    public boolean useBoxInRotations(String boxName)
    {
        return this.modelRenderersRotations.get(boxName) != null;
    }

    /** Check if box is in translations */
    public boolean useBoxInTranslations(String boxName)
    {
        return this.modelRenderersTranslations.get(boxName) != null;
    }
    
    /** Check if box has offset modifications */
    public boolean useBoxInOffsets(String boxName)
    {
        return this.modelRenderersOffsets.get(boxName) != null;
    }
    
    /** Check if box has stretch modifications */
    public boolean useBoxInStretchs(String boxName)
    {
        return this.modelRenderersStretchs.get(boxName) != null;
    }

    /** Copy the keyframe */
    @Override
    public KeyFrame clone()
    {
        KeyFrame kf = new KeyFrame();
        kf.modelRenderersRotations = this.modelRenderersRotations;
        kf.modelRenderersTranslations = this.modelRenderersTranslations;
        return kf;
    }
}