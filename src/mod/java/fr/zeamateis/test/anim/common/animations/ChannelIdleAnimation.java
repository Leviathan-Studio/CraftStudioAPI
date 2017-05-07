package fr.zeamateis.test.anim.common.animations;

import com.leviathanstudio.craftstudio.common.animation.Channel;
import com.leviathanstudio.craftstudio.common.animation.KeyFrame;
import com.leviathanstudio.craftstudio.common.math.Quaternion;

public class ChannelIdleAnimation extends Channel
{
    public ChannelIdleAnimation(String name, float fps)
    {
        super(name, fps, 60, Channel.EnumAnimationMode.LOOP);
    }

    @Override
    protected void initializeAllFrames()
    {
        KeyFrame keyframe8 = new KeyFrame();
        KeyFrame keyframe0 = new KeyFrame();
        KeyFrame keyframe60 = new KeyFrame();
        KeyFrame keyframe45 = new KeyFrame();
        KeyFrame keyframe30 = new KeyFrame();
        KeyFrame keyframe15 = new KeyFrame();
        keyframe0.modelRenderersRotations.put("Upper_Arm_Right", new Quaternion(0.0F, -0.0F, 4.171811F));
        keyframe15.modelRenderersRotations.put("Upper_Arm_Right", new Quaternion(0.0F, -0.0F, 1.254342F));
        keyframe30.modelRenderersRotations.put("Upper_Arm_Right", new Quaternion(0.0F, -0.0F, 6.620682F));
        keyframe45.modelRenderersRotations.put("Upper_Arm_Right", new Quaternion(0.0F, -0.0F, 2.199501F));
        keyframe60.modelRenderersRotations.put("Upper_Arm_Right", new Quaternion(0.0F, -0.0F, 4.17F));
        keyframe0.modelRenderersRotations.put("Upper_Arm_Left", new Quaternion(0.0F, -0.0F, -7.722006F));
        keyframe15.modelRenderersRotations.put("Upper_Arm_Left", new Quaternion(0.0F, -0.0F, -0.891409F));
        keyframe30.modelRenderersRotations.put("Upper_Arm_Left", new Quaternion(0.0F, -0.0F, -7.101573F));
        keyframe45.modelRenderersRotations.put("Upper_Arm_Left", new Quaternion(0.0F, -0.0F, -3.180463F));
        keyframe60.modelRenderersRotations.put("Upper_Arm_Left", new Quaternion(0.0F, -0.0F, -7.72F));
        keyframe0.modelRenderersRotations.put("Spine", new Quaternion(0.0F, -0.0F, -0.0F));
        keyframe8.modelRenderersRotations.put("Spine", new Quaternion(1.28F, -0.0F, -0.0F));
        keyframe30.modelRenderersRotations.put("Spine", new Quaternion(4.786639F, -0.0F, -0.0F));
        keyframe60.modelRenderersRotations.put("Spine", new Quaternion(0.0F, -0.0F, -0.0F));
        this.keyFrames.put(8, keyframe8);
        this.keyFrames.put(0, keyframe0);
        this.keyFrames.put(60, keyframe60);
        this.keyFrames.put(45, keyframe45);
        this.keyFrames.put(30, keyframe30);
        this.keyFrames.put(15, keyframe15);
    }
}
