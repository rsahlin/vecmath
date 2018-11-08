package com.nucleus.vecmath;

/**
 * Static utilities for linear interpolation
 */
public class Lerp {

    /**
     * Linear interpolation on a vec3 stored as float[]
     * result goes from a when w is 0 and b when w is 1
     * 
     * @param a
     * @param b
     * @param w weight, 0 = a, 1 = b
     * @param result
     * @param resultIndex
     * @return
     */
    public static final float[] lerpVec3(float[] a, float[] b, float w, float[] result, int resultIndex) {
        result[resultIndex++] = a[0] + w * (b[0] - a[0]);
        result[resultIndex++] = a[1] + w * (b[1] - a[1]);
        result[resultIndex++] = a[2] + w * (b[2] - a[2]);
        return result;
    }
    
}
