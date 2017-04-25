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

    @SerializedName("axisAngle")
    AxisAngle axisAngle;

    @SerializedName("scale")
    float[] scale = new float[] { 1, 1, 1 };

    @SerializedName("translate")
    float[] translate = new float[3];

    @SerializedName("scaleLimit")
    private Limiter scaleLimit;

    /**
     * Default constructor
     */
    public Transform() {
        super();
    }

    /**
     * Creates a new instance from the specified transform.
     * All values in the source transform will be copied into this class
     * 
     * @param source
     */
    public Transform(Transform source) {
        set(source);
    }

    /**
     * Copies all transform values from the source into this class.
     * 
     * @param source
     */
    public void set(Transform source) {
        setScale(source.getScale());
        setTranslate(source.getTranslate());
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
    public void addTranslation(float[] add) {
        translate[X] += add[X];
        translate[Y] += add[Y];
        translate[Z] += add[Z];
    }

    /**
     * Adds the specified translation in x and y
     * @param x
     * @param y
     */
    public void translate(float x, float y) {
        translate[X] += x;
        translate[Y] += y;
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

    @Override
    public float[] getMatrix() {
        Matrix.setIdentity(matrix, 0);
        Matrix.rotateM(matrix, axisAngle);
        Matrix.translate(matrix, translate);
        Matrix.scaleM(matrix, 0, scale);
        return matrix;
    }

}
