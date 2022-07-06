package com.github.fainaaa.DBHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBHandler implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(DBHandler.class);
    private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/com/github/fainaaa/database/PetDB.db";
    private Connection connection;

    public DBHandler() {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            logger.fatal("DB DRIVER CLASS NOT FOUND" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public ResultSet executeQueryStatement(String sqlQuery){
        ResultSet resultSet;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        }
        catch (SQLException e) {
            logger.error("SQLException in executeQueryStatement()");
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public void executeUpdateStatement(String sqlQuery){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("PRAGMA foreign_keys = ON");
            statement.executeUpdate(sqlQuery);
        }
        catch (SQLException e) {
            logger.error("SQLException in executeUpdateStatement()");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void close() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            logger.fatal("CONNECTION CLOSING: FAILED");
            e.printStackTrace();
        }
    }
}
