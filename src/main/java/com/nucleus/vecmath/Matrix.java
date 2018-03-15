package com.nucleus.vecmath;

/**
 * This class is NOT thread safe since it uses static temp float arrays
 * 4 x 4 matrix laid out contiguously in memory, translation component is at the 13th, 14th, and 15th element,
 * ie column major. See OpenGL Specification or Android implementation of Matrix.java
 * Use this for classes that can represent the data as a matrix, for instance a scale or translation
 * 
 * Matrix math utilities. These methods operate on OpenGL ES format
 * matrices and vectors stored in float arrays.
 *
 * Matrices are 4 x 4 column-vector matrices stored in column-major
 * order:
 * 
 * <pre>
 *  m[offset +  0] m[offset +  4] m[offset +  8] m[offset + 12]
 *  m[offset +  1] m[offset +  5] m[offset +  9] m[offset + 13]
 *  m[offset +  2] m[offset +  6] m[offset + 10] m[offset + 14]
 *  m[offset +  3] m[offset +  7] m[offset + 11] m[offset + 15]
 * </pre>
 *
 * Vectors are 4 row x 1 column column-vectors stored in order:
 * 
 * <pre>
 * v[offset + 0]
 * v[offset + 1]
 * v[offset + 2]
 * v[offset + 3]
 * </pre>
 *
 * s *
 * 
 * @author Richard Sahlin
 *
 */
public abstract class Matrix extends VecMath {

    public final static int TRANSLATE_X = 12;
    public final static int TRANSLATE_Y = 13;
    public final static int TRANSLATE_Z = 14;
    public final static int TRANSLATE_W = 15;

    /**
     * Number of elements (values) in a matrix
     */
    public final static int MATRIX_ELEMENTS = 16;

    /**
     * Identity matrix to be used to read from
     * DO NOT WRITE TO THIS MATRIX
     */
    public final static float[] IDENTITY_MATRIX = Matrix.setIdentity(Matrix.createMatrix(), 0);

    /**
     * Used to store the transform
     */
    transient protected float[] matrix = Matrix.createMatrix();

    private static float[] temp = new float[16];
    private static float[] result = new float[16];

    /**
     * Returns a matrix with the values contained in the implementing class, ie the rotate, translate and scale values.
     * 
     * @return Matrix with the data contained in the implementing class
     */
    public abstract float[] getMatrix();

    /**
     * Creates a new, empty, matrix
     * 
     * @return
     */
    public final static float[] createMatrix() {
        return new float[MATRIX_ELEMENTS];
    }

    /**
     * Sets the matrix to identity.
     * 
     * @param matrix The matrix
     * @offset Offset into array where matrix values are stored.
     * @return The matrix, this is the same as passed into this method
     */
    public final static float[] setIdentity(float[] matrix, int offset) {
        matrix[offset++] = 1f;
        matrix[offset++] = 0f;
        matrix[offset++] = 0f;
        matrix[offset++] = 0f;

        matrix[offset++] = 0f;
        matrix[offset++] = 1f;
        matrix[offset++] = 0f;
        matrix[offset++] = 0f;

        matrix[offset++] = 0f;
        matrix[offset++] = 0f;
        matrix[offset++] = 1f;
        matrix[offset++] = 0f;

        matrix[offset++] = 0f;
        matrix[offset++] = 0f;
        matrix[offset++] = 0f;
        matrix[offset++] = 1f;
        return matrix;
    }

    /**
     * Scales the matrix
     * 
     * @param m
     * @param mOffset
     * @param x
     * @param y
     * @param z
     */
    public static void scaleM(float[] matrix, int offset, float[] scale) {
        for (int i = 0; i < 4; i++) {
            int index = offset + i;
            matrix[index] *= scale[X];
            matrix[index + 4] *= scale[Y];
            matrix[index + 8] *= scale[Z];
        }
    }

    /**
     * Multiply a 3 element vector with a matrix, the resultvector will be transformed using the matrix.
     * 
     * @param matrix The transform matrix
     * @param vec The vector to transform.
     * @param resultVec The output vector, this is where the result is stored. May not be the same as vec
     */
    public final static void transformVec3(float[] matrix, float[] vec, float[] resultVec) {
        resultVec[0] = matrix[0] * vec[0] + matrix[4] * vec[1] + matrix[8] * vec[2] + matrix[12];
        resultVec[1] = matrix[1] * vec[0] + matrix[5] * vec[1] + matrix[9] * vec[2] + matrix[13];
        resultVec[2] = matrix[2] * vec[0] + matrix[6] * vec[1] + matrix[10] * vec[2] + matrix[14];
    }

