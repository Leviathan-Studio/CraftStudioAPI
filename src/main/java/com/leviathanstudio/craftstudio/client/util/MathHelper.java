package com.leviathanstudio.craftstudio.client.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MathHelper
{
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
}
