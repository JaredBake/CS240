package Server.DataAccess;

import Server.Model.Game;
import Server.Model.User;
import chess.ChessGame;
import dataAccess.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *  A database for storing and retrieving the server’s data
 */
public class Access {
    /**
     * creates a user and checks to see if it has access
     */
    // Map that contains the information about the users and auth tokens
    Map<String, String> auth_map = new HashMap<String, String>();
    HashSet<User> users_list = new HashSet<>();
    HashSet<Game> game_list = new HashSet<>();
    void CreateUser(User u) throws DataAccessException {
        users_list.add(u);
        auth_map.put(u.getUsername(), u.getPassword());
    }
    /**
     * method for inserting a new game into the database.
     */
    void Insert(Game g) throws DataAccessException{
        game_list.add(g);
    }
    /**
     * Tries to find the desired game from the database by gameID
     */
    Integer Find(Game g) throws DataAccessException{
        if (game_list.contains(g)){
            return g.getGameID();
        }
        throw new DataAccessException("Could not find Game");
    }
    /**
     * Finds all games that have been created
     */
    HashSet<Game> FindAll() throws DataAccessException{
        if (!game_list.isEmpty()) {
            return game_list;
        }
        throw new DataAccessException("There are no games in the DataBase");
    }
    /**
     * Claims a spot in the game and saves the color of team
     */
    void ClaimSpot(User u, Game g) throws DataAccessException{
        if (!(g.getBlackUsername() == null)){
            g.setBlackUsername(u.getUsername());
        }else if(!g.getBlackUsername().equals(u.getUsername())){
            g.setWhiteUsername(u.getUsername());
        }else{
            throw new DataAccessException("You can't play for both teams dummy");
        }
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