    /**
     * Multiply a number of 2 element vector with a matrix, the resultvectors will be transformed using the matrix
     * and stored sequentially
     * 
     * @param matrix
     * @param offset Offset in matrix array where matrix starts
     * @param vec
     * @param resultVec The output vector, this may not be the same as vec
     * @param count Number of vectors to transform
     */
    public final static void transformVec2(float[] matrix, int offset, float[] vec, float[] resultVec, int count) {
        int output = 0;
        int input = 0;
        for (int i = 0; i < count; i++) {
            resultVec[output++] = matrix[offset] * vec[input] + matrix[offset + 1] * vec[input + 1]
                    + matrix[offset + 3];
            resultVec[output++] = matrix[offset + 4] * vec[input] + matrix[offset + 5] * vec[input + 1]
                    + matrix[offset + 7];
            input += 2;
        }
    }

    /**
     * Transposes a 4 x 4 matrix.
     *
     * @param mTrans the array that holds the output inverted matrix
     * @param mTransOffset an offset into mInv where the inverted matrix is
     * stored.
     * @param m the input array
     * @param mOffset an offset into m where the matrix is stored.
     */
    public static void transposeM(float[] mTrans, int mTransOffset, float[] m,
            int mOffset) {
        for (int i = 0; i < 4; i++) {
            int mBase = i * 4 + mOffset;
            mTrans[i + mTransOffset] = m[mBase];
            mTrans[i + 4 + mTransOffset] = m[mBase + 1];
            mTrans[i + 8 + mTransOffset] = m[mBase + 2];
            mTrans[i + 12 + mTransOffset] = m[mBase + 3];
        }
    }

    /**
     * Concatenate Matrix m1 with Matrix m2 and store the result in destination matrix.
     * 
     * @param m1
     * @param m2
     * @param destination
     */
    public final static void mul4(float[] m1, float[] m2, float[] destination) {
        // Concatenate matrix 1 with matrix 2, 4*4
        destination[0] = (m1[0] * m2[0] + m1[4] * m2[1] + m1[8] * m2[2] + m1[12] * m2[3]);
        destination[4] = (m1[0] * m2[4] + m1[4] * m2[5] + m1[8] * m2[6] + m1[12] * m2[7]);
        destination[8] = (m1[0] * m2[8] + m1[4] * m2[9] + m1[8] * m2[10] + m1[12] * m2[11]);
        destination[12] = (m1[0] * m2[12] + m1[4] * m2[13] + m1[8] * m2[14] + m1[12] * m2[15]);

        destination[1] = (m1[1] * m2[0] + m1[5] * m2[1] + m1[9] * m2[2] + m1[13] * m2[3]);
        destination[5] = (m1[1] * m2[4] + m1[5] * m2[5] + m1[9] * m2[6] + m1[13] * m2[7]);
        destination[9] = (m1[1] * m2[8] + m1[5] * m2[9] + m1[9] * m2[10] + m1[13] * m2[11]);
        destination[13] = (m1[1] * m2[12] + m1[5] * m2[13] + m1[9] * m2[14] + m1[13] * m2[15]);

        destination[2] = (m1[2] * m2[0] + m1[6] * m2[1] + m1[10] * m2[2] + m1[14] * m2[3]);
        destination[6] = (m1[2] * m2[4] + m1[6] * m2[5] + m1[10] * m2[6] + m1[14] * m2[7]);
        destination[10] = (m1[2] * m2[8] + m1[6] * m2[9] + m1[10] * m2[10] + m1[14] * m2[11]);
        destination[14] = (m1[2] * m2[12] + m1[6] * m2[13] + m1[10] * m2[14] + m1[14] * m2[15]);

        destination[3] = (m1[3] * m2[0] + m1[7] * m2[1] + m1[11] * m2[2] + m1[15] * m2[3]);
        destination[7] = (m1[3] * m2[4] + m1[7] * m2[5] + m1[11] * m2[6] + m1[15] * m2[7]);
        destination[11] = (m1[3] * m2[8] + m1[7] * m2[9] + m1[11] * m2[10] + m1[15] * m2[11]);
        destination[15] = (m1[3] * m2[12] + m1[7] * m2[13] + m1[11] * m2[14] + m1[15] * m2[15]);

    }

