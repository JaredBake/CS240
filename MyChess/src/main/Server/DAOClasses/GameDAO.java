package Server.DAOClasses;

import Server.Model.Game;
import Server.Model.User;
import Server.Requests.JoinRequest;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameDAO {

    private Database database = new Database();
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
        // TODO: UPDATE
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
        // TODO: UPDATE
        game_map.clear();
    }

    public ArrayList<Game> getGameList(){
        // TODO: UPDATE
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
        // TODO: UPDATE
        Game game = game_map.get(gameID);
        if (playerColor.equals("BLACK")){
            if (game.getBlackUsername() == null) {
                game.setBlackUsername(username);
            }else {
                throw new DataAccessException("Error: already taken");
            }
        }else if (playerColor.equals("WHITE")){
            if (game.getWhiteUsername() == null) {
                game.setWhiteUsername(username);
            }else {
                throw new DataAccessException("Error: already taken");
            }
        }else{
            throw new DataAccessException("Error: bad request");
        }
    }

    void configureDatabase() throws SQLException {
        // TODO: UPDATE
        try (Connection conn = database.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS mydatabase");
            createDbStatement.executeUpdate();

            var createGameTable = """
            CREATE TABLE  IF NOT EXISTS games (
                whiteplayer VARCHAR(255) DEFAULT NULL,
                blackplayer VARCHAR(255) DEFAULT NULL,
                gamename VARCHAR(255) NOT NULL,
                gameID VARCHAR(255) NOT NULL,
                chessGame TEXT NOT NULL,
                PRIMARY KEY (gameID)
            )""";

            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
