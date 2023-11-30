package Server.DAOClasses;

import Server.Model.AuthToken;
import dataAccess.DataAccessException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *  A database for storing and retrieving the server’s data for authTokens
 */
public class AuthDAO {


    // Map that contains the information about the auth tokens
    Map<String, AuthToken> auth_map = new HashMap<>();

    public String createToken(String username){
        AuthToken authToken = new AuthToken();
        authToken.setAuthToken(UUID.randomUUID().toString());
        authToken.setUsername(username);
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
        if (auth_map.containsKey(token)){
            return true;
        }else{
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public AuthToken getAuthToken(String token){
        return auth_map.get(token);
    }
    public void deleteToken(String authToken) {
        auth_map.remove(authToken);
    }

    public String findUser(String authToken) throws DataAccessException{
       if (auth_map.containsKey(authToken)){
           return auth_map.get(authToken).getUsername();
       }
       throw new DataAccessException("Error: unauthorized");
    }

    // TODO: Update the table maker
    void configureDatabase() throws SQLException {
        try (Connection conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS userDAO");
            createDbStatement.executeUpdate();

            conn.setCatalog("userDAO");

            var createUserTable = """
            CREATE TABLE  IF NOT EXISTS user (
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )""";


            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }


    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "monkeypie");
    }

    void makeSQLCalls() throws SQLException {
        try (var conn = getConnection()) {
            // Execute SQL statements on the connection here
        }
    }

}
