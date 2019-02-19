import org.atlas.sql.JointTableType;
import org.atlas.sql.SQLBuilder;
import org.atlas.sql.SQLExpr;
import org.junit.Test;

import static org.junit.Assert.*;

public class SQLBuilderTest {

    @Test
    public void deleteNoWhereOk() {
        final var validateSql = "DELETE FROM table1";
        var sqlStatement = SQLBuilder.newSql().delete().from("table1").build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void selectNoWhereOk() {
        final var validateSql = "SELECT * FROM table1";
        var sqlStatement = SQLBuilder.newSql().select().from("table1").build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void updateNoWhereOk() {
        final var validateSql = "UPDATE table1 SET column1 = ?, column2 = ?";
        var sqlStatement = SQLBuilder.newSql().update().to("table1").set().column("column1", "value1").column("column2", 3).build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void updateNoWhereLiternalStringOk() {
        final var validateSql = "UPDATE table1 SET column1 = 'value1', column2 = 3";
        var sqlStatement = SQLBuilder.newSql().update().to("table1").set().column("column1", "value1").column("column2", 3).build();
        assertEquals(validateSql, sqlStatement.getLiteralSqlString());
    }


    @Test
    public void deleteWhereOk() {
        final var validateSql = "DELETE FROM table1 WHERE table1.column1 = 'value'";
        var sqlStatement = SQLBuilder.newSql().delete().from("table1").where("table1.column1 = 'value'").build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void deleteWithWhereOk() {
        final var validateSql = "DELETE FROM table1 WHERE a = b";
        var sqlStatement = SQLBuilder.newSql().delete().from("table1").where("a = b").build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void deleteWithWhereConditionsOk() {
        final var validateSql = "DELETE FROM table1 WHERE a = b";
        var sqlStatement = SQLBuilder.newSql().delete().from("table1").where().condition("a = b").build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void deleteWithWhereComplexConditionsOk() {
        final var validateSql = "DELETE FROM table1 WHERE a = b AND c = d OR f = g AND ( l = m OR h = i )";
        var sqlStatement = SQLBuilder.newSql().delete().from("table1")
                .where().condition("a = b")
                .and()
                .condition("c = d")
                .or()
                .condition("f = g")
                .and()
                .aggCondition(new SQLExpr("l = m").or().condition("h = i"))
                .build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }

    @Test
    public void selectJoinTablesOk() {
        final var validateSql = "SELECT t1.c1, t2.c2 FROM table1 t1 " +
                "JOIN table2 t2 on t1.id = t2.id " +
                "WHERE a = b AND c = d OR f = g AND ( l = m OR h = i )";
        var sqlStatement = SQLBuilder.newSql().select("t1.c1, t2.c2").from("table1 t1")
                .join(JointTableType.JOIN, "table2 t2").on("t1.id = t2.id")
                .where().condition("a = b")
                .and()
                .condition("c = d")
                .or()
                .condition("f = g")
                .and()
                .aggCondition(new SQLExpr("l = m").or().condition("h = i"))
                .build();
        assertEquals(validateSql, sqlStatement.getSqlString());
    }
}