package Server.Results;

public class CreateResult {
    /**
     * the message returned about the result
     */
    private String message;
    /**
     * the token given to verify login access
     */
    private String authToken;
    /**
     *  Variable to hold the GameID
     */
    private String gameID;

    /**
     * A function that returns the result of the login attempt
     */
    public CreateResult() {}

    /**
     * Getters and Setters for message, authToken, and username properties
     */

    public String getMessage() {
        return message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
