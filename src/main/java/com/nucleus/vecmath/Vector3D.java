package com.nucleus.vecmath;

/**
 * 3 Component vector utilities
 * 
 * @author Richard Sahlin
 *
 */
public class Vector3D extends VecMath {

    /**
     * Returns the cross product vector from vector1 and vector2
     * cx = ay * bz − az * by
     * cy = az * bx − ax * bz
     * cz = ax * by − ay * bx
     * 
     * @param vector1
     * @param vector2
     * @return New 3 component vector that is the cross product of vector1 and vector2
     */
    public final static float[] cross3(float[] vector1, float[] vector2) {
        float[] cross = new float[3];
        cross[0] = vector1[Y] * vector2[Z] - vector1[Z] * vector2[Y];
        cross[1] = vector1[Z] * vector2[X] - vector1[X] * vector2[Z];
        cross[2] = vector1[X] * vector2[Y] - vector1[Y] * vector2[X];
        return cross;
    }

    /**
     * Sets a 3 component vector at the specified index
     * 
     * @param src
     * @param srcIndex
     * @param dest
     * @param destIndex
     */
    public final static void set(float[] src, int srcIndex, float[] dest, int destIndex) {
        dest[destIndex++] = src[srcIndex++];
        dest[destIndex++] = src[srcIndex++];
        dest[destIndex++] = src[srcIndex++];
    }

    public final static float[] subtract(float[] v1, int v1Index, float[] v2, int v2Index) {
        float[] result = new float[3];
        result[0] = v2[v1Index++] - v1[v2Index++];
        result[1] = v2[v1Index++] - v1[v2Index++];
        result[2] = v2[v1Index] - v1[v2Index];
        return result;
    }

    /**
     * Returns the cross product vector from vector1 and vector2
     * cx = ay * bz − az * by
     * cy = az * bx − ax * bz
     * cz = ax * by − ay * bx
     * 
     * @param data Array containing vector1 and vector2 and the destination
     * @param v1 index into array where vertex 1 is
     * @param v2 index into array where vertex 2 is
     * @param dest index to result
     */
    public final static void cross3(float[] data, int v1, int v2, int dest) {
        data[dest + X] = data[v1 + Y] * data[v2 + Z] - data[v1 + Z] * data[v2 + Y];
        data[dest + Y] = data[v1 + Z] * data[v2 + X] - data[v1 + X] * data[v2 + Z];
        data[dest + Z] = data[v1 + X] * data[v2 + Y] - data[v1 + Y] * data[v2 + X];
    }

}
