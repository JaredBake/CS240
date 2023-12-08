package Results;

public class LogoutResult {
    /**
     * the message returned about the result
     */
    private String message;
    /**
     * the token given to verify login access
     */
    private String authToken;
    /**
     * unique username returned with result
     */
    private String username;
    /**
     * A function that returns the result of the login attempt
     */
    public LogoutResult() {}

    /**
     * Getters and Setters for message, authToken, and username properties
     */
    public String getToken(){
        return null;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
