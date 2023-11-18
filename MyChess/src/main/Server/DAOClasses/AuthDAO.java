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
        authToken.setUsername(username);
        auth_map.put(authToken.getAuthToken(),authToken);
        return authToken.getAuthToken();
    }

    /**
     * Clears all authTokens from the database
     */
    public void clearAll() {
        auth_map.clear();
    }

    public boolean verifyToken(String token) throws DataAccessException {
//        AuthToken authToken = new AuthToken();
//        authToken.setAuthToken(token);
        if (auth_map.containsKey(token)){
            return true;
        }else{
            throw new DataAccessException("Error: unauthorized");
        }

    }

    public void deleteToken(String authToken) {
        auth_map.remove(authToken);
    }
}
