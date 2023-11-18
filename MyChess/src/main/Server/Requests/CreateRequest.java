package Server.Requests;

public class CreateRequest {
    /**
     * unique username for the account
     */
    private String game_name;
    /**
     * authToken for the unique username
     */
    private String authToken;
    public CreateRequest() {}

    // Getters and Setters for username and password properties
    public String getGameName() {
        return game_name;
    }

    public void setGameName(String username) {
        this.game_name = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
