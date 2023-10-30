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
}
