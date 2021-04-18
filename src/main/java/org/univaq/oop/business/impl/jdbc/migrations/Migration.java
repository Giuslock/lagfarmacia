package org.univaq.oop.business.impl.jdbc.migrations;

import org.univaq.oop.business.impl.jdbc.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class Migration {

    protected final String table;

    public Migration(String table) {
        this.table = table;
    }

    public void up() {
        try(Connection connection = DatabaseConnection.getConnection()) {
            String tableColumns = getStringFromTableColumns(tableColumns());
            try(PreparedStatement statement = upStatement(connection, tableColumns)) {

                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void down() {
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("drop table if exists " + table);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private PreparedStatement upStatement(Connection connection, String tableColumns) throws SQLException {
        String createStatementQuery = "create table if not exists " + table + "(" + tableColumns +  ");";
        PreparedStatement statement = connection.prepareStatement(createStatementQuery);
        return statement;
    }

    protected abstract List<String> tableColumns();

    private String getStringFromTableColumns(List<String> tableColumns) {
        return String.join(",", tableColumns);
    }

}
