package com.leviathanstudio.craftstudio.client.json;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.util.math.Vector3f;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that store information relative to an animated block.</br>
 * Type of keyframe elements POS = position, ROT = rotation, OFS = offset from
 * pivot, SIZ = size, STR = stretching.
 *
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSReadedAnimBlock
{
    public static final byte            POS       = 0, ROT = 1, OFS = 2, SIZ = 3, STR = 4;
    public String                       name;
    public Map<Integer, ReadedKeyFrame> keyFrames = new HashMap<>();

    /**
     * Add an element to a keyframe. If the keyframe does exist it's created.
     *
     * @param keyFrame
     *            Key of the keyframe.
     * @param type
     *            Type of the element. See {@link CSReadedAnimBlock}.
     * @param value
     *            Value of the element.
     */
    public void addKFElement(int keyFrame, byte type, Vector3f value) {
        if (!this.keyFrames.containsKey(keyFrame))
            this.keyFrames.put(keyFrame, new ReadedKeyFrame());
        switch (type) {
            case POS:
                this.keyFrames.get(keyFrame).position = value;
                break;
            case ROT:
                this.keyFrames.get(keyFrame).rotation = value;
                break;
            case OFS:
                this.keyFrames.get(keyFrame).offset = value;
                break;
            case SIZ:
                this.keyFrames.get(keyFrame).size = value;
                break;
            case STR:
                this.keyFrames.get(keyFrame).stretching = value;
                break;
        }
    }

    /**
     * Class used to store informations relative to keyframes.
     */
    public class ReadedKeyFrame
    {
        public Vector3f position, rotation, offset, size, stretching;
    }
}
