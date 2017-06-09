package com.leviathanstudio.craftstudio.client.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MathHelper
{
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

    public static Quat4f quatFromEuler(Vector3f rot) {
        return quatFromEuler(rot.x, rot.y, rot.z);
    }

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