    /**
     * Concatenate matrix with matrix2 and store the result in matrix
     * 
     * @param matrix Source 1 matrix - matrix1 * matrix2 stored here
     * @param matrix2 Source 2 matrix
     */
    public static void mul4(float[] matrix, float[] matrix2) {
        float temp_float1, temp_float2, temp_float3, temp_float4;

        // Concatenate this with matrix 2, 4*4
        temp_float1 = (matrix[0] * matrix2[0] + matrix[1] * matrix2[4] + matrix[2] * matrix2[8] +
                matrix[3] * matrix2[12]);
        temp_float2 = (matrix[0] * matrix2[1] + matrix[1] * matrix2[5] + matrix[2] * matrix2[9] +
                matrix[3] * matrix2[13]);
        temp_float3 = (matrix[0] * matrix2[2] + matrix[1] * matrix2[6] + matrix[2] * matrix2[10] +
                matrix[3] * matrix2[14]);
        temp_float4 = (matrix[0] * matrix2[3] + matrix[1] * matrix2[7] + matrix[2] * matrix2[11] +
                matrix[3] * matrix2[15]);
        matrix[0] = temp_float1;
        matrix[4] = temp_float2;
        matrix[8] = temp_float3;
        matrix[12] = temp_float4;

        temp_float1 = (matrix[4] * matrix2[0] + matrix[5] * matrix2[4] + matrix[6] * matrix2[8] +
                matrix[7] * matrix2[12]);
        temp_float2 = (matrix[4] * matrix2[1] + matrix[5] * matrix2[5] + matrix[6] * matrix2[9] +
                matrix[7] * matrix2[13]);
        temp_float3 = (matrix[4] * matrix2[2] + matrix[5] * matrix2[6] + matrix[6] * matrix2[10] +
                matrix[7] * matrix2[14]);
        temp_float4 = (matrix[4] * matrix2[3] + matrix[5] * matrix2[7] + matrix[6] * matrix2[11] +
                matrix[7] * matrix2[15]);
        matrix[1] = temp_float1;
        matrix[5] = temp_float2;
        matrix[9] = temp_float3;
        matrix[13] = temp_float4;

        temp_float1 = (matrix[8] * matrix2[0] + matrix[9] * matrix2[4] + matrix[10] * matrix2[8] +
                matrix[11] * matrix2[12]);
        temp_float2 = (matrix[8] * matrix2[1] + matrix[9] * matrix2[5] + matrix[10] * matrix2[9] +
                matrix[11] * matrix2[13]);
        temp_float3 = (matrix[8] * matrix2[2] + matrix[9] * matrix2[6] + matrix[10] * matrix2[10] +
                matrix[11] * matrix2[14]);
        temp_float4 = (matrix[8] * matrix2[3] + matrix[9] * matrix2[7] + matrix[10] * matrix2[11] +
                matrix[11] * matrix2[15]);
        matrix[2] = temp_float1;
        matrix[6] = temp_float2;
        matrix[10] = temp_float3;
        matrix[14] = temp_float4;

        temp_float1 = (matrix[12] * matrix2[0] + matrix[13] * matrix2[4] + matrix[14] * matrix2[8] +
                matrix[15] * matrix2[12]);
        temp_float2 = (matrix[12] * matrix2[1] + matrix[13] * matrix2[5] + matrix[14] * matrix2[9] +
                matrix[15] * matrix2[13]);
        temp_float3 = (matrix[12] * matrix2[2] + matrix[13] * matrix2[6] + matrix[14] * matrix2[10]
                + matrix[15] * matrix2[14]);
        temp_float4 = (matrix[12] * matrix2[3] + matrix[13] * matrix2[7] + matrix[14] * matrix2[11]
                + matrix[15] * matrix2[15]);
        matrix[3] = temp_float1;
        matrix[7] = temp_float2;
        matrix[11] = temp_float3;
        matrix[15] = temp_float4;

    }

