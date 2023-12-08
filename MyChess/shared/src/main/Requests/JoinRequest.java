package Requests;

public class JoinRequest {
    /**
     * unique username for the account
     */
    private String username;
    /**
     * password for the unique username
     */
    private String password;
    // Holds the authToken
    private String authToken;
    // Holds the requested player color for the game WHITE/BLACK
    private String playerColor;
    // Holds the gameID
    private Integer gameID;
    /**
     * the request is being sent in using the username and password with this function
     */
    public JoinRequest() {}

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

    public void setAuthToken(String authorization) {
        authToken = authorization;
    }

    public String getPlayerColor() {
        return playerColor;
    }
    public Integer getGameID(){
        return gameID;
    }

    public String getAuthToken() {
        return authToken;
    }
    public void setGameID(Integer gameID){
        this.gameID = gameID;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
}
