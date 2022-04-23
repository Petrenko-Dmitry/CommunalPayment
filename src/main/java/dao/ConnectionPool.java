package dao;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final PGConnectionPoolDataSource connectionPool;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connectionPool = new PGConnectionPoolDataSource();
        connectionPool.setServerNames(new String[] {"localhost"});
        connectionPool.setPortNumbers(new int[] {5432});
        connectionPool.setUser("postgres");
        connectionPool.setPassword("12345");
        connectionPool.setDatabaseName("postgres");
    }

    public static Connection createConnection() {
        try {
            return connectionPool.getPooledConnection().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
