package Requests;

public class CreateRequest {
    /**
     * unique username for the account
     */
    private String gameName;
    /**
     * authToken for the unique username
     */
    private String authToken;
    public CreateRequest() {}

    // Getters and Setters for username and password properties
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String username) {
        this.gameName = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
