package Server.DAOClasses;

import Server.Model.Game;
import Server.Model.User;
import dataAccess.DataAccessException;

import java.util.HashMap;
import java.util.Map;

public class GameDAO {
    private Map<Integer, Game> game_map = new HashMap<>();

    /**
     * method for inserting a new game into the database.
     */
    void Insert(Game game) throws DataAccessException {


    }

    /**
     * Tries to find the desired game from the database by gameID
     */
    Game find(Integer gameID) throws DataAccessException{
        if (game_map.containsKey(gameID)){
            return game_map.get(gameID);
        }
        throw new DataAccessException("Could not find Game");
    }


    /**
     * Finds all games that have been created
     */
    Map<Integer,Game> findAll() throws DataAccessException{
        if (!game_map.isEmpty()) {
            return game_map;
        }
        throw new DataAccessException("There are no games in the DataBase");
    }


    /**
     * Claims a spot in the game and saves the color of team
     */
    void claimSpot(User user, Game game) throws DataAccessException{
        if (!(game.getBlackUsername() == null)){
            game.setBlackUsername(user.getUsername());
        }else if(!game.getBlackUsername().equals(user.getUsername())){
            game.setWhiteUsername(user.getUsername());
        }else{
            throw new DataAccessException("You can't play for both teams dummy");
        }
    }


    /**
     * Updates the game in the database replaces the chessGame string
     * with a new one
     */
    void updateGame() throws DataAccessException{}


    /**
     * Deletes a game from the database
     */
    void remove() throws DataAccessException{}

    /**
     * Clears all games from the database
     */
    void clear() throws DataAccessException{}
}
