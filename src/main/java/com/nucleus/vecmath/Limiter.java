package com.nucleus.vecmath;

import com.google.gson.annotations.SerializedName;

/**
 * Specify max and min values for arrays, this can be used to limit for instance transform (scale, rotate, translate)
 * to be within min - max
 * This class can be serialized using GSON
 * 
 * @author Richard Sahlin
 *
 */
public class Limiter {

    @SerializedName("max")
    private float[] max;
    @SerializedName("min")
    private float[] min;

    public Limiter() {

    }

    /**
     * Creates a copy of the source limiter
     * 
     * @param source
     */
    public Limiter(Limiter source) {
        set(source);
    }

    /**
     * Copies the values from the source limit to this class, if source is null nothing is done.
     * Will create min/max arrays as needed
     * 
     * @param source
     */
    public void set(Limiter source) {
        if (source != null) {
            if (max == null || max.length != source.max.length) {
                max = new float[source.max.length];
            }
            if (min == null || min.length != source.min.length) {
                min = new float[source.min.length];
            }
        }
        System.arraycopy(source.max, 0, max, 0, max.length);
        System.arraycopy(source.min, 0, min, 0, min.length);
    }

    /**
     * Returns the max value(s), if the array contains several values they are considered to be using axis offsets.
     * ie X, Y, Z axis at index 0, 1 and 2 respectively.
     * 
     * @return
     */
    public float[] getMax() {
        return max;
    }

    /**
     * Returns the min value(s), if the array contains several values they are considered to be using axis offsets.
     * ie X, Y, Z axis at index 0, 1 and 2 respectively.
     * 
     * @return
     */
    public float[] getMin() {
        return min;
    }

    /**
     * Limits the values in place according to min/max
     * 
     * @param values
     */
    public void limit(float[] values) {
        max(values);
        min(values);
    }

    /**
     * Limits the values to the max limit values.
     * 
     * @param values
     */
    public void max(float[] values) {
        int count = values.length;
        int size = max.length - 1;
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (values[i] > max[index]) {
                values[i] = max[index];
            }
            if (index < size) {
                index++;
            }
        }
    }

    /**
     * Limits the values to the min limit values.
     * 
     * @param values
     */
    public void min(float[] values) {
        int count = values.length;
        int size = max.length - 1;
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (values[i] < min[index]) {
                values[i] = min[index];
            }
            if (index < size) {
                index++;
            }
        }
    }

}
