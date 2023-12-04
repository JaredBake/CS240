package Server.DAOClasses;

import Server.Model.AuthToken;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *  A database for storing and retrieving the server’s data for authTokens
 */
public class AuthDAO {


    // Map that contains the information about the auth tokens
    private Database database = new Database();
    public String createToken(String username) throws SQLException {
        // UPDATED
        Connection conn;
        AuthToken authToken = new AuthToken();
        authToken.setAuthToken(UUID.randomUUID().toString());
        authToken.setUsername(username);
        // Get the connection with mydatabase

        try {
            conn = database.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authtoken, username) VALUES(?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, authToken.getAuthToken());
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            throw new SQLException("Could not create a new authToken");
        }
        return authToken.getAuthToken();
    }

    public Integer listSize() {
//         TODO: UPDATE LAST
        Connection conn = null;
        try {
            conn = database.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        var auth = new ArrayList<AuthToken>();
        try (var preparedStatement = conn.prepareStatement("SELECT username, authToken FROM auth")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var username = rs.getString("name");

                    // Read and deserialize the friend JSON.
                    var json = rs.getString("authToken");
                    AuthToken token = new Gson().fromJson(json, AuthToken.class);

                    AuthToken authToken = new AuthToken();
                    authToken.setUsername(username);
                    authToken.setAuthToken(token.getAuthToken());
                    auth.add(authToken);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return auth.size();
    }

    /**
     * Clears all authTokens from the database
     */
    public void clearAll() {
        // Updated
        Connection conn;
        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }

        try (var preparedStatement = conn.prepareStatement("DELETE FROM auth ")) {
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean verifyToken(String token) throws DataAccessException {
        // UPDATED
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
            } catch (SQLException exception1) {
                throw new RuntimeException(exception1);
            }
        }
        return true;
    }


    public void deleteToken(String token) {
        Connection conn;
        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }

        try (var preparedStatement = conn.prepareStatement("UPDATE auth SET authtoken = null WHERE=?")) {
            preparedStatement.setString(1, token);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


    public String findUser(String authToken) throws DataAccessException{
        // TODO: DOUBLE CHECK THIS ONE
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
        // UPDATED
        try (Connection conn = database.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS mydatabase");
            createDbStatement.executeUpdate();

            var createAuthTable = """
            CREATE TABLE  IF NOT EXISTS auth (
                authtoken VARCHAR(255) NOT NULL,
                username VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )""";


            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

}
