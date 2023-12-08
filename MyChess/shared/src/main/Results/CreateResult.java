package Results;

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
    private Integer gameID;

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

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
