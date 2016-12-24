package lib.craftstudio.common.math;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class Quat4fHelper
{
    public static Vector3f eulerAnglesFromQuaternion(Quat4f quat) {
        return new Vector3f(pitch(quat), yaw(quat), roll(quat));
    }

    public static float pitch(Quat4f quat) {
        return (float) Math.atan2(2.0F * (quat.y * quat.z + quat.w * quat.x), quat.w * quat.w - quat.x * quat.x - quat.y * quat.y + quat.z * quat.z);
    }

    public static float yaw(Quat4f quat) {
        return (float) Math.asin(-2.0F * (quat.x * quat.z - quat.w * quat.y));
    }

    public static float roll(Quat4f quat) {
        return (float) Math.atan2(2.0F * (quat.x * quat.y + quat.w * quat.z), quat.w * quat.w + quat.x * quat.x - quat.y * quat.y - quat.z * quat.z);
    }

    public static Quat4f quaternionFromEulerAngles(float pitch, float yaw, float roll) {
        Vector3f coss = new Vector3f();
        coss.x = (float) Math.cos(pitch * 0.5F);
        coss.y = (float) Math.cos(yaw * 0.5F);
        coss.z = (float) Math.cos(roll * 0.5F);
        Vector3f sins = new Vector3f();
        sins.x = (float) Math.sin(pitch * 0.5F);
        sins.y = (float) Math.sin(yaw * 0.5F);
        sins.z = (float) Math.sin(roll * 0.5F);

        float w = coss.x * coss.y * coss.z + sins.x * sins.y * sins.z;
        float x = sins.x * coss.y * coss.z + coss.x * sins.y * sins.z;
        float y = coss.x * sins.y * coss.z - sins.x * coss.y * sins.z;
        float z = coss.x * coss.y * sins.z - sins.x * sins.y * coss.z;

        return new Quat4f(x, y, z, w);
    }

    public static Quat4f quaternionFromEulerAnglesInDegrees(float pitch, float yaw, float roll) {
        float pitchRadians = (float) Math.toRadians(pitch);
        float yawRadians = (float) Math.toRadians(yaw);
        float rollRadians = (float) Math.toRadians(roll);
        return quaternionFromEulerAngles(pitchRadians, yawRadians, rollRadians);
    }

}