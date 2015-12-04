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
    final AxisAngle axisAngle = new AxisAngle();

    @SerializedName("scale")
    final float[] scale = new float[] { 1, 1, 1 };

    @SerializedName("translate")
    final float[] translate = new float[3];

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
     * Returns the z,y, z axis scale
     * The returned array will be a reference to the scale in this class.
     * 
     * @return Array with x,y and z axis scale.
     */
    public float[] getScale() {
        return scale;
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
        Matrix.translate(matrix, translate);
        Matrix.scaleM(matrix, 0, scale);
        return matrix;
    }

}
