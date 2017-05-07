package com.leviathanstudio.craftstudio.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import com.leviathanstudio.craftstudio.common.math.Matrix4f;
import com.leviathanstudio.craftstudio.common.math.Quaternion;
import com.leviathanstudio.craftstudio.common.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Utils
{
    private static int textureWidth, textureHeight;

    /**
     * Make a direct NIO FloatBuffer from an array of floats
     *
     * @param arr
     *            The array
     * @return The newly created FloatBuffer
     */
    public static FloatBuffer makeFloatBuffer(float[] arr) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        bb.order(ByteOrder.nativeOrder());
        final FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    /**
     * Make a direct NIO ByteBuffer from an array of floats
     *
     * @param arr
     *            The array
     * @return The newly created FloatBuffer
     */
    public static ByteBuffer makeByteBuffer(byte[] arr) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
        bb.order(ByteOrder.nativeOrder());
        bb.put(arr);
        bb.position(0);
        return bb;
    }

    /** Get the quaternion from a matrix. We need to transpose the matrix. */
    public static Quaternion getQuaternionFromMatrix(Matrix4f matrix) {
        final Matrix4f copy = new Matrix4f(matrix);
        return new Quaternion(copy.transpose());
    }

    /** Get the quaternion from euler angles. Minecraft eulers order is XYZ. */
    public static Quaternion getQuaternionFromEulers(float x, float y, float z) {
        final Quaternion quatX = new Quaternion(Vector3f.UNIT_X, (float) Math.toRadians(x));
        final Quaternion quatY = new Quaternion(Vector3f.UNIT_Y, (float) Math.toRadians(y));
        final Quaternion quatZ = new Quaternion(Vector3f.UNIT_Z, (float) Math.toRadians(z));
        quatY.mul(quatY, quatX);
        quatZ.mul(quatZ, quatY);
        return quatZ;
    }

    public static int getTextureHeight() {
        return Utils.textureHeight;
    }

    public static void setTextureHeight(int texHeight) {
        Utils.textureHeight = texHeight;
    }

    public static int getTextureWidth() {
        return Utils.textureWidth;
    }

    public static void setTextureWidth(int texWidth) {
        Utils.textureWidth = texWidth;
    }

    public static void readTextureFileSize(ResourceLocation textureLocation) {
        try {
            final BufferedImage bimg = ImageIO
                    .read(new BufferedInputStream(Minecraft.getMinecraft().getResourceManager().getResource(textureLocation).getInputStream()));
            Utils.setTextureWidth(bimg.getWidth());
            Utils.setTextureHeight(bimg.getHeight());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
