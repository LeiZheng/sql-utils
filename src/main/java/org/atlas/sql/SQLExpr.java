package org.atlas.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLExpr {
    public static final String COLUMNS_VALUES_PLACEHOLDER = "COLUMNS_VALUES_PLACEHOLDER]";
    private List<ColumnValue> columnValues = new ArrayList<>();
    private StringBuilder sb = new StringBuilder();

    public SQLExpr(String expr) {
        this.expr(expr);
    }

    public SQLExpr() {
    }

    public SQLExpr from(String table) {
        return expr("FROM", table);
    }

    public SQLExpr delete() {
        return expr("DELETE");
    }

    public SQLStatement build() {
        var compositedSql = this.sb.toString().trim();
        if (this.columnValues.size() > 0) {
            compositedSql = compositedSql.replaceAll(COLUMNS_VALUES_PLACEHOLDER, String.join(", ", columnValues.stream().map((colVal) -> colVal.getColumn()
                    + " = ?").collect(Collectors.toList())));
        }
        return new SQLStatement(compositedSql, columnValues);
    }

    public SQLExpr select() {
        return this.select("*");
    }

    public SQLExpr select(String columns) {
        return this.expr("SELECT", columns);
    }

    public SQLExpr expr(String... exprs) {
        this.sb.append(String.join(" ", exprs));
        this.sb.append(" ");
        return this;
    }

    public SQLExpr update() {
        return this.expr("UPDATE");
    }

    public SQLExpr to(String table) {
        return this.expr(table);
    }

    public SQLExpr where(String cond) {
        return this.expr("WHERE", cond);
    }

    public SQLExpr set() {
        return this.expr("SET", SQLExpr.COLUMNS_VALUES_PLACEHOLDER);
    }

    public SQLExpr column(String column, String value) {
        this.columnValues.add(new ColumnValue(column, value));
        return this;
    }

    public SQLExpr column(String column, int value) {
        this.columnValues.add(new ColumnValue(column, value));
        return this;
    }

    public SQLExpr where() {
        return this.expr("WHERE");
    }

    public SQLExpr condition(String cond) {
        return this.expr(cond);
    }

    public SQLExpr and() {
        return this.expr("AND");
    }

    public SQLExpr or() {
        return this.expr("OR");
    }

    public SQLExpr aggCondition(SQLExpr condition) {
        return this.expr("(", condition.build().getSqlString(), ")");
    }

    public SQLExpr join(JointTableType joinType, String table) {
        return this.expr(joinType.toString(), table);
    }

    public SQLExpr on(String joint) {
        return this.expr("on", joint);
    }
}
