package by.bsuir.bookmaker.dao.pool.impl;

import by.bsuir.bookmaker.dao.pool.IConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectionPool implements IConnectionPool {

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        JDBC_URL = resourceBundle.getString("JDBC_URL");
        DB_USERNAME = resourceBundle.getString("DB_USERNAME");
        DB_PASSWORD = resourceBundle.getString("DB_PASSWORD");
    }

    private static final String JDBC_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;

    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 50;

    private List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();

    private static class ConnectionPoolHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    private ConnectionPool() {
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    private Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found", e);
        }
    }

    public Connection getConnection() {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("Max pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public void closeAllConnections() {
        for (Connection connection : connectionPool) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }
}

