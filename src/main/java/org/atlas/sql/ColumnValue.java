package org.atlas.sql;

public class ColumnValue {
    private final String column;
    private final Object value;

    public ColumnValue(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }
}
