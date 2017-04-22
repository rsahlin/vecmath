package com.nucleus.vecmath;

import com.google.gson.annotations.SerializedName;

/**
 * Definition of an axis aligned rectangle (quad) using a position plus width and height.
 * This class can be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public class Rectangle {

    public static final String RECT = "rect";
    public static final String VALUES = "values";

    public final static int X = 0;
    public final static int Y = 1;
    public final static int WIDTH = 2;
    public final static int HEIGHT = 3;

    public enum Mode {
        /**
         * Defined using position + size,
         */
        SIZE(4);

        int values;

        Mode(int values) {
            this.values = values;
        }
    }

    @SerializedName(VALUES)
    private float[] values;

    /**
     * Creates a new rectangle with position and size
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Rectangle(float x, float y, float width, float height) {
        values = new float[Mode.SIZE.values];
        values[X] = x;
        values[Y] = y;
        values[WIDTH] = width;
        values[HEIGHT] = height;

    }

    /**
     * Creates a new empty rectangle
     * 
     */
    public Rectangle() {
        values = new float[Mode.SIZE.values];
    }

    public Rectangle(Rectangle source) {
        setValues(source.values);
    }

    public float[] getValues() {
        return values;
    }

    /**
     * Returns the width and height of the specified rectangle index
     * 
     * @return
     */
    public float[] getSize() {
        float[] size = new float[2];
        size[0] = values[WIDTH];
        size[1] = values[HEIGHT];
        return size;
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
