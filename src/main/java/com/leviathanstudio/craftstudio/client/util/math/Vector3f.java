package com.leviathanstudio.craftstudio.client.util.math;

import java.util.logging.Logger;

/**
 * Vector3f defines a Vector for a three float value tuple. Vector3f can
 * represent any three dimensional value, such as a vertex, a normal, etc.
 * Utility methods are also included to aid in mathematical calculations.
 */
public final class Vector3f implements Cloneable, java.io.Serializable
{

    static final long            serialVersionUID  = 1;

    private static final Logger  logger            = Logger.getLogger(Vector3f.class.getName());

    public final static Vector3f ZERO              = new Vector3f(0, 0, 0);
    public final static Vector3f NAN               = new Vector3f(Float.NaN, Float.NaN, Float.NaN);
    public final static Vector3f UNIT_X            = new Vector3f(1, 0, 0);
    public final static Vector3f UNIT_Y            = new Vector3f(0, 1, 0);
    public final static Vector3f UNIT_Z            = new Vector3f(0, 0, 1);
    public final static Vector3f UNIT_XYZ          = new Vector3f(1, 1, 1);
    public final static Vector3f POSITIVE_INFINITY = new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public final static Vector3f NEGATIVE_INFINITY = new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

    /**
     * the x value of the vector.
     */
    public float                 x;

    /**
     * the y value of the vector.
     */
    public float                 y;

    /**
     * the z value of the vector.
     */
    public float                 z;

    /**
     * Constructor instantiates a new Vector3f with default values of (0,0,0).
     *
     */
    public Vector3f() {
        this.x = this.y = this.z = 0;
    }

    /**
     * Constructor instantiates a new Vector3f with provides values.
     *
     * @param x
     *            the x value of the vector.
     * @param y
     *            the y value of the vector.
     * @param z
     *            the z value of the vector.
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor instantiates a new Vector3f that is a copy of the provided
     * vector
     *
     * @param copy
     *            The Vector3f to copy
     */
    public Vector3f(Vector3f copy) {
        this.set(copy);
    }

    /**
     * Set sets the x,y,z values of the vector based on passed parameters.
     *
     * @param x
     *            the x value of the vector.
     * @param y
     *            the y value of the vector.
     * @param z
     *            the z value of the vector.
     * @return this vector
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set sets the x,y,z values of the vector by copying the supplied vector.
     *
     * @param vect
     *            the vector to copy.
     * @return this vector
     */
    public Vector3f set(Vector3f vect) {
        this.x = vect.x;
        this.y = vect.y;
        this.z = vect.z;
        return this;
    }

    /**
     *
     * Add adds a provided vector to this vector creating a resultant vector
     * which is returned. If the provided vector is null, null is returned.
     *
     * @param vec
     *            the vector to add to this.
     * @return the resultant vector.
     */
    public Vector3f add(Vector3f vec) {
        return new Vector3f(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    /**
     *
     * Add adds the provided values to this vector, creating a new vector that
     * is then returned.
     *
     * @param addX
     *            the x value to add.
     * @param addY
     *            the y value to add.
     * @param addZ
     *            the z value to add.
     * @return the result vector.
     */
    public Vector3f add(float addX, float addY, float addZ) {
        return new Vector3f(this.x + addX, this.y + addY, this.z + addZ);
    }

    /**
     * Sets this vector to the interpolation by changeAmnt from this to the
     * finalVec this=(1-changeAmnt)*this + changeAmnt * finalVec
     *
     * @param finalVec
     *            The final vector to interpolate towards
     * @param changeAmnt
     *            An amount between 0.0 - 1.0 representing a precentage change
     *            from this towards finalVec
     */
    public Vector3f interpolate(Vector3f finalVec, float changeAmnt) {
        this.set(this.interpolate(this, finalVec, changeAmnt));
        return this;
    }

    /**
     * Sets this vector to the interpolation by changeAmnt from beginVec to
     * finalVec this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
     *
     * @param beginVec
     *            the beging vector (changeAmnt=0)
     * @param finalVec
     *            The final vector to interpolate towards
     * @param changeAmnt
     *            An amount between 0.0 - 1.0 representing a precentage change
     *            from beginVec towards finalVec
     */
    public Vector3f interpolate(Vector3f beginVec, Vector3f finalVec, float changeAmnt) {
        this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
        this.z = (1 - changeAmnt) * beginVec.z + changeAmnt * finalVec.z;
        return this;
    }

    @Override
    public Vector3f clone() {
        try {
            return (Vector3f) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }

    /**
     * ToString returns the string representation of this vector. The format is:
     *
     * org.jme.math.Vector3f [X=XX.XXXX, Y=YY.YYYY, Z=ZZ.ZZZZ]
     *
     * @return the string representation of this vector.
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
