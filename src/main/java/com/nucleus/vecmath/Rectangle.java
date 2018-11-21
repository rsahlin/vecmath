package com.nucleus.vecmath;

/**
 * Definition of an axis aligned rectangle (quad) using a position plus width and height.
 * This class can be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public class Rectangle extends Shape {

    public static final String RECT = "rect";
    /**
     * Number of values for a rectangle x,y + width, height
     */
    private static final int ELEMENTS = 4;

    public final static int INDEX_X = 0;
    public final static int INDEX_Y = 1;
    public final static int INDEX_WIDTH = 2;
    public final static int INDEX_HEIGHT = 3;

    /**
     * Creates a new rectangle with position and size
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Rectangle(float x, float y, float width, float height) {
        createValues();
        values[INDEX_X] = x;
        values[INDEX_Y] = y;
        values[INDEX_WIDTH] = width;
        values[INDEX_HEIGHT] = height;

    }

    public Rectangle(Rectangle source) {
        type = Type.rect;
        setValues(source.values);
    }

    @Override
    public int getElementCount() {
        return ELEMENTS;
    }

    /**
     * Returns the width and height of the specified rectangle index
     * 
     * @return
     */
    public float[] getSize() {
        float[] size = new float[2];
        size[0] = values[INDEX_WIDTH];
        size[1] = values[INDEX_HEIGHT];
        return size;
    }

    public float getWidth() {
        return values[INDEX_WIDTH];
    }

    public float getHeight() {
        return values[INDEX_HEIGHT];
    }

    public float getX() {
        return values[INDEX_X];
    }

    public float getY() {
        return values[INDEX_Y];
    }

    /**
     * Scales the rectangle x,y,widht and height.
     * 
     * @param scale Scale factor
     */
    public void scale(float scale) {
        values[INDEX_X] *= scale;
        values[INDEX_Y] *= scale;
        values[INDEX_WIDTH] *= scale;
        values[INDEX_HEIGHT] *= scale;
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

    @Override
    public String toString() {
        return "X:" + values[INDEX_X] + " y:" + values[INDEX_Y] + " width:" + values[INDEX_WIDTH] + " height:"
                + values[INDEX_HEIGHT];
    }

}
