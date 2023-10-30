package Server.Results;

/**
 * Class for returning the result of the attempted login
 */
public class LoginResult {
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
    public LoginResult() {}

    /**
     * Getters and Setters for message, authToken, and username properties
     */
    public String getToken(){
        return null;
    }

}
