package Server.Requests;

/**
 * class to attempt logging in
 */
public class LoginRequest {
    /**
     * unique username for the account
     */
    private String username;
    /**
     * password for the unique username
     */
    private String password;
    /**
     * the request is being sent in using the username and password with this function
     */
    public LoginRequest() {}

    // Getters and Setters for username and password properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