    /**
     * Inverts a 4 x 4 matrix.
     *
     * @param mInv the array that holds the output inverted matrix
     * @param mInvOffset an offset into mInv where the inverted matrix is
     * stored.
     * @param m the input array
     * @param mOffset an offset into m where the matrix is stored.
     * @return true if the matrix could be inverted, false if it could not.
     */
    public static boolean invertM(float[] mInv, int mInvOffset, float[] m,
            int mOffset) {
        // Invert a 4 x 4 matrix using Cramer's Rule

        // array of transpose source matrix
        float[] src = new float[16];

        // transpose matrix
        transposeM(src, 0, m, mOffset);

        // temp array for pairs
        float[] tmp = new float[12];

        // calculate pairs for first 8 elements (cofactors)
        tmp[0] = src[10] * src[15];
        tmp[1] = src[11] * src[14];
        tmp[2] = src[9] * src[15];
        tmp[3] = src[11] * src[13];
        tmp[4] = src[9] * src[14];
        tmp[5] = src[10] * src[13];
        tmp[6] = src[8] * src[15];
        tmp[7] = src[11] * src[12];
        tmp[8] = src[8] * src[14];
        tmp[9] = src[10] * src[12];
        tmp[10] = src[8] * src[13];
        tmp[11] = src[9] * src[12];

        // Holds the destination matrix while we're building it up.
        float[] dst = new float[16];

        // calculate first 8 elements (cofactors)
        dst[0] = tmp[0] * src[5] + tmp[3] * src[6] + tmp[4] * src[7];
        dst[0] -= tmp[1] * src[5] + tmp[2] * src[6] + tmp[5] * src[7];
        dst[1] = tmp[1] * src[4] + tmp[6] * src[6] + tmp[9] * src[7];
        dst[1] -= tmp[0] * src[4] + tmp[7] * src[6] + tmp[8] * src[7];
        dst[2] = tmp[2] * src[4] + tmp[7] * src[5] + tmp[10] * src[7];
        dst[2] -= tmp[3] * src[4] + tmp[6] * src[5] + tmp[11] * src[7];
        dst[3] = tmp[5] * src[4] + tmp[8] * src[5] + tmp[11] * src[6];
        dst[3] -= tmp[4] * src[4] + tmp[9] * src[5] + tmp[10] * src[6];
        dst[4] = tmp[1] * src[1] + tmp[2] * src[2] + tmp[5] * src[3];
        dst[4] -= tmp[0] * src[1] + tmp[3] * src[2] + tmp[4] * src[3];
        dst[5] = tmp[0] * src[0] + tmp[7] * src[2] + tmp[8] * src[3];
        dst[5] -= tmp[1] * src[0] + tmp[6] * src[2] + tmp[9] * src[3];
        dst[6] = tmp[3] * src[0] + tmp[6] * src[1] + tmp[11] * src[3];
        dst[6] -= tmp[2] * src[0] + tmp[7] * src[1] + tmp[10] * src[3];
        dst[7] = tmp[4] * src[0] + tmp[9] * src[1] + tmp[10] * src[2];
        dst[7] -= tmp[5] * src[0] + tmp[8] * src[1] + tmp[11] * src[2];

        // calculate pairs for second 8 elements (cofactors)
        tmp[0] = src[2] * src[7];
        tmp[1] = src[3] * src[6];
        tmp[2] = src[1] * src[7];
        tmp[3] = src[3] * src[5];
        tmp[4] = src[1] * src[6];
        tmp[5] = src[2] * src[5];
        tmp[6] = src[0] * src[7];
        tmp[7] = src[3] * src[4];
        tmp[8] = src[0] * src[6];
        tmp[9] = src[2] * src[4];
        tmp[10] = src[0] * src[5];
        tmp[11] = src[1] * src[4];

        // calculate second 8 elements (cofactors)
        dst[8] = tmp[0] * src[13] + tmp[3] * src[14] + tmp[4] * src[15];
        dst[8] -= tmp[1] * src[13] + tmp[2] * src[14] + tmp[5] * src[15];
        dst[9] = tmp[1] * src[12] + tmp[6] * src[14] + tmp[9] * src[15];
        dst[9] -= tmp[0] * src[12] + tmp[7] * src[14] + tmp[8] * src[15];
        dst[10] = tmp[2] * src[12] + tmp[7] * src[13] + tmp[10] * src[15];
        dst[10] -= tmp[3] * src[12] + tmp[6] * src[13] + tmp[11] * src[15];
        dst[11] = tmp[5] * src[12] + tmp[8] * src[13] + tmp[11] * src[14];
        dst[11] -= tmp[4] * src[12] + tmp[9] * src[13] + tmp[10] * src[14];
        dst[12] = tmp[2] * src[10] + tmp[5] * src[11] + tmp[1] * src[9];
        dst[12] -= tmp[4] * src[11] + tmp[0] * src[9] + tmp[3] * src[10];
        dst[13] = tmp[8] * src[11] + tmp[0] * src[8] + tmp[7] * src[10];
        dst[13] -= tmp[6] * src[10] + tmp[9] * src[11] + tmp[1] * src[8];
        dst[14] = tmp[6] * src[9] + tmp[11] * src[11] + tmp[3] * src[8];
        dst[14] -= tmp[10] * src[11] + tmp[2] * src[8] + tmp[7] * src[9];
        dst[15] = tmp[10] * src[10] + tmp[4] * src[8] + tmp[9] * src[9];
        dst[15] -= tmp[8] * src[9] + tmp[11] * src[10] + tmp[5] * src[8];

        // calculate determinant
        float det = src[0] * dst[0] + src[1] * dst[1] + src[2] * dst[2] + src[3]
                * dst[3];

        if (det == 0.0f) {

        }

        // calculate matrix inverse
        det = 1 / det;
        for (int j = 0; j < 16; j++)
            mInv[j + mInvOffset] = dst[j] * det;

        return true;
    }

    /**
     * Computes an orthographic projection matrix.
     * X axis increasing to the right
     * Y axis increasing upwards
     * Z axis increasing into the screen
     *
     * @param m returns the result
     * @param mOffset
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */
    public static void orthoM(float[] m, int mOffset,
            float left, float right, float bottom, float top,
            float near, float far) {
        if (left == right) {
            throw new IllegalArgumentException("left == right");
        }
        if (bottom == top) {
            throw new IllegalArgumentException("bottom == top");
        }
        if (near == far) {
            throw new IllegalArgumentException("near == far");
        }

        final float r_width = 1.0f / (right - left);
        final float r_height = 1.0f / (top - bottom);
        final float r_depth = 1.0f / (far - near);
        final float x = 2.0f * (r_width);
        final float y = 2.0f * (r_height);
        final float z = 2.0f * (r_depth);
        final float tx = -(right + left) * r_width;
        final float ty = -(top + bottom) * r_height;
        final float tz = -(far + near) * r_depth;
        m[mOffset + 0] = x;
        m[mOffset + 5] = y;
        m[mOffset + 10] = z;
        m[mOffset + 12] = tx;
        m[mOffset + 13] = ty;
        m[mOffset + 14] = tz;
        m[mOffset + 15] = 1.0f;
        m[mOffset + 1] = 0.0f;
        m[mOffset + 2] = 0.0f;
        m[mOffset + 3] = 0.0f;
        m[mOffset + 4] = 0.0f;
        m[mOffset + 6] = 0.0f;
        m[mOffset + 7] = 0.0f;
        m[mOffset + 8] = 0.0f;
        m[mOffset + 9] = 0.0f;
        m[mOffset + 11] = 0.0f;
    }

    /**
     * Define a projection matrix in terms of six clip planes
     * X axis increasing to the right
     * Y axis increasing upwards
     * Z axis increasing into the screen
     * 
     * @param m the float array that holds the perspective matrix
     * @param offset the offset into float array m where the perspective
     * matrix data is written
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */

    public static void frustumM(float[] m, int offset,
            float left, float right, float bottom, float top,
            float near, float far) {
        if (left == right) {
            throw new IllegalArgumentException("left == right");
        }
        if (top == bottom) {
            throw new IllegalArgumentException("top == bottom");
        }
        if (near == far) {
            throw new IllegalArgumentException("near == far");
        }
        if (near <= 0.0f) {
            throw new IllegalArgumentException("near <= 0.0f");
        }
        if (far <= 0.0f) {
            throw new IllegalArgumentException("far <= 0.0f");
        }
        final float r_width = 1.0f / (right - left);
        final float r_height = 1.0f / (top - bottom);
        final float r_depth = 1.0f / (near - far);
        final float x = 2.0f * (near * r_width);
        final float y = 2.0f * (near * r_height);
        final float A = 2.0f * ((right + left) * r_width);
        final float B = (top + bottom) * r_height;
        final float C = -(far + near) * r_depth;
        final float D = 2.0f * (far * near * r_depth);
        m[offset + 0] = x;
        m[offset + 5] = y;
        m[offset + 8] = A;
        m[offset + 9] = B;
        m[offset + 10] = C;
        m[offset + 14] = D;
        m[offset + 11] = 1.0f;
        m[offset + 1] = 0.0f;
        m[offset + 2] = 0.0f;
        m[offset + 3] = 0.0f;
        m[offset + 4] = 0.0f;
        m[offset + 6] = 0.0f;
        m[offset + 7] = 0.0f;
        m[offset + 12] = 0.0f;
        m[offset + 13] = 0.0f;
        m[offset + 15] = 0.0f;
    }

    /**
     * Computes the length of a vector
     *
     * @param x x coordinate of a vector
     * @param y y coordinate of a vector
     * @param z z coordinate of a vector
     * @return the length of a vector
     */
    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Sets matrix m to the identity matrix.
     * 
     * @param sm returns the result
     * @param smOffset index into sm where the result matrix starts
     */
    public static void setIdentityM(float[] sm, int smOffset) {
        for (int i = 0; i < 16; i++) {
            sm[smOffset + i] = 0;
        }
        for (int i = 0; i < 16; i += 5) {
            sm[smOffset + i] = 1.0f;
        }
    }

    /**
     * Scales matrix m by x, y, and z, putting the result in sm
     * 
     * @param sm returns the result
     * @param smOffset index into sm where the resandroid.opengl.matrixult matrix starts
     * @param m source matrix
     * @param mOffset index into m where the source matrix starts
     * @param x scale factor x
     * @param y scale factor y
     * @param z scale factor z
     */
    public static void scaleM(float[] sm, int smOffset,
            float[] m, int mOffset,
            float x, float y, float z) {
        for (int i = 0; i < 4; i++) {
            int smi = smOffset + i;
            int mi = mOffset + i;
            sm[smi] = m[mi] * x;
            sm[4 + smi] = m[4 + mi] * y;
            sm[8 + smi] = m[8 + mi] * z;
            sm[12 + smi] = m[12 + mi];
        }
    }

    /**
     * Scales matrix m in place by sx, sy, and sz
     * 
     * @param m matrix to scale
     * @param mOffset index into m where the matrix starts
     * @param x scale factor x
     * @param y scale factor y
     * @param z scale factor z
     */
    public static void scaleM(float[] m, int mOffset,
            float x, float y, float z) {
        for (int i = 0; i < 4; i++) {
            int mi = mOffset + i;
            m[mi] *= x;
            m[4 + mi] *= y;
            m[8 + mi] *= z;
        }
    }

    /**
     * Translates matrix m by x, y, and z, putting the result in tm
     * 
     * @param tm returns the result
     * @param tmOffset index into sm where the result matrix starts
     * @param m source matrix
     * @param mOffset index into m where the source matrix starts
     * @param x translation factor x
     * @param y translation factor y
     * @param z translation factor z
     */
    public static void translateM(float[] tm, int tmOffset,
            float[] m, int mOffset,
            float x, float y, float z) {
        for (int i = 0; i < 12; i++) {
            tm[tmOffset + i] = m[mOffset + i];
        }
        for (int i = 0; i < 4; i++) {
            int tmi = tmOffset + i;
            int mi = mOffset + i;
            tm[12 + tmi] = m[mi] * x + m[4 + mi] * y + m[8 + mi] * z +
                    m[12 + mi];
        }
    }

    /**
     * Translates matrix m by x, y, and z in place.
     * 
     * @param m matrix
     * @param mOffset index into m where the matrix starts
     * @param x translation factor x
     * @param y translation factor y
     * @param z translation factor z
     */
    public static void translateM(
            float[] m, int mOffset,
            float x, float y, float z) {
        for (int i = 0; i < 4; i++) {
            int mi = mOffset + i;
            m[3 + mi] += m[mi] * x + m[1 + mi] * y + m[2 + mi] * z;
        }
    }

    /**
     * Translate the matrix, OpenGL row wise, along x,y and z axis
     * 
     * @param matrix
     * @param x
     * @param y
     * @param z
     */
    public final static void translate(float[] matrix, float x, float y, float z) {
        matrix[3] = x;
        matrix[7] = y;
        matrix[11] = z;
    }

    /**
     * Translate the matrix, OpenGL row wise, along x,y and z axis
     * 
     * @param matrix
     * @param translate
     */
    public final static void translate(float[] matrix, float[] translate) {
        matrix[3] = translate[0];
        matrix[7] = translate[1];
        matrix[11] = translate[2];
    }

    /**
     * Rotates matrix m by angle a (in degrees) around the axis (x, y, z)
     * 
     * @param rm returns the result
     * @param m source matrix
     * @param a angle to rotate in degrees
     * @param x scale factor x
     * @param y scale factor y
     * @param z scale factor z
     */
    public static void rotateM(float[] rm,
            float[] m, float a, float x, float y, float z) {
        setRotateM(temp, 0, a, x, y, z);
        mul4(m, temp, rm);
    }

    /**
     * Rotates matrix m in place by angle a (in degrees)
     * around the axis (x, y, z)
     * 
     * @param m source matrix
     * @param mOffset index into m where the matrix starts
     * @param a angle to rotate in degrees
     * @param x scale factor x
     * @param y scale factor y
     * @param z scale factor z
     */
    public static void rotateM(float[] m, int mOffset,
            float a, float x, float y, float z) {
        setRotateM(temp, 0, a, x, y, z);
        mul4(m, temp, result);
        System.arraycopy(result, 0, m, mOffset, 16);
    }

    public static void rotateM(float[] m, AxisAngle axisAngle) {
        if (axisAngle != null) {
            // TODO - should a check be made for 0 values in X,Y,Z axis which results in NaN?
            float[] values = axisAngle.axisAngle;
            setRotateM(temp, 0, values[AxisAngle.ANGLE], values[AxisAngle.X], values[AxisAngle.Y], values[AxisAngle.Z]);
            mul4(m, temp, result);
            System.arraycopy(result, 0, m, 0, 16);
        }
    }

    /**
     * Rotates matrix m by angle a (in degrees) around the axis (x, y, z)
     * 
     * @param rm returns the result
     * @param rmOffset index into rm where the result matrix starts
     * @param a angle to rotate in radians
     * @param x scale factor x
     * @param y scale factor y
     * @param z scale factor z
     */
    public static void setRotateM(float[] rm, int rmOffset,
            float a, float x, float y, float z) {
        rm[rmOffset + 3] = 0;
        rm[rmOffset + 7] = 0;
        rm[rmOffset + 11] = 0;
        rm[rmOffset + 12] = 0;
        rm[rmOffset + 13] = 0;
        rm[rmOffset + 14] = 0;
        rm[rmOffset + 15] = 1;
        float s = (float) Math.sin(a);
        float c = (float) Math.cos(a);
        if (1.0f == x && 0.0f == y && 0.0f == z) {
            rm[rmOffset + 5] = c;
            rm[rmOffset + 10] = c;
            rm[rmOffset + 6] = s;
            rm[rmOffset + 9] = -s;
            rm[rmOffset + 1] = 0;
            rm[rmOffset + 2] = 0;
            rm[rmOffset + 4] = 0;
            rm[rmOffset + 8] = 0;
            rm[rmOffset + 0] = 1;
        } else if (0.0f == x && 1.0f == y && 0.0f == z) {
            rm[rmOffset + 0] = c;
            rm[rmOffset + 10] = c;
            rm[rmOffset + 8] = s;
            rm[rmOffset + 2] = -s;
            rm[rmOffset + 1] = 0;
            rm[rmOffset + 4] = 0;
            rm[rmOffset + 6] = 0;
            rm[rmOffset + 9] = 0;
            rm[rmOffset + 5] = 1;
        } else if (0.0f == x && 0.0f == y && 1.0f == z) {
            rm[rmOffset + 0] = c;
            rm[rmOffset + 5] = c;
            rm[rmOffset + 1] = s;
            rm[rmOffset + 4] = -s;
            rm[rmOffset + 2] = 0;
            rm[rmOffset + 6] = 0;
            rm[rmOffset + 8] = 0;
            rm[rmOffset + 9] = 0;
            rm[rmOffset + 10] = 1;
        } else {
            float len = length(x, y, z);
            if (1.0f != len) {
                float recipLen = 1.0f / len;
                x *= recipLen;
                y *= recipLen;
                z *= recipLen;
            }
            float nc = 1.0f - c;
            float xy = x * y;
            float yz = y * z;
            float zx = z * x;
            float xs = x * s;
            float ys = y * s;
            float zs = z * s;
            rm[rmOffset + 0] = x * x * nc + c;
            rm[rmOffset + 4] = xy * nc - zs;
            rm[rmOffset + 8] = zx * nc + ys;
            rm[rmOffset + 1] = xy * nc + zs;
            rm[rmOffset + 5] = y * y * nc + c;
            rm[rmOffset + 9] = yz * nc - xs;
            rm[rmOffset + 2] = zx * nc - ys;
            rm[rmOffset + 6] = yz * nc + xs;
            rm[rmOffset + 10] = z * z * nc + c;
        }
    }

