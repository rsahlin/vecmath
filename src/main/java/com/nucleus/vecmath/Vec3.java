package com.nucleus.vecmath;

/**
 * 3 Component vector and utilities
 * 
 * @author Richard Sahlin
 *
 */
public class Vec3 extends VecMath {

    /**
     * The float Vector values.
     */
    public float[] values = new float[3];

    /**
     * Negate the values and return.
     * 
     * @return
     */
    public Vec3 negate() {
        values[0] = -values[0];
        values[1] = -values[1];
        values[2] = -values[2];
        return this;
    }

    /**
     * Constructor with 3 values for x, y and z.
     *
     * @param x
     * @param y
     * @param z
     */
    public Vec3(float x, float y, float z) {
        set(x, y, z);
    }

    /**
     * Set this Vector to be the halfwayvector of this + other Vector, result is normalized.
     * 
     * @param other
     */
    public void halfway(Vec3 other) {
        add(other);
        values[0] = values[0] / 2;
        values[1] = values[1] / 2;
        values[2] = values[2] / 2;
        this.normalize();
    }

    /**
     * Construct a Vector from the specified origin and endpoint.
     * 
     * @param origin
     * @param endpoint
     */
    public Vec3(float[] origin, float[] endpoint) {
        set(origin, endpoint);
    }

    /**
     * Constructor with array of float as parameter.
     * 
     * @param values
     * @throws IllegalArgumentException If values is null or
     * there is not enough values in the values array, must be at least 3
     */
    public Vec3(float[] values) {
        set(values, 0);
    }

    /**
     * Constructor with array and index as parameter, copy the values from the
     * specified index to a Vector3.
     * 
     * @param values Array containing values.
     * @param index Index into the values array where values are read.
     * @throws IllegalArgumentException If values is null or
     * there is not enough values in the values array, must be at least index + 3
     */
    public Vec3(float[] values, int index) {
        set(values, index);

    }

    /**
     * Constructor with Vector3 as source.
     * 
     * @param source
     * @throws IllegalArgumentException If source is null
     */
    public Vec3(Vec3 source) {
        if (source == null) {
            throw new IllegalArgumentException("Source vector is null");
        }
        set(source);
    }

    /**
     * Copy the values from the specified float array.
     * 
     * @param setValues
     * @param index
     * @throws IllegalArgumentException If values is null or does not contain index +
     * 3 values.
     */
    public void set(float[] setValues, int index) {
        if (setValues == null || setValues.length < index + 3) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        System.arraycopy(setValues, index, values, 0, 3);
    }

    /**
     * Copy the contents of the source Vector into this Vector.
     * 
     * @param source
     * @throws IllegalArgumentException If source is null.
     */
    public Vec3 set(Vec3 source) {
        if (source == null) {
            throw new IllegalArgumentException("Source vector is null");
        }
        System.arraycopy(source.values, 0, values, 0, 3);
        return this;
    }

    /**
     * Set this Vector to be the distance from origin to endpoint, origin and
     * enpoint must have (at least) 3 values each.
     * 
     * @param origin
     * @param endpoint
     * @throws IllegalArgumentException if origin or endpoint is null
     */
    public void set(float[] origin, float[] endpoint) {
        if (origin == null || endpoint == null) {
            throw new IllegalArgumentException("Invalid parameter - null");
        }
        values[0] = endpoint[0] - origin[0];
        values[1] = endpoint[1] - origin[1];
        values[2] = endpoint[2] - origin[2];
    }

    /**
     * Set the values of this Vector
     *
     * @param x
     * @param y
     * @param z
     */
    public void set(float x, float y, float z) {
        values[0] = x;
        values[1] = y;
        values[2] = z;
    }

    /**
     * Set Vector to 0
     */
    public void clear() {
        values[X] = 0;
        values[Y] = 0;
        values[Z] = 0;
    }

