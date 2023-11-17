package Server.DAOClasses;

import Server.Model.AuthToken;
import dataAccess.DataAccessException;

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
        auth_map.put(username,authToken);
        return authToken.getAuthToken();
    }

    /**
     * Clears all authTokens from the database
     */
    public void clearAll() {
        auth_map.clear();
    }
}
