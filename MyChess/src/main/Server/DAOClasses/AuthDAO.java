package Server.DAOClasses;

import Server.Model.AuthToken;
import dataAccess.DataAccessException;
import dataAccess.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *  A database for storing and retrieving the server’s data for authTokens
 */
public class AuthDAO {


    // Map that contains the information about the auth tokens
    Map<String, AuthToken> auth_map = new HashMap<>();
    private Database database = new Database();
    public String createToken(String username) throws SQLException {

        Connection conn;
        AuthToken authToken = new AuthToken();
        authToken.setAuthToken(UUID.randomUUID().toString());
        authToken.setUsername(username);
        // Get the connection with mydatabase
        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }
        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (username, authtoken) VALUES(?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, String.valueOf(authToken));
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            throw new SQLException("Could not create a new authToken");
        }

        auth_map.put(authToken.getAuthToken(),authToken);
        return authToken.getAuthToken();
    }

    public Integer listSize(){
        return auth_map.keySet().size();
    }

    /**
     * Clears all authTokens from the database
     */
    public void clearAll() {
        auth_map.clear();
    }

    public boolean verifyToken(String token) throws DataAccessException {

        Connection conn = database.getConnection();
        String authtoken = "";
        try (var preparedStatement = conn.prepareStatement("SELECT authtoken FROM auth ")) {
            preparedStatement.setString(1, token);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    authtoken = rs.getString("authtoken");

                    System.out.printf("authToken: %s", authtoken);
                }
            }
        } catch (SQLException exception) {
            try {
                throw new SQLException("Error: unathorized");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
//            throw new DataAccessException("Error: unauthorized");

    }
    public void deleteToken(String token) {
        auth_map.remove(token);
    }

    public String findUser(String authToken) throws DataAccessException{
        Connection conn = database.getConnection();
        String username = "";
        try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE authtoken=?")) {
            preparedStatement.setString(1, authToken);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    username = rs.getString("username");

                    System.out.printf("authToken: %s", username);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return username;
//        throw new DataAccessException("Error: unauthorized");
    }

    void configureDatabase() throws SQLException {
        try (Connection conn = database.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS mydatabase");
            createDbStatement.executeUpdate();

//            conn.setCatalog("mydatabase");

            var createAuthTable = """
            CREATE TABLE  IF NOT EXISTS auth (
                authtoken VARCHAR(255) NOT NULL,
                username VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )""";


            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
