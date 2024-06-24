package com.leviathanstudio.craftstudio.client.json;

import com.leviathanstudio.craftstudio.client.util.EnumFrameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Vector3f;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that store information relative to an animated block.</br>
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSReadedAnimBlock {
    private String name;
    private Map<Integer, ReadedKeyFrame> keyFrames = new HashMap<>();

    /**
     * Add an element to a keyframe. If the keyframe does exist it's created.
     *
     * @param keyFrame Key of the keyframe.
     * @param type     Type of the element. See {@link CSReadedAnimBlock}.
     * @param value    Value of the element.
     */
    public void addKFElement(int keyFrame, EnumFrameType type, Vector3f value) {
        if (!this.keyFrames.containsKey(keyFrame))
            this.keyFrames.put(keyFrame, new ReadedKeyFrame());
        switch (type) {
            case POSITION:
                this.keyFrames.get(keyFrame).position = value;
                break;
            case ROTATION:
                this.keyFrames.get(keyFrame).rotation = value;
                break;
            case OFFSET:
                this.keyFrames.get(keyFrame).offset = value;
                break;
            case SIZE:
                this.keyFrames.get(keyFrame).size = value;
                break;
            case STRETCH:
                this.keyFrames.get(keyFrame).stretching = value;
                break;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, ReadedKeyFrame> getKeyFrames() {
        return this.keyFrames;
    }

    public void setKeyFrames(Map<Integer, ReadedKeyFrame> keyFrames) {
        this.keyFrames = keyFrames;
    }

    /**
     * Class used to store informations relative to keyframes.
     *
     * @author Timmypote
     * @since 0.3.0
     */
    public class ReadedKeyFrame {
        public Vector3f position, rotation, offset, size, stretching;
    }
}
