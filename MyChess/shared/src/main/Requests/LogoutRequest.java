package Requests;

public class LogoutRequest {
    private String authToken;
    /**
     * the request is being sent in using the username and password with this function
     */

    public LogoutRequest() {}

    // Getters and Setters for username and password properties


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
