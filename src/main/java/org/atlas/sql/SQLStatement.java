package org.atlas.sql;

import org.atlas.sql.error.UnsupportedSQLValueException;

import java.util.List;

public class SQLStatement {

    private final String sql;
    private final List<ColumnValue> columnValues;

    public SQLStatement(String sql, List<ColumnValue> columnValues) {
        this.sql = sql;
        this.columnValues = columnValues;
    }

    public String getSqlString() {
        return this.sql;
    }

    public String getLiteralSqlString() {
        var liternalString = this.sql;
        for(var item : columnValues) {
            liternalString = liternalString.replaceFirst("\\?", getSQLValue(item.getValue()));
        }
        return liternalString;
    }

    private String getSQLValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof Integer || value instanceof Double || value instanceof Float) {
            return String.valueOf(value);
        } else {
            return String.valueOf(value);
        }
    }
}