    /**
     * Normalize to unit length |a| = sqrt( (x*x) + (y*y) + (z*z) ) x = ax / |a|
     * y = ay / |a| z = az / |a|
     */
    public Vec3 normalize() {
        normalize(values, 0);
        return this;
    }

    /**
     * Normalizes the vector at index
     * 
     * @param values
     * @param index
     */
    public static final void normalize(float[] values, int index) {
        float len = (float) Math
                .sqrt((values[index + X] * values[index + X])
                        + (values[index + Y] * values[index + Y])
                        + (values[index + Z] * values[index + Z]));
        values[index + X] = values[index + X] / len;
        values[index + Y] = values[index + Y] / len;
        values[index + Z] = values[index + Z] / len;
    }

    /**
     * Normalize 2D vector in the specified array, storing back the result.
     * 
     * @param vectorArray with 2 dimensional vector
     * @param index Index into array where values are
     */
    public static void normalize2D(float[] vector, int index) {
        float x = vector[index];
        float y = vector[index + 1];
        float len = (float) Math.sqrt(x * x + y * y);
        vector[index] = x / len;
        vector[index + 1] = y / len;
    }

    /**
     * Calculate the length of the Vector. To get the normalized unit length
     * call normalize()
     * 
     * @return The length of the vector.
     */
    public float length() {
        return (float) Math
                .sqrt((values[X] * values[X])
                        + (values[Y] * values[Y])
                        + (values[Z] * values[Z]));

    }

    /**
     * Calculate the length of the Vector. To get the normalized unit length
     * call normalize()
     * Note that no error checking is done, the source array must contain 3 values at index.
     * 
     * @param vector Array with vector values
     * @param index Index into array where vector starts.
     * @return The length of the vector
     */
    public final static float length(float[] vector, int index) {
        return (float) Math
                .sqrt((vector[X + index] * vector[X + index])
                        + (vector[Y + index] * vector[Y + index])
                        + (vector[Z + index] * vector[Z + index]));

    }

    /**
     * Calculate the dot product between this Vertex and an array with 3 float
     * values (x,y,z). The angle between the directions of the two vectors. If
     * the angle is greater than 90 between the two vectors the dot product will
     * be negative (facing away)
     * 
     * @param vector2
     * @return
     */
    public float dot(float[] vertex2) {
        return dot(vertex2, 0);
    }

    /**
     * Calculate the dot product between this Vertex and 3 float values in the
     * array (x,y,z). The angle between the directions of the two vectors. If
     * the angle is greater than 90 between the two vectors the dot product will
     * be negative (facing away)
     * 
     * @param vertex2 Normalized vector.
     * @param index
     * @return The dot product
     */
    public float dot(float[] vertex2, int index) {
        // Calculate the dot product between this vector and vector2
        // Remember that this solution only works for normalized vectors
        // Otherwise ( u * v) / (|u| * |v|) should be used.
        // (the dot product divided by the sum of the magnitudes)

        return values[X] * vertex2[index++]
                + values[Y] * vertex2[index++]
                + values[Z] * vertex2[index++];
    }

    /**
     * returns ax * bx + ay * by + az * bz
     * 
     * @param vec1 ax, ay, az
     * @param vec2 bx, by, bz
     * @return ax * bx + ay * by + az * bz
     */
    public static float dot(float[] vec1, int index1, float[] vec2, int index2) {
        return vec1[index1++] * vec2[index2++] + vec1[index1++] * vec2[index2++] + vec1[index1] * vec2[index2];
    }

    /**
     * returns ax * bx + ay * by
     * 
     * @param vec1
     * @param vec2
     * @return ax * bx + ay * by
     */
    public static float dotZAxis(Vec3 vec1, Vec3 vec2) {
        return vec1.values[0] * vec2.values[0] + vec1.values[1] * vec2.values[1];
    }

