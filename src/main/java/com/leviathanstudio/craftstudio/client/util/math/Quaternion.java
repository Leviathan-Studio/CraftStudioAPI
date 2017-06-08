package com.leviathanstudio.craftstudio.client.util.math;

import javax.vecmath.Quat4f;

/**
 * A 4 element unit quaternion represented by single precision floating point
 * x,y,z,w coordinates. The quaternion is always normalized.
 */
public class Quaternion implements Cloneable
{
    public float x, y, z, w;

    public Quaternion(float x, float y, float z, float w) {
        float mag;
        mag = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        this.x = x * mag;
        this.y = y * mag;
        this.z = z * mag;
        this.w = w * mag;
    }

    public Quaternion() {
        this(0F, 0F, 0F, 1F);
    }

    public Quaternion(Vector3f vec) {
        this(vec.x, vec.y, vec.z);
    }

    /** Most used constructor to directly use degrees angles */
    public Quaternion(float pitch, float yaw, float roll) {
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

        this.w = coss.x * coss.y * coss.z + sins.x * sins.y * sins.z;
        this.x = sins.x * coss.y * coss.z + coss.x * sins.y * sins.z;
        this.y = coss.x * sins.y * coss.z - sins.x * coss.y * sins.z;
        this.z = coss.x * coss.y * sins.z - sins.x * sins.y * coss.z;
    }

    /** Sets quaternion from the given matrix. */
    public Quaternion(Matrix4f mat) {
        final double T = 1 + mat.m00 + mat.m11 + mat.m22;
        if (T > 0.00000001) // to avoid large distortions!
        {
            final double S = Math.sqrt(T) * 2;
            this.x = (float) ((mat.m12 - mat.m21) / S);
            this.y = (float) ((mat.m02 - mat.m20) / S);
            this.z = (float) ((mat.m10 - mat.m01) / S);
            this.w = (float) (0.25 * S);
        }
        else if (T == 0)
            if (mat.m00 > mat.m11 && mat.m00 > mat.m22) { // Column 0:
                final double S = Math.sqrt(1.0 + mat.m00 - mat.m11 - mat.m22) * 2;
                this.x = (float) (0.25 * S);
                this.y = (float) ((mat.m10 + mat.m01) / S);
                this.z = (float) ((mat.m02 + mat.m20) / S);
                this.w = (float) ((mat.m21 - mat.m12) / S);
            }
            else if (mat.m11 > mat.m22) { // Column 1:
                final double S = Math.sqrt(1.0 + mat.m11 - mat.m00 - mat.m22) * 2;
                this.x = (float) ((mat.m10 + mat.m01) / S);
                this.y = (float) (0.25 * S);
                this.z = (float) ((mat.m21 + mat.m12) / S);
                this.w = (float) ((mat.m02 - mat.m20) / S);
            }
            else { // Column 2:
                final double S = Math.sqrt(1.0 + mat.m22 - mat.m00 - mat.m11) * 2;
                this.x = (float) ((mat.m02 + mat.m20) / S);
                this.y = (float) ((mat.m21 + mat.m12) / S);
                this.z = (float) (0.25 * S);
                this.w = (float) ((mat.m10 - mat.m01) / S);
            }
    }
    
    public void set(Quaternion q){
        this.w = q.w;
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
    }

    /**
     * Sets the value of this quaternion to the quaternion product of this and
     * q1.
     */
    public final void mul(Quaternion q1) {
        this.set(mul(this, q1));
    }

    /**
     * Sets the value of this quaternion to the quaternion product of
     * quaternions q1 and q2 (this = q1 * q2). Note that this is safe for
     * aliasing (e.g. this can be q1 or q2). This operation is used for adding
     * the 2 orientations.
     */
    public static final Quaternion mul(Quaternion q1, Quaternion q2) {
        Quaternion nq = new Quaternion();
        nq.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
        nq.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
        nq.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
        nq.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
        return nq;
    }

    /**
     * <code>slerp</code> sets this quaternion's value as an interpolation
     * between two other quaternions.
     *
     * @param q1
     *            the first quaternion.
     * @param q2
     *            the second quaternion.
     * @param t
     *            the amount to interpolate between the two quaternions.
     */
    public Quaternion slerp(Quaternion q1, Quaternion q2, float t) {
        // Create a local quaternion to store the interpolated quaternion
        if (q1.x == q2.x && q1.y == q2.y && q1.z == q2.z && q1.w == q2.w){
            this.set(q1);
            return this;
        }

        float result = q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;

        if (result < 0.0f) {
            // Negate the second quaternion and the result of the dot product
            q2.x = -q2.x;
            q2.y = -q2.y;
            q2.z = -q2.z;
            q2.w = -q2.w;
            result = -result;
        }

        // Set the first and second scale for the interpolation
        float scale0 = 1 - t;
        float scale1 = t;

        // Check if the angle between the 2 quaternions was big enough to
        // warrant such calculations
        if (1 - result > 0.1f) {// Get the angle between the 2 quaternions,
                                // and then store the sin() of that angle
            final float theta = (float) Math.acos(result);
            final float invSinTheta = (float) (1 / Math.sin(theta));

            // Calculate the scale for q1 and q2, according to the angle and
            // it's sine value
            scale0 = (float) (Math.sin((1 - t) * theta) * invSinTheta);
            scale1 = (float) (Math.sin(t * theta) * invSinTheta);
        }

        // Calculate the x, y, z and w values for the quaternion by using a
        // special
        // form of linear interpolation for quaternions.
        this.x = scale0 * q1.x + scale1 * q2.x;
        this.y = scale0 * q1.y + scale1 * q2.y;
        this.z = scale0 * q1.z + scale1 * q2.z;
        this.w = scale0 * q1.w + scale1 * q2.w;

        // Return the interpolated quaternion
        return this;
    }

    /**
     * Sets the values of this quaternion to the slerp from itself to q2 by
     * changeAmnt
     *
     * @param q2
     *            Final interpolation value
     * @param changeAmnt
     *            The amount diffrence
     */
    public void slerp(Quaternion q2, float changeAmnt) {
        this.slerp(this, q2, changeAmnt);
    }

    @Override
    public Quaternion clone() {
        try {
            return (Quaternion) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}