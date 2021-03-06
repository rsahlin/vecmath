package com.nucleus.vecmath;

import com.google.gson.annotations.SerializedName;

/**
 * Holds 3D transform information, data is for x,y and z axis.
 * This class may be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public final class Transform extends Matrix {

    public static final String TRANSFORM = "transform";
    public static final String AXIS_ANGLE = "axisAngle";
    public static final String SCALE = "scale";
    public static final String TRANSLATE = "translate";
    public static final String SCALE_LIMIT = "scaleLimit";

    @SerializedName(AXIS_ANGLE)
    AxisAngle axisAngle;

    @SerializedName(SCALE)
    float[] scale;

    @SerializedName(TRANSLATE)
    float[] translate;

    @SerializedName(SCALE_LIMIT)
    private Limiter scaleLimit;

    transient protected boolean matrixMode = false;

    /**
     * Default constructor
     */
    public Transform() {
        super();
        Matrix.setIdentity(matrix, 0);
    }

    /**
     * Creates a new instance from the specified transform.
     * All values in the source transform will be copied into this class
     * 
     * @param source
     */
    public Transform(Transform source) {
        Matrix.setIdentity(matrix, 0);
        set(source);
    }

    /**
     * Copies all transform values from the source into this class.
     * 
     * @param source
     */
    public void set(Transform source) {
        if (source.scale != null) {
            scale = new float[3];
            setScale(source.scale);
        }
        if (source.translate != null) {
            translate = new float[3];
            setTranslate(source.translate);
        }
        if (source.axisAngle != null) {
            axisAngle = new AxisAngle();
            axisAngle.setValues(source.getAxisAngle().getValues());
        }
        if (source.scaleLimit != null) {
            this.scaleLimit = new Limiter(source.scaleLimit);
        }
    }

    /**
     * Returns the translation for this transform.
     * The returned array will be a reference to the translate in this class.
     * 
     * @return Array with x,y and z axis translation
     */
    public float[] getTranslate() {
        return translate;
    }

    /**
     * Copies the translate values into this transform
     * 
     * @param translate Array with at least 3 values
     */
    public void setTranslate(float[] translate) {
        this.translate[X] = translate[X];
        this.translate[Y] = translate[Y];
        this.translate[Z] = translate[Z];
    }

    /**
     * Adds x,y and z to the current translation
     * 
     * @param add The values to add to the current translation
     */
    public void translate(float[] add) {
        translate[X] += add[X];
        translate[Y] += add[Y];
        translate[Z] += add[Z];
    }

    /**
     * Adds the specified translation in x, y and z
     * 
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        translate[X] += x;
        translate[Y] += y;
        translate[Z] += z;
    }

    /**
     * Returns the z,y, z axis scale
     * The returned array will be a reference to the scale in this class.
     * 
     * @return Array with x,y and z axis scale.
     */
    public float[] getScale() {
        return scale;
    }

    /**
     * Reads the scale into result array.
     * 
     * @param result
     */
    public void getScale(float[] result) {
        result[0] = scale[0];
        result[1] = scale[1];
        result[2] = scale[2];
    }

    /**
     * Checks if the limiter is set (not null), if so the values are checked to be in limit
     * 
     * @param values
     */
    private void limit(float[] values, Limiter limiter) {
        if (limiter != null) {
            limiter.limit(this.scale);
        }
    }

    /**
     * Sets the x,y and z axis scale
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setScale(float x, float y, float z) {
        scale[X] = x;
        scale[Y] = y;
        scale[Z] = z;
        limit(scale, scaleLimit);
    }

    /**
     * Multiplies the scalar factor with the scale values and adds to the current scale,
     * this can be used to uniformly modify the scale values.
     * Positive values will increase scale, negative values will decrease scale.
     * 
     * @param factor Scalefactor
     */
    public void scale(float factor) {
        scale[X] += factor * scale[X];
        scale[Y] += factor * scale[Y];
        scale[Z] += factor * scale[Z];
        limit(scale, scaleLimit);
    }

    /**
     * Sets the scale values in this transform from the specified array.
     * If the scale limit values are set then scale values are checked to be in range.
     * 
     * @param scale Array with at least 3 values
     */
    public void setScale(float[] scale) {
        this.scale[X] = scale[X];
        this.scale[Y] = scale[Y];
        this.scale[Z] = scale[Z];
        limit(scale, scaleLimit);
    }

    /**
     * Returns the axis angle.
     * The returned object will be a reference to the axisangle in this class
     * 
     * @return The axis angle for this transform.
     */
    public AxisAngle getAxisAngle() {
        return axisAngle;
    }

    /**
     * Sets the transform to the matrix, values are copied to the matrix in this class.
     * 
     * @param matrix Matrix to set to this class
     */
    public void setMatrix(float[] matrix) {
        System.arraycopy(matrix, 0, this.matrix, 0, Matrix.MATRIX_ELEMENTS);
    }

    /**
     * Enables or disables matrix mode, if matrixMode is enabled then the {@link #updateMatrix()} method will return the
     * existing matrix.
     * 
     * @param matrixMode True to enable matrix mode, false to disable.
     */
    public void setMatrixMode(boolean matrixMode) {
        this.matrixMode = matrixMode;
    }

    /**
     * Returns a reference to the matrix in this class.
     * 
     * @return
     */
    public float[] getMatrix() {
        return matrix;
    }

    /**
     * If matrix mode is disabled the matrix is updated with transform using rotate, scale and translate values.
     * 
     * @return If matrix mode is enabled, returns the updated matrix with transform values, otherwise the matrix is
     * returned.
     */
    public float[] updateMatrix() {
        if (!matrixMode && (axisAngle != null || scale != null || translate != null)) {
            Matrix.setIdentity(matrix, 0);
            Matrix.rotateM(matrix, axisAngle);
            Matrix.scaleM(matrix, 0, scale);
            Matrix.translate(matrix, translate);
        }
        return matrix;

    }

    /**
     * Returns true if this transform uses matrix mode, ie transform is not specified by rotate, scale and translate -
     * instread the matrix is used.
     * 
     * @return True if this transform uses a matrix.
     */
    public boolean isMatrixMode() {
        return matrixMode;
    }

}
