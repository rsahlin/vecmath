package com.nucleus.vecmath;

/**
 * Utility class for euclidean 2d vector operations, ie the vector is made up of direction and magnitude.
 * A 2D vector is made up of 3 float values, the methods in this class either take this class as parameter or just the
 * float[] with an index.
 *
 */
public final class Vector2D extends VecMath {

    public final static int MAGNITUDE = 2;
    public final float[] vector = new float[3];

    public Vector2D() {

    }

    /**
     * Creates a 2D vector by normalizing the x and y value and storing the length as the 3'rd component.
     * 
     * @param x
     * @param y
     */
    public Vector2D(float x, float y) {
        setNormalized(x, y);
    }

    /**
     * Sets x and y as normalized direction and magnitude.
     * 
     * @param x Size of vector x axis
     * @param y Size of vector y axis
     */
    public void setNormalized(float x, float y) {
        float length = length(x, y);
        vector[X] = x / length;
        vector[Y] = y / length;
        vector[MAGNITUDE] = length;
    }

    /**
     * Creates a 2D vector by normalizing the first 2 (x and y) values in the input array and storing the length as the
     * magnitude.
     * 
     * @param values
     */
    public Vector2D(float[] values) {
        float length = length(values[X], values[Y]);
        vector[X] = values[X] / length;
        vector[Y] = values[Y] / length;
        vector[MAGNITUDE] = length;
    }

    /**
     * Computes the length of the 2D vector made up of x and y.
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
    public final float dot(Vector2D vector2) {
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

}
