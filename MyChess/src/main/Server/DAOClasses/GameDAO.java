package Server.DAOClasses;

import Server.Model.Game;
import Server.Model.User;
import chess.ChessGame;
import dataAccess.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameDAO {
    private Map<String, Game> game_map = new HashMap<>();

    /**
     * method for inserting a new game into the database.
     */
    public void insert(Game game) throws DataAccessException {

    }

    /**
     * Method for creating a new game with a unique gameID
     */
    public String create(String game_name){
        String gameID = UUID.randomUUID().toString();
        Game game = new Game();
        game.setGameName(game_name);
        game.setBlackUsername("Empty");
        game.setWhiteUsername("Empty");
        game_map.put(gameID,game);
        return gameID;
    }

    /**
     * Tries to find the desired game from the database by gameID
     */
    public Game find(String gameID) throws DataAccessException{
        if (game_map.containsKey(gameID)){
            return game_map.get(gameID);
        }
        throw new DataAccessException("Could not find Game");
    }


    /**
     * Finds all games that have been created
     */
    public Map<String,Game> findAll() throws DataAccessException{
        if (!game_map.isEmpty()) {
            return game_map;
        }
        throw new DataAccessException("There are no games in the DataBase");
    }


    /**
     * Claims a spot in the game and saves the color of team
     */
    public void claimSpot(User user, Game game) throws DataAccessException{
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
    public void updateGame() throws DataAccessException{}


    /**
     * Deletes a game from the database
     */
    public void remove() throws DataAccessException{}

    /**
     * Clears all games from the database
     */
    public void clearAll() {
        game_map.clear();
    }

    public ArrayList<Game> getGameList() {
        ArrayList<Game> gameList = new ArrayList<>();
        for (String ID: game_map.keySet()){
            gameList.add(game_map.get(ID));
        }
        return gameList;
    }
}
