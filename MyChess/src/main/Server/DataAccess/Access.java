package Server.DataAccess;

import Server.Model.Game;
import Server.Model.User;
import chess.ChessGame;
import dataAccess.DataAccessException;

import java.util.HashMap;
import java.util.Map;

/**
 *  A database for storing and retrieving the server’s data
 */
public class Access {
    /**
     * creates a user and checks to see if it has access
     */
    // Map that contains the information about the users and auth tokens
    Map<String, String> Data = new HashMap<String, String>()
    {
        {
            put("DuroBake", "114766");
            put("FastTrack", "soccer23");
        }
    };

    void CreateUser(User u) throws DataAccessException {

    }
    /**
     * method for inserting a new game into the database.
     */
    void Insert(Game g) throws DataAccessException{

    }
    /**
     * Tries to find the desired game from the database by gameID
     */
    void Find(User u) throws DataAccessException{
    }
    /**
     * Finds all games that have been created
     */
    void FindAll() throws DataAccessException{

    }
    /**
     * Claims a spot in the game and saves the color of team
     */
    void ClaimSpot(User u) throws DataAccessException{
    }
    /**
     * Updates the game in the database replaces the chessGame string
     * with a new one
     */
    void UpdateGame() throws DataAccessException{}
    /**
     * Deletes a game from the database
     */
    void Remove() throws DataAccessException{}
    /**
     * Clears all data from the database
     */
    void Clear() throws DataAccessException{}

}
