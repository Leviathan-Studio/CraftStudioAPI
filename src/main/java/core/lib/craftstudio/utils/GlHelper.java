package lib.craftstudio.utils;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Universal GlStateManager class for < 1.8 mc version */
@SideOnly(Side.CLIENT)
public class GlHelper
{
    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    public static void multMatrix(FloatBuffer matrix) {
        GL11.glMultMatrix(matrix);
    }

    public static void callList(int list) {
        GL11.glCallList(list);
    }

    public static void glNewList(int p_187423_0_, int p_187423_1_) {
        GL11.glNewList(p_187423_0_, p_187423_1_);
    }

    public static void glEndList() {
        GL11.glEndList();
    }
}