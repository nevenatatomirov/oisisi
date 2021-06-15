package com.pozoriste.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.function.Function;
import org.h2.jdbcx.JdbcConnectionPool;


public class Database {

    private static JdbcConnectionPool conn_pool;

    static {
        conn_pool = JdbcConnectionPool.create("jdbc:h2:./oisisi;AUTO_SERVER=TRUE", "user", "password");
    }

    private Database(){}

    public static Connection getConnection() throws SQLException {
        return conn_pool.getConnection();
    }

    public static int update(String query) {
        try (Connection conn = conn_pool.getConnection()) {
            int result = conn.createStatement().executeUpdate(query);

            conn.close();

            return result;
        } catch (SQLException e) {
            printSQLException(e);
            return -1;
        }
    }

    public static <T> ArrayList<T> query(String query, Function<ResultSet, T> mapper) {
        try (Connection conn = conn_pool.getConnection()) {
            ResultSet rs  = conn.createStatement().executeQuery(query);

            ArrayList<T> collection = new ArrayList<>();
            while(rs.next()) {
                collection.add(mapper.apply(rs));
            }

            conn.close();

            return collection;
        } catch (SQLException e) {
            printSQLException(e);
            return null;
        }
    }

    private static boolean ignoreSQLException(String sqlState) {
        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;

        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55"))
            return true;

        return false;
    }


    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (!ignoreSQLException(
                        ((SQLException) e).
                                getSQLState())) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException)e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException)e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while(t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }
}
