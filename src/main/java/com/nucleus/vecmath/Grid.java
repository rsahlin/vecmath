package com.nucleus.vecmath;

/**
 * Definition of an axis aligned grid (quad) using a position, width/height and number of segments in x and y
 * This class can be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public class Grid extends Rectangle {

    public final static int INDEX_X_SEGMENTS = 4;
    public final static int INDEX_Y_SEGMENTS = 5;

    /**
     * Number of values for a rectangle x,y + width, height
     */
    private static final int ELEMENTS = 6;

    public Grid(float x, float y, float width, float height, int xSegments, int ySegments) {
        super(x, y, width, height);
    }

    @Override
    public int getElementCount() {
        return ELEMENTS;
    }

    /**
     * Returns the number of segments in X
     * 
     * @return
     */
    public int getXSegments() {
        return (int) values[INDEX_X_SEGMENTS];
    }

    /**
     * Returns the number of segments in Y
     * 
     * @return
     */
    public int getYSegments() {
        return (int) values[INDEX_Y_SEGMENTS];
    }

}
