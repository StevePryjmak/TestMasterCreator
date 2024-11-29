
//login: Test 
//Password: test

import java.sql.*;

public class Database {
    
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private Connection connection;

    public Database(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("Connected to the database successfully.");
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed.");
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    public ResultSet executePreparedQuery(String query, Object... parameters) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
        return preparedStatement.executeQuery();
    }

    public int executePreparedUpdate(String query, Object... parameters) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
        return preparedStatement.executeUpdate();
    }
    
    // Main method for testing
    public static void main(String[] args) {
        String dbUrl = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
        String dbUsername = "Test";
        String dbPassword = "test";

        Database dbExecutor = new Database(dbUrl, dbUsername, dbPassword);
        
        try {
            dbExecutor.connect();

            // Example: SELECT query
            //ResultSet resultSet = dbExecutor.executeQuery("SELECT * FROM users");
            //while (resultSet.next()) {
            //    System.out.println("ID: " + resultSet.getInt(1) + ", Name: " + resultSet.getString(2));
            //}

            // Example: INSERT query
            //int rowsInserted = dbExecutor.executeUpdate("INSERT INTO your_table (name) VALUES ('New Entry')");
            //System.out.println("Rows inserted: " + rowsInserted);

            dbExecutor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
