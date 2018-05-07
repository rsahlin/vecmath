package com.nucleus.vecmath;

import com.google.gson.annotations.SerializedName;

/**
 * Definition of basic shape that can be serialized
 * This is used for import/export
 *
 */
public abstract class Shape {

    public static final String SHAPE = "shape";
    public static final String TYPE = "type";
    public static final String VALUES = "values";

    public enum Type {
        /**
         * Two dimensional rectangle
         */
        rect(Rectangle.class);

        public final Class<?> typeClass;

        private Type(Class<?> typeClass) {
            this.typeClass = typeClass;
        }
    }

    @SerializedName(TYPE)
    protected Type type;

    @SerializedName(VALUES)
    protected float[] values;

    /**
     * Returns the number of values that define the shape
     * 
     * @return
     */
    public abstract int getElementCount();

    /**
     * Creates a new instance of the specified Shape, generic version of {@link #createInstance(Rectangle)}
     * 
     * @param source
     * @return
     */
    public static Shape createInstance(Shape source) {
        switch (source.type) {
            case rect:
                return createInstance((Rectangle) source);
            default:
                throw new IllegalArgumentException("Not implemented for " + source.type);
        }
    }

    /**
     * Creates a copy of the source rectangle
     * 
     * @param source
     * @return
     */
    public static Rectangle createInstance(Rectangle source) {
        return new Rectangle(source);
    }

    /**
     * Call this method to create array to store values - used when creating runtime instance.
     * Will call {@link #getElementCount()} to create array of correct size.
     */
    protected void createValues() {
        values = new float[getElementCount()];
    }

    /**
     * Returns the array where values can be read by indexing values based on subclass type
     * 
     * @return Array with values defining the shape
     */
    public float[] getValues() {
        return values;
    }

    /**
     * Returns the shape type
     * 
     * @return
     */
    public Type getType() {
        return type;
    }

}
