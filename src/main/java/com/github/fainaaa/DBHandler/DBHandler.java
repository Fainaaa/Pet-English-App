package com.github.fainaaa.DBHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBHandler implements AutoCloseable {
    Logger logger = LogManager.getRootLogger();
    private static String nameConnection = "org.sqlite.JDBC";
    private Connection connection;

    public DBHandler() {

        try {
            Class.forName(nameConnection);
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/github/fainaaa/database/PetDB.db");
        } catch (ClassNotFoundException | SQLException e) {
            logger.fatal("КЛАСС ДРАЙВЕРА К БД НЕ НАЙДЕН\n" + e.getMessage());
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
            logger.error("SQLException в executeQueryStatement()");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void executeUpdateStatement(String sqlQuery){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            logger.error("SQLException в executeUpdateStatement()");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.fatal("НЕ УДАЛОСЬ ЗАКРЫТЬ ПОДКЛЮЧЕНИЕ К БД");
            e.printStackTrace();
        }
    }

}