    /**
     * Returns ay * by + az * bz
     * 
     * @param vec1
     * @param vec2
     * @return ay * by + az * bz
     */
    public static float dotXAxis(Vec3 vec1, Vec3 vec2) {
        return vec1.values[1] * vec2.values[1] + vec1.values[2] * vec2.values[2];
    }

    /**
     * returns ax * by − ay * bx
     * 
     * @param vec1
     * @param vec2
     * @return
     */
    public static float crossZAxis(Vec3 vec1, Vec3 vec2) {
        return vec1.values[0] * vec2.values[1] - vec1.values[1] * vec2.values[0];
    }

    /**
     * returns ay * bz - az * by
     * 
     * @param vec1
     * @param vec2
     * @return
     */
    public static float crossXAxis(Vec3 vec1, Vec3 vec2) {
        return vec1.values[1] * vec2.values[2] - vec1.values[2] * vec2.values[1];
    }

    /**
     * Calculate the cross product (direction of the plane defined by this
     * vector and v2). The result will be the surface 'normal' (not normalized),
     * stored in this vector.
     * 
     * @param values2
     */
    public void cross(float[] values2) {

        // Calculate the cross product of this and values2.
        float tmpX = values[1] * values2[2] - values[2] * values2[1];
        float tmpY = values[2] * values2[0] - values[0] * values2[2];
        float tmpZ = values[0] * values2[1] - values[1] * values2[0];

        values[0] = tmpX;
        values[1] = tmpY;
        values[2] = tmpZ;
    }

    /**
     * Create the cross product based on the plane defined by
     * (endpoint1-origin), (endpoint2-origin)
     * 
     * @param origin
     * @param endpoint1
     * @param endpoint2
     */
    public void crossFromPoints(float[] origin, float[] endpoint1, float[] endpoint2) {
        values[0] = (endpoint1[1] - origin[1]) * (endpoint2[2] - origin[2])
                - (endpoint1[2] - origin[2]) * (endpoint2[1] - origin[1]);
        values[1] = (endpoint1[2] - origin[2]) * (endpoint2[0] - origin[0])
                - (endpoint1[0] - origin[0]) * (endpoint2[2] - origin[2]);
        values[2] = (endpoint1[0] - origin[0]) * (endpoint2[1] - origin[1])
                - (endpoint1[1] - origin[1]) * (endpoint2[0] - origin[0]);
    }

    /**
     * Calculates the cross product of vec1 X vec2 and stores in result
     * cx = ay * bz - az * by
     * cy = az * bx - ax * bz
     * cz = ax * by - ay * bx
     * 
     * @param vec1
     * @param index1
     * @param vec2
     * @param index2
     * @param result
     * @param rIndex
     * @return The result array
     */
    public static float[] cross(float[] vec1, int index1, float[] vec2, int index2, float[] result, int rIndex) {
        result[rIndex++] = vec1[1 + index1] * vec2[2 + index2] - vec1[2 + index1] * vec2[1 + index2];
        result[rIndex++] = vec1[2 + index1] * vec2[0 + index2] - vec1[0 + index1] * vec2[2 + index2];
        result[rIndex] = vec1[0 + index1] * vec2[1 + index2] - vec1[1 + index1] * vec2[0 + index2];
        return result;
    }

    /**
     * Add the specified Vector to this Vector.
     *
     * @param add The Vector to add.
     */
    public Vec3 add(Vec3 add) {
        values[0] += add.values[0];
        values[1] += add.values[1];
        values[2] += add.values[2];
        return this;
    }

    /**
     * Add the specified values to this Vector.
     * 
     * @param x The x value to add.
     * @param y The y value to add
     * @param z The z value to add
     */
    public void add(float x, float y, float z) {
        values[0] += x;
        values[1] += y;
        values[2] += z;
    }

    /**
     * Adds data from an array to this vector.
     * 
     * @param data
     * @param index
     * @throws IllegalArgumentException If data is null or does not contain index + 3 values.
     */
    public void add(float[] data, int index) {
        if (data == null || data.length < index + 3) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        values[0] += data[index++];
        values[1] += data[index++];
        values[2] += data[index++];
    }

