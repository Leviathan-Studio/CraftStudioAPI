package fr.zeamateis.test.anim.common.animations;

import com.leviathanstudio.craftstudio.common.animation.Channel;
import com.leviathanstudio.craftstudio.common.animation.KeyFrame;
import com.leviathanstudio.craftstudio.common.math.Quaternion;

public class ChannelBlockAnimation extends Channel
{
    public ChannelBlockAnimation(String name, float fps) {
        super(name, fps, 30, Channel.EnumAnimationMode.LOOP);
    }

    @Override
    protected void initializeAllFrames() {
        KeyFrame keyframe0 = new KeyFrame();
        KeyFrame keyframe15 = new KeyFrame();
        keyframe0.modelRenderersRotations.put("Block1", new Quaternion(0.0F, -0.0F, -0.0F));
        keyframe15.modelRenderersRotations.put("Block1", new Quaternion(0.0F, 180.0F, -0.0F));
        keyframe0.modelRenderersRotations.put("Block4", new Quaternion(0.0F, -0.0F, -0.0F));
        keyframe15.modelRenderersRotations.put("Block4", new Quaternion(-7.072892E-7F, -89.99999F, -7.072892E-7F));
        keyframe0.modelRenderersRotations.put("Block2", new Quaternion(0.0F, -0.0F, -0.0F));
        keyframe15.modelRenderersRotations.put("Block2", new Quaternion(-7.072892E-7F, 89.99999F, 7.072892E-7F));
        keyframe0.modelRenderersRotations.put("Block3", new Quaternion(0.0F, -0.0F, -0.0F));
        keyframe15.modelRenderersRotations.put("Block3", new Quaternion(0.0F, 180.0F, -0.0F));
        this.keyFrames.put(0, keyframe0);
        this.keyFrames.put(15, keyframe15);
    }
}
