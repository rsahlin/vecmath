package com.nucleus.vecmath;

/**
 * Definition of axis index values, use this to get index to X,Y or Z axis in a n-dimensional array.
 * 
 * @author Richard Sahlin
 *
 */
public enum Axis {

    /**
     * Index to X axis
     */
    X(0),
    /**
     * Index to Y axis
     */
    Y(1),
    /**
     * Index to Z axis
     */
    Z(2),
    WIDTH(0),
    HEIGHT(1),
    DEPTH(2);

    public final int index;

    private Axis(int index) {
        this.index = index;
    }

}