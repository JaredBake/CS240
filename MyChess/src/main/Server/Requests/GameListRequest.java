package Server.Requests;

public class GameListRequest {
    private String authToken;


    /**
     * the request is being sent in using the username and password with this function
     */
    public GameListRequest() {}

    // Getters and Setters for authToken

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authorization) {
        this.authToken = authorization;
    }
}
