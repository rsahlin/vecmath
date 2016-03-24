package com.nucleus.vecmath;

import com.google.gson.annotations.SerializedName;

/**
 * Definition if a rectangle (quad)
 * The rectangle can be defined in 2 ways,
 * one with position + size, eg x, y, width, height
 * the other with each corner defined, eg x1,y1,x2,y2 etc.
 * This class can be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public class Rectangle {

    public final static int X = 0;
    public final static int Y = 1;
    public final static int WIDTH = 2;
    public final static int HEIGHT = 3;

    public enum Mode {
        /**
         * Defined using position + size,
         */
        SIZE(),
        /**
         * Defined by each corner, x1,y1,x2,y2,x3,y3,x4,y4
         */
        CORNER();
    }

    @SerializedName("mode")
    private Mode mode;
    @SerializedName("values")
    private float[] values;

    public Rectangle(Rectangle source) {
        this.mode = source.mode;
        setValues(source.values);
    }

    public Mode getMode() {
        return mode;
    }

    public float[] getValues() {
        return values;
    }

    /**
     * Returns the width and height of the specified rectangle index
     * 
     * @param index
     * @return
     */
    public float[] getSize(int index) {
        float[] size = new float[2];
        switch (mode) {
        case SIZE:
            size[0] = values[index * 4 + WIDTH];
            size[1] = values[index * 4 + HEIGHT];
            return size;
        default:
            throw new IllegalArgumentException("Not implemented: " + mode);
        }
    }

    /**
     * Copies the values from the source into this class, creating the value array if it does not exist.
     * 
     * @param values
     */
    public void setValues(float[] values) {
        if (this.values == null) {
            this.values = new float[values.length];
        }
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

}
