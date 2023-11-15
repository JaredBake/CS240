package Server.Model;

import chess.ChessGame;

/**
 * Gets information about
 */
public class Game {

    /**
     * unique game identifier
     */
    int gameID;
    /**
     * unique username of white team
     */
    String whiteUsername;
    /**
     * unique username of black team
     */
    String blackUsername;
    /**
     * name of the game we are playing
     */
    String gameName;
    /**
     * Current game
     */
    ChessGame game;

    public Game(){
        gameID = 0;
        whiteUsername = null;
        blackUsername = null;
        gameName = null;
        game = null;

    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
