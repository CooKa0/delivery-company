package com.solvd.laba.delivery.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionPool;
    private final int poolSize;

    private ConnectionPool(int poolSize) {
        this.poolSize = poolSize;
        connectionPool = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(createConnection());
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool(50);
        }
        return instance;
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/delivery-company", "root", "Servertest");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating connection", e);
            throw new RuntimeException("Error creating connection", e);
        }
    }

    public Connection getConnection() throws InterruptedException {
        Connection connection = connectionPool.take();
        if (!isValid(connection)) {
            connection = createConnection();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (isValid(connection)) {
            connectionPool.offer(connection);
        } else {

            connectionPool.offer(createConnection());
        }
    }

    private boolean isValid(Connection connection) {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
