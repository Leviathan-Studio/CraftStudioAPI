package fr.zeamateis.test.anim.common.animations;

import com.leviathanstudio.craftstudio.client.animation.CustomChannel;
import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.util.Util;
import com.leviathanstudio.craftstudio.util.math.Quaternion;

import fr.zeamateis.test.anim.common.EntityTest2;

public class TestCustomAnimation extends CustomChannel
{

    public TestCustomAnimation(String channelNameIn) {
        super(channelNameIn);
    }

    @Override
    public void update(CSModelRenderer parts, IAnimated animated) {
        EntityTest2 entityTest = (EntityTest2) animated;

        // Attemp to make LookAt animation, failed
        if (parts.boxName.equals("Head")) {
            float diff = entityTest.getRotationYawHead() - entityTest.renderYawOffset;
            Quaternion quat = Util.getQuaternionFromEulers(entityTest.rotationPitch, 0.0F, diff);
            Quaternion quat2 = new Quaternion(parts.getDefaultRotationAsQuaternion());
            quat2.mul(quat);
            parts.getRotationMatrix().set(quat2);
        }
    }

}
