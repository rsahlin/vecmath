package com.nucleus.vecmath;

import com.google.gson.annotations.SerializedName;

/**
 * Axis angle representation of x,y and z axis rotation.
 * This is based on the Collada way of representing rotations.
 * This class may be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public final class AxisAngle extends VecMath {

    public static final String VALUES = "values";
    /**
     * Index to the angle, angle value is stored in degrees (same as Collada)
     */
    public final static int ANGLE = 3;

    @SerializedName(VALUES)
    final float[] axisAngle = new float[4];

    /**
     * Default constructor
     */
    public AxisAngle() {
        super();
    }

    /**
     * Creates a copy of the specified axis angle, all values are copied.
     * 
     * @param source
     */
    public AxisAngle(AxisAngle source) {
        setValues(source.getValues());
    }

    /**
     * Returns a reference to the array containing axis/angle values.
     * Index X,Y and Z is used for x axis and the ANGLE index contains the angle value.
     * 
     * @return Array with x,y,z axis + angle.
     */
    public float[] getValues() {
        return axisAngle;
    }

    /**
     * Sets the axis-angle values.
     * 
     * @param x X axis component.
     * @param y Y axis component.
     * @param z Z axis component.
     * @param angle Angle value, in degrees
     */
    public void setValues(float x, float y, float z, float angle) {
        axisAngle[X] = x;
        axisAngle[Y] = y;
        axisAngle[Z] = z;
        axisAngle[ANGLE] = angle;
    }

    /**
     * Sets the x,y,z and angle values
     * 
     * @param values Array with X,Y,Z and ANGLE values
     */
    public void setValues(float[] values) {
        axisAngle[X] = values[X];
        axisAngle[Y] = values[Y];
        axisAngle[Z] = values[Z];
        axisAngle[ANGLE] = values[ANGLE];
        if (axisAngle[X] == 0 && axisAngle[Y] == 0 && axisAngle[Z] == 0) {
        	throw new IllegalArgumentException("Invalid axis:" + axisAngle[X] + ", " + axisAngle[Y] + ", " + axisAngle[Z]);
        }

    }

}
