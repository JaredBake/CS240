package Server.Requests;

public class LogoutRequest {
    /**
     * unique username for the account
     */
    private String username;
    /**
     * password for the unique username
     */
    private String password;
    /**
     * This String holds the email of the user
     */
    private String email;
    /**
     * the request is being sent in using the username and password with this function
     */

    public LogoutRequest() {}

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

    public String getEmail() {
        return email;
    }
}
