package Server.DAOClasses;

import Server.Model.Game;
import Server.Model.User;
import Server.Requests.JoinRequest;
import chess.ChessGame;
import dataAccess.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameDAO {
    private Map<Integer, Game> game_map = new HashMap<>();

    /**
     * method for inserting a new game into the database.
     */
    public void insert(Game game) throws DataAccessException {

    }

    /**
     * Method for creating a new game with a unique gameID
     */
    public Integer create(String game_name){
        Integer gameID = UUID.randomUUID().hashCode();
        if (gameID < 0){
            gameID = gameID * -1;
        }
        Game game = new Game();
        game.setGameName(game_name);
        game.setBlackUsername("empty");
        game.setWhiteUsername("empty");
        game.setGameID(gameID);
        game_map.put(gameID,game);
        return gameID;
    }

    /**
     * Tries to find the desired game from the database by gameID
     */
    public Game find(Integer gameID) throws DataAccessException{
        if (game_map.containsKey(gameID)){
            return game_map.get(gameID);
        }
        throw new DataAccessException("Error: bad request");
    }


    /**
     * Finds all games that have been created
     */
    public Map<Integer,Game> findAll() throws DataAccessException{
        if (!game_map.isEmpty()) {
            return game_map;
        }
        throw new DataAccessException("There are no games in the DataBase");
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

    public ArrayList<Game> getGameList(){
        ArrayList<Game> gameList = new ArrayList<>();
        for (Integer ID: game_map.keySet()){
            gameList.add(game_map.get(ID));
        }
        return gameList;
    }

    public void observeGame(JoinRequest joinRequest, UserDAO userDAO) throws DataAccessException {

    }
    /**
     * Claims a spot in the game and saves the color of team
     */
    public void claimSpot(String username, Integer gameID, String playerColor) throws DataAccessException {
        Game game = game_map.get(gameID);
        if (playerColor.equals("Black")){
            if (game.getBlackUsername().equals("empty")) {
                game.setBlackUsername(username);
            }else {
                throw new DataAccessException("Error: already taken");
            }
        }else {
            if (game.getWhiteUsername().equals("empty")) {
                game.setWhiteUsername(username);
            }else {
                throw new DataAccessException("Error: already taken");
            }
        }
    }
}
