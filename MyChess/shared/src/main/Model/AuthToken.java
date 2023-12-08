package Model;
/**
 * Keeps track of the tokens given
 */
public class AuthToken {
    /**
     * authentication token
     */
    String authToken;
    /**
     * unique username
     */
    String username;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
