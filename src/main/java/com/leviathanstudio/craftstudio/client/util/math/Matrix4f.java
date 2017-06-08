package com.leviathanstudio.craftstudio.client.util.math;

public class Matrix4f implements Cloneable
{
    public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;

    /**
     * Sets the value of this matrix to its transpose in place.
     */
    public final Matrix4f transpose() {
        float temp;

        temp = this.m10;
        this.m10 = this.m01;
        this.m01 = temp;

        temp = this.m20;
        this.m20 = this.m02;
        this.m02 = temp;

        temp = this.m30;
        this.m30 = this.m03;
        this.m03 = temp;

        temp = this.m21;
        this.m21 = this.m12;
        this.m12 = temp;

        temp = this.m31;
        this.m31 = this.m13;
        this.m13 = temp;

        temp = this.m32;
        this.m32 = this.m23;
        this.m23 = temp;

        return this;
    }

    /**
     * Sets the value of this matrix to the matrix conversion of the single
     * precision quaternion argument.
     *
     * @param q1
     *            the quaternion to be converted
     */
    public final Matrix4f set(Quaternion q1) {
        /*
         * How it is done: 1.0f - 2.0f*qy*qy - 2.0f*qz*qz, 2.0f*qx*qy -
         * 2.0f*qz*qw, 2.0f*qx*qz + 2.0f*qy*qw, 0.0f, 2.0f*qx*qy + 2.0f*qz*qw,
         * 1.0f - 2.0f*qx*qx - 2.0f*qz*qz, 2.0f*qy*qz - 2.0f*qx*qw, 0.0f,
         * 2.0f*qx*qz - 2.0f*qy*qw, 2.0f*qy*qz + 2.0f*qx*qw, 1.0f - 2.0f*qx*qx -
         * 2.0f*qy*qy, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
         */
        this.m00 = 1.0f - 2.0f * q1.y * q1.y - 2.0f * q1.z * q1.z;
        this.m10 = 2.0f * (q1.x * q1.y + q1.w * q1.z);
        this.m20 = 2.0f * (q1.x * q1.z - q1.w * q1.y);

        this.m01 = 2.0f * (q1.x * q1.y - q1.w * q1.z);
        this.m11 = 1.0f - 2.0f * q1.x * q1.x - 2.0f * q1.z * q1.z;
        this.m21 = 2.0f * (q1.y * q1.z + q1.w * q1.x);

        this.m02 = 2.0f * (q1.x * q1.z + q1.w * q1.y);
        this.m12 = 2.0f * (q1.y * q1.z - q1.w * q1.x);
        this.m22 = 1.0f - 2.0f * q1.x * q1.x - 2.0f * q1.y * q1.y;

        this.m03 = (float) 0.0;
        this.m13 = (float) 0.0;
        this.m23 = (float) 0.0;

        this.m30 = (float) 0.0;
        this.m31 = (float) 0.0;
        this.m32 = (float) 0.0;
        this.m33 = (float) 1.0;

        return this;
    }

    /**
     * Return a new Array with the 16 values of this matrix in order
     */
    public final float[] intoArray() {
        final float[] m = new float[16];

        m[0] = this.m00;
        m[1] = this.m01;
        m[2] = this.m02;
        m[3] = this.m03;
        m[4] = this.m10;
        m[5] = this.m11;
        m[6] = this.m12;
        m[7] = this.m13;
        m[8] = this.m20;
        m[9] = this.m21;
        m[10] = this.m22;
        m[11] = this.m23;
        m[12] = this.m30;
        m[13] = this.m31;
        m[14] = this.m32;
        m[15] = this.m33;

        return m;
    }

    @Override
    public Matrix4f clone() {
        try {
            return (Matrix4f) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}