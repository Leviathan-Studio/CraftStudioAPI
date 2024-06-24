package com.leviathanstudio.craftstudio.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Class containing useful math methods for the api.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class MathHelper {
    /**
     * Create a direct FloatBuffer from a Matrix4f.
     *
     * @param mat The Matrix4f.
     * @return The FloatBuffer.
     */
    public static FloatBuffer makeFloatBuffer(Matrix4f mat) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(64);
        bb.order(ByteOrder.nativeOrder());
        final FloatBuffer fb = bb.asFloatBuffer();
        fb.put(mat.m00);
        fb.put(mat.m01);
        fb.put(mat.m02);
        fb.put(mat.m03);
        fb.put(mat.m10);
        fb.put(mat.m11);
        fb.put(mat.m12);
        fb.put(mat.m13);
        fb.put(mat.m20);
        fb.put(mat.m21);
        fb.put(mat.m22);
        fb.put(mat.m23);
        fb.put(mat.m30);
        fb.put(mat.m31);
        fb.put(mat.m32);
        fb.put(mat.m33);
        fb.position(0);
        return fb;
    }

    /**
     * Create a new Quat4f representing the yaw, pitch and roll given(applied in
     * that order).
     *
     * @param rot The pitch, yaw and roll as a Vector3f(x=pitch, y=yaw, z=roll).
     * @return The new Quat4f.
     */
    public static Quat4f quatFromEuler(Vector3f rot) {
        return quatFromEuler(rot.x, rot.y, rot.z);
    }

    /**
     * Create a new Quat4f representing the yaw, pitch and roll given(applied in
     * that order).
     *
     * @param pitch The pitch.
     * @param yaw   The yaw.
     * @param roll  The roll.
     * @return The new Quat4f.
     */
    public static Quat4f quatFromEuler(float pitch, float yaw, float roll) {
        Quat4f quat = new Quat4f();
        pitch = (float) Math.toRadians(pitch);
        yaw = (float) Math.toRadians(yaw);
        roll = (float) Math.toRadians(roll);

        final Vector3f coss = new Vector3f();
        coss.x = (float) Math.cos(pitch * 0.5F);
        coss.y = (float) Math.cos(yaw * 0.5F);
        coss.z = (float) Math.cos(roll * 0.5F);
        final Vector3f sins = new Vector3f();
        sins.x = (float) Math.sin(pitch * 0.5F);
        sins.y = (float) Math.sin(yaw * 0.5F);
        sins.z = (float) Math.sin(roll * 0.5F);

        quat.w = coss.x * coss.y * coss.z + sins.x * sins.y * sins.z;
        quat.x = sins.x * coss.y * coss.z + coss.x * sins.y * sins.z;
        quat.y = coss.x * sins.y * coss.z - sins.x * coss.y * sins.z;
        quat.z = coss.x * coss.y * sins.z - sins.x * sins.y * coss.z;
        return quat;
    }
}