    /**
     * Define a viewing transformation in terms of an eye point, a center of
     * view, and an up vector.
     *
     * @param rm returns the result
     * @param rmOffset index into rm where the result matrix starts
     * @param eyeX eye point X
     * @param eyeY eye point Y
     * @param eyeZ eye point Z
     * @param centerX center of view X
     * @param centerY center of view Y
     * @param centerZ center of view Z
     * @param upX up vector X
     * @param upY up vector Y
     * @param upZ up vector Z
     */
    public static void setLookAtM(float[] rm, int rmOffset,
            float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ, float upX, float upY,
            float upZ) {

        // See the OpenGL GLUT documentation for gluLookAt for a description
        // of the algorithm. We implement it in a straightforward way:

        float fx = centerX - eyeX;
        float fy = centerY - eyeY;
        float fz = centerZ - eyeZ;

        // Normalize f
        float rlf = 1.0f / Matrix.length(fx, fy, fz);
        fx *= rlf;
        fy *= rlf;
        fz *= rlf;

        // compute s = f x up (x means "cross product")
        float sx = fy * upZ - fz * upY;
        float sy = fz * upX - fx * upZ;
        float sz = fx * upY - fy * upX;

        // and normalize s
        float rls = 1.0f / Matrix.length(sx, sy, sz);
        sx *= rls;
        sy *= rls;
        sz *= rls;

        // compute u = s x f
        float ux = sy * fz - sz * fy;
        float uy = sz * fx - sx * fz;
        float uz = sx * fy - sy * fx;

        rm[rmOffset + 0] = sx;
        rm[rmOffset + 1] = ux;
        rm[rmOffset + 2] = -fx;
        rm[rmOffset + 3] = 0.0f;

        rm[rmOffset + 4] = sy;
        rm[rmOffset + 5] = uy;
        rm[rmOffset + 6] = -fy;
        rm[rmOffset + 7] = 0.0f;

        rm[rmOffset + 8] = sz;
        rm[rmOffset + 9] = uz;
        rm[rmOffset + 10] = -fz;
        rm[rmOffset + 11] = 0.0f;

        rm[rmOffset + 12] = 0.0f;
        rm[rmOffset + 13] = 0.0f;
        rm[rmOffset + 14] = 0.0f;
        rm[rmOffset + 15] = 1.0f;

        translateM(rm, rmOffset, -eyeX, -eyeY, -eyeZ);
    }

    /**
     * Set a row in the matrix from the specified source, beginning at offset.
     * This will set value row, row + 4, row + 8, row + 12, ie not consecutive memory addresses.
     *
     * @param matrix
     * @param row
     * @param source
     * @param offset
     */
    public final static void setRow(float[] matrix, int row, float[] source, int offset) {
        matrix[row] = source[offset++];
        matrix[row + 4] = source[offset++];
        matrix[row + 8] = source[offset++];
        matrix[row + 12] = source[offset++];
    }

}