    /**
     * Subtract the specified Vector from this Vector.
     *
     * @param sub The Vector to subtract.
     */
    public void sub(Vec3 sub) {
        values[0] -= sub.values[0];
        values[1] -= sub.values[1];
        values[2] -= sub.values[2];
    }

    /**
     * Subtract the specified values from this Vector.
     * 
     * @param x The x value to subtract.
     * @param y The y value to subtract
     * @param z The z value to subtract
     */
    public void sub(float x, float y, float z) {
        values[0] -= x;
        values[1] -= y;
        values[2] -= z;
    }

    /**
     * Subtract the specified values from this Vector.
     * 
     * @param data The values to subtract
     */
    public void sub(float[] data, int index) {
        values[0] -= data[index++];
        values[1] -= data[index++];
        values[2] -= data[index++];
    }

    /**
     * Multiply this Vector with a scalar.
     * 
     * @param scalar The scalar to multiply with
     */
    public void mult(float scalar) {
        values[0] *= scalar;
        values[1] *= scalar;
        values[2] *= scalar;
    }

    /**
     * Multiply this Vector with another vector.
     * The elements are multiplied
     * element by element.
     * 
     * @param mul Array to multiply vector with, 3 values.
     */
    public void mult(float[] mul) {
        values[0] *= mul[0];
        values[1] *= mul[1];
        values[2] *= mul[2];
    }

    /**
     * Rotate the Vector on the x axis.
     * 
     * @param angle
     * @param destination The destination vector
     */
    public void rotateXAxis(float angle, float[] destination) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float tempY = (values[1] * cos) - (values[2] * sin);
        destination[2] = (values[1] * sin) + (values[2] * cos);
        destination[1] = tempY;
    }

    /**
     * Rotate the Vector on the y axis.
     * 
     * @param angle
     * @param destination The destination vector
     */
    public void rotateYAxis(float angle, float[] destination) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float tempZ = (values[2] * cos) - (values[0] * sin);
        destination[0] = (values[2] * sin) + (values[0] * cos);
        destination[2] = tempZ;
    }

    /**
     * Utility method to rotate a Vector on the z axis, the vector source and destination is a float array.
     * Same as calling rotateZAxis(vector, 0, angle)
     * 
     * @param vector Source and destination array, index 0 and 1 are rotated and written back.
     * @param angle
     */
    public final static void rotateZAxis(float[] vector, float angle) {
        Vec3.rotateZAxis(vector, 0, angle);
    }

    /**
     * Utility method to rotate a Vector on the z axis, the vector source and destination is a float array.
     * 
     * @param vector Source and destination array, x and y are rotated (index and index + 1)
     * @param index Index into vector where values are rotated.
     * @param angle
     */
    public final static void rotateZAxis(float[] vector, int index, float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float tempX = (vector[index] * cos) - (vector[index + 1] * sin);
        vector[index + 1] = (vector[index] * sin) + (vector[index + 1] * cos);
        vector[index] = tempX;
    }

    /**
     * Rotates this Vector on the z axis.
     * 
     * @param angle
     * @param destination The destination vector
     */
    public void rotateZAxis(float angle, float[] destination) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float tempX = (values[0] * cos) - (values[1] * sin);
        destination[1] = (values[0] * sin) + (values[1] * cos);
        destination[0] = tempX;
    }

    /**
     * Adds the contents of vector1 and vector2 and stores in result.
     * 
     * @param vector1
     * @param v1Index
     * @param vector2
     * @param v2Index
     * @param result
     * @param rIndex
     */
    public final static void add(float[] vector1, int v1Index, float[] vector2, int v2Index, float[] result,
            int rIndex) {
        result[rIndex++] = vector1[v1Index++] + vector2[v2Index++];
        result[rIndex++] = vector1[v1Index++] + vector2[v2Index++];
        result[rIndex] = vector1[v1Index] + vector2[v2Index];
    }

    /**
     * Clears the 3 component vector values to 0.
     * 
     * @param vector float array with at least 3 values.
     */
    public final static void clear(float[] vector) {
        vector[0] = 0;
        vector[1] = 0;
        vector[2] = 0;
    }

    /**
     * Multiply the vector with a scalar - store result in vector and return
     * 
     * @param vector
     * @param scalar
     * @return vector[] with vector * scalar
     */
    public final static float[] mul(float[] vector, float scalar) {
        vector[0] = vector[0] * scalar;
        vector[1] = vector[1] * scalar;
        vector[2] = vector[2] * scalar;
        return vector;
    }

    /**
     * Scale the vector by the specified factor.
     * 
     * @param factor
     */
    public void scale(float factor) {
        values[0] = values[0] * factor;
        values[1] = values[1] * factor;
        values[2] = values[2] * factor;
    }

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

    /**
     * Sets the x, y and z
     * 
     * @param destination
     * @param x
     * @param y
     * @param z
     */
    public final static void set(float[] destination, float x, float y, float z) {
        destination[0] = x;
        destination[1] = y;
        destination[2] = z;
    }

    /**
     * Creates a vector from pos1 to pos2 (subtracting pos2 from pos1) and storing the result in resultVec
     * Result is not unit vector (not normalized)
     * 
     * @param pos1 Start pos of vector
     * @param index1
     * @param pos2 End pos of vector
     * @param index2
     * @param resultVec pos2 - pos1
     * @param resultIndex
     */
    public final static void toVector(float[] pos1, int index1, float[] pos2, int index2, float[] resultVec,
            int resultIndex) {
        resultVec[resultIndex++] = pos2[index2++] - pos1[index1++];
        resultVec[resultIndex++] = pos2[index2++] - pos1[index1++];
        resultVec[resultIndex] = pos2[index2] - pos1[index1];
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
     * @param dest Destination array
     * @param destIndex index to result
     */
    public final static void cross3(float[] data, int v1, int v2, float[] dest, int destIndex) {
        dest[destIndex++] = data[v1 + Y] * data[v2 + Z] - data[v1 + Z] * data[v2 + Y];
        dest[destIndex++] = data[v1 + Z] * data[v2 + X] - data[v1 + X] * data[v2 + Z];
        dest[destIndex++] = data[v1 + X] * data[v2 + Y] - data[v1 + Y] * data[v2 + X];
    }

    /**
     * Subtract the vector s from m and store in r.
     * r may be s or m.
     * 
     * @param m
     * @param mIndex
     * @param s
     * @param sIndex
     * @param r The result (m - s) is stored here
     * @param rIndex
     */
    public final static void subtract(float[] m, int mIndex, float[] s, int sIndex, float[] r, int rIndex) {
        r[rIndex++] = m[mIndex++] - s[sIndex++];
        r[rIndex++] = m[mIndex++] - s[sIndex++];
        r[rIndex] = m[mIndex] - s[sIndex];
    }

    /**
     * Multiply the vec by a scalar and store in result
     * 
     * @param vec
     * @param vIndex
     * @param scalar
     * @param result
     * @param rIndex
     */
    public final static void mul(float[] vec, int vIndex, float scalar, float[] result, int rIndex) {
        result[rIndex++] = vec[vIndex++] * scalar;
        result[rIndex++] = vec[vIndex++] * scalar;
        result[rIndex] = vec[vIndex] * scalar;
    }

    /**
     * Copies the source vector to destination.
     * 
     * @param source
     * @param sIndex
     * @param dest
     * @param dIndex
     */
    public final static void copy(float[] source, int sIndex, float[] dest, int dIndex) {
        System.arraycopy(source, sIndex, dest, dIndex, 3);
    }

}
