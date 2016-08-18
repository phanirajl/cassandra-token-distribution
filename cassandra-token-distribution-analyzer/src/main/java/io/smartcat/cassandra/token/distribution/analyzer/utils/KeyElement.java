package io.smartcat.cassandra.token.distribution.analyzer.utils;

import com.datastax.driver.core.DataType;

/**
 * Represents a column that is part of a key in Cassandra table.
 */
public class KeyElement {

    /**
     * Name of the column.
     */
    private String name;

    /**
     * Type of the column.
     */
    private DataType datatype;

    /**
     * Value of the column.
     */
    private Object value;

    public KeyElement(String name, DataType datatype, Object value) {
        super();
        this.name = name;
        this.datatype = datatype;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDatatype() {
        return datatype;
    }

    public void setDatatype(DataType datatype) {
        this.datatype = datatype;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
