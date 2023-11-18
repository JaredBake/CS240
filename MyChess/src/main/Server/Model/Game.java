package Server.Model;

import chess.ChessGame;

/**
 * Gets information about
 */
public class Game {

    /**
     * unique game identifier
     */
    String gameID;
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
        gameID = null;
        whiteUsername = null;
        blackUsername = null;
        gameName = null;
        game = null;
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
