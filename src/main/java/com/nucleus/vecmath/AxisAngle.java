package com.nucleus.vecmath;

/**
 * Axis angle representation of x,y and z axis rotation.
 * This is based on the Collada way of representing rotations.
 * 
 * @author Richard Sahlin
 *
 */
public final class AxisAngle extends VecMath {

    /**
     * Index to the angle, angle value is stored in degrees (same as Collada)
     */
    public final static int ANGLE = 4;

    final float[] axisAngle = new float[4];

    /**
     * Returns a reference to the array containing axis/angle values.
     * Index X,Y and Z is used for x axis and the ANGLE index contains the angle value.
     * 
     * @return Array with x,y,z axis + angle.
     */
    public float[] getAxisAngle() {
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
    public void setAxisAngle(float x, float y, float z, float angle) {
        axisAngle[X] = x;
        axisAngle[Y] = y;
        axisAngle[Z] = z;
        axisAngle[ANGLE] = angle;
    }

}
