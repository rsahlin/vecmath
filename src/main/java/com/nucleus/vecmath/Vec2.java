package com.nucleus.vecmath;

/**
 * Utility class for euclidean 2d vector operations, ie the vector is made up of direction and magnitude.
 * A 2D vector is made up of 3 float values, the methods in this class either take this class as parameter or just the
 * float[] with an index.
 *
 */
public final class Vec2 extends VecMath {

    public final static int MAGNITUDE = 2;
    public final float[] vector = new float[3];

    public Vec2() {

    }

    /**
     * Creates a 2D vector by normalizing the x and y values the length as the
     * magnitude.
     * 
     * @param x
     * @param y
     */
    public Vec2(float x, float y) {
        float length = length(x, y);
        vector[X] = x / length;
        vector[Y] = y / length;
        vector[MAGNITUDE] = length;
    }

    /**
     * Creates a new 2 vector from p1 to p2, vector will not be normalized.
     * 
     * @param p1
     * @param p2
     */
    public Vec2(float[] p1, float[] p2) {
        vector[X] = p2[X] - p1[X];
        vector[Y] = p2[Y] - p1[Y];
        vector[MAGNITUDE] = 1;
    }

    /**
     * Normalizes the vector, magnitude will be 1
     */
    public void normalize() {
        float length = getLength();
        vector[X] = vector[X] / length;
        vector[Y] = vector[Y] / length;
        vector[MAGNITUDE] = 1;
    }

    /**
     * Creates a 2D vector by normalizing the first 2 (x and y) values in the input array and storing the length as the
     * magnitude.
     * 
     * @param values
     */
    public Vec2(float[] values) {
        float length = length(values[X], values[Y]);
        vector[X] = values[X] / length;
        vector[Y] = values[Y] / length;
        vector[MAGNITUDE] = length;
    }

    /**
     * Computes the length (hypothenuse) of the 2D vector made up of x and y.
     * 
     * @param x
     * @param y
     * @return Length of the 2D vector.
     */
    public final static float length(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the dot product of this Vector and vector2
     * 
     * @param vector2
     * @return The dot product of the 2 Vectors
     */
    public final float dot(Vec2 vector2) {
        return vector[X] * vector2.vector[X] + vector[Y] * vector2.vector[Y];
    }

    /**
     * Calculate the dot product of this Vector and vector2
     * 
     * @param vector2 Float array with 2 values for x and y.
     * @return The dot product of the 2 Vectors
     */
    public final float dot(float[] vector2) {
        return vector[X] * vector2[X] + vector[Y] * vector2[Y];
    }

    /**
     * Limit 2 components to the max value specified.
     * 
     * @param vector Array with at least 2 values.
     * @param max Max value for X and Y
     */
    public final static void max(float[] vector, float max) {
        if (vector[X] > max) {
            vector[X] = max;
        }
        if (vector[Y] > max) {
            vector[Y] = max;
        }
    }

    /**
     * Limit 2 components to the min value specified.
     * 
     * @param vector Array with at least 2 values.
     * @param max Min value for X and Y
     */
    public final static void min(float[] vector, float min) {
        if (vector[X] < min) {
            vector[X] = min;
        }
        if (vector[Y] < min) {
            vector[Y] = min;
        }
    }

    /**
     * Calculates the dot product between two 2 dimensional vectors.
     * 
     * @param v1
     * @param v2
     * @return The dot product
     */
    public static final float dot2D(float[] v1, float[] v2) {
        return v1[X] * v2[X]
                + v1[Y] * v2[Y];
    }

    /**
     * Rotates a 2 dimensional vector along the z axis.
     * 
     * @paran source The source vector
     * @param destination The destination vector
     * @param angle
     */
    public static final void rotateZAxis(float[] source, float[] destination, float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        destination[1] = (source[0] * sin) + (source[1] * cos);
        destination[0] = (source[0] * cos) - (source[1] * sin);
    }

    /**
     * Rotates a 2 dimensional vector along the z axis.
     * 
     * @paran source The source vector
     * @param sourceindex Index into the source array where the 2 vector values are
     * @param destination The destination vector
     * @param destindex Index into the destination array where the 2 vector values shall be stored
     * @param angle
     */
    public static final void rotateZAxis(float[] source, int sourceindex, float[] destination, int destindex,
            float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        destination[destindex++] = (source[sourceindex] * cos) - (source[sourceindex + 1] * sin);
        destination[destindex] = (source[sourceindex] * sin) + (source[sourceindex + 1] * cos);

    }

    /**
     * Sets the destination vector, going from vector2 to vector1
     * 
     * @param vector1
     * @param vector2
     * @param destination vector1 - vector2
     */
    public final static void set(float[] vector1, float[] vector2, float[] destination) {
        destination[0] = vector1[0] - vector2[0];
        destination[1] = vector1[1] - vector2[1];
    }

    /**
     * Multiplies the magnitude by a scalar value, ie changing the velocity
     * 
     * @param scalar
     */
    public void mult(float scalar) {
        vector[MAGNITUDE] = vector[MAGNITUDE] * scalar;
    }

    /**
     * Returns the length (hypothenuse) of this vector
     * 
     * @return
     */
    public float getLength() {
        float x = vector[X] * vector[MAGNITUDE];
        float y = vector[Y] * vector[MAGNITUDE];
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Returns the sine of the vector angle - sin A = Y / HYP
     * 
     * @return
     */
    public float getSin() {
        return (vector[Y] * vector[MAGNITUDE]) / getLength();
    }

    /**
     * Returns the cos of the vector angle - cos A = X / HYP
     * 
     * @return
     */
    public float getCos() {
        return (vector[X] * vector[MAGNITUDE]) / getLength();
    }

}
