package Server.DAOClasses;


import Server.Adapters.BoardAdapter;
import Server.Adapters.GameAdapter;
import Server.Adapters.PieceAdapter;
import Server.Adapters.PositionAdapter;
import chess.*;
import com.google.gson.Gson;
import Model.Game;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import dataAccess.Database;
import chess.ChessGame;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class GameDAO {

    private Database database = new Database();
    /**
     * method for inserting a new game into the database.
     */
    public void insert(Game game) throws DataAccessException {

    }

    /**
     * Method for creating a new game with a unique gameID
     */
    public Integer create(String game_name) throws SQLException {
        Connection conn;
        Integer gameID = UUID.randomUUID().hashCode();
        if (gameID < 0){
            gameID = gameID * -1;
        }

        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }
        try (var preparedStatement = conn.prepareStatement("INSERT INTO games (gamename, gameID, chessGame) VALUES(?, ?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, game_name);
            preparedStatement.setInt(2, gameID);
            // Serialize and store the friend JSON.
            ChessGame game = new ChessGameIm();
            var json = new Gson().toJson(game);
            preparedStatement.setString(3, json);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            throw new SQLException(exception);
        }finally {
            try {
                database.closeConnection(conn);
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return gameID;
    }

    /**
     * Tries to find the desired game from the database by gameID
     */
    public Game find(Integer gameID) throws DataAccessException{
        // UPDATED
        Connection conn = null;
        Game chessGame = new Game();
        conn = database.getConnection();
        try (var preparedStatement = conn.prepareStatement("Select * FROM games WHERE gameID=?")) {
            preparedStatement.setString(1, String.valueOf(gameID));
            var rs = preparedStatement.executeQuery();
            if (rs.next()) {
                // get the return statement and pull all the info needed from it

                String json = rs.getString("chessGame");
                String gameName = rs.getString("gamename");
                String whitePlayer = rs.getString("whiteplayer");
                String blackPlayer = rs.getString("blackplayer");

                // Declare all the Adapters
                var builder = new GsonBuilder();
                builder.registerTypeAdapter(ChessGame.class, new GameAdapter());
                builder.registerTypeAdapter(ChessPosition.class, new PositionAdapter());
                builder.registerTypeAdapter(ChessPiece.class, new PieceAdapter());
                builder.registerTypeAdapter(ChessBoard.class, new BoardAdapter());

                // Define the game and all the players in the game
                chessGame.setGameID(gameID);
                chessGame.setGame(builder.create().fromJson(json, ChessGameIm.class));
                chessGame.setGameName(gameName);
                chessGame.setWhiteUsername(whitePlayer);
                chessGame.setBlackUsername(blackPlayer);

                return chessGame;
            }else {
                throw new DataAccessException("Error: bad request");
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }finally {
            try {
                database.closeConnection(conn);
            } catch (DataAccessException exception) {
                throw new RuntimeException(exception);
            }
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
        // UPDATED
        Connection conn;
        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }

        try (var preparedStatement = conn.prepareStatement("DELETE FROM games")) {
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }finally {
            try {
                database.closeConnection(conn);
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Game> getGameList(){
        // UPDATED
        ArrayList<Game> gameList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }
        try (var preparedStatement = conn.prepareStatement("Select * FROM games", RETURN_GENERATED_KEYS)) {

            // get the return statement and pull all the info needed from it
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var json = rs.getString("chessGame");
                String gameName = rs.getString("gamename");
                var whitePlayer = rs.getString("whiteplayer");
                var blackPlayer = rs.getString("blackplayer");
                Integer gameID = rs.getInt("gameID");
                Game chessGame = new Game();

                // Declare all the Adapters
                var builder = new GsonBuilder();
                builder.registerTypeAdapter(ChessGame.class, new GameAdapter());
                builder.registerTypeAdapter(ChessPosition.class, new PositionAdapter());
                builder.registerTypeAdapter(ChessPiece.class, new PieceAdapter());
                builder.registerTypeAdapter(ChessBoard.class, new BoardAdapter());

                // Define the game and all the players in the game
                chessGame.setGameID(gameID);
                chessGame.setGame(builder.create().fromJson(json, ChessGameIm.class));
                chessGame.setGameName(gameName);
                chessGame.setWhiteUsername(whitePlayer);
                chessGame.setBlackUsername(blackPlayer);

                gameList.add(chessGame);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }finally {
            try {
                database.closeConnection(conn);
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return gameList;
    }


    public void observeGame(String username) throws DataAccessException {
        HashSet<String> observers = new HashSet<>();
        if (username == null){
            throw new DataAccessException("Error: bad request");
        }
        observers.add(username);

    }
    /**
     * Claims a spot in the game and saves the color of team
     */
    public void claimSpot(String username, Integer gameID, String playerColor) throws DataAccessException {
        // TODO: Check
        try (Connection conn = database.getConnection()) {
            var getPlayerStatus = conn.prepareStatement("SELECT whiteplayer, blackplayer FROM games WHERE gameID=?");
            getPlayerStatus.setInt(1,gameID);
            var rs = getPlayerStatus.executeQuery();
            if (rs.next()) {
                if (playerColor == null){
                    observeGame(username);
                    return;
                }
                String whiteName = rs.getString("whiteplayer");
                String blackName = rs.getString("blackplayer");
                if (playerColor.equals("BLACK")) {
                    if (blackName == null) {
                        var setPlayerStatus = conn.prepareStatement("UPDATE games SET blackplayer=? WHERE gameID=?");
                        setPlayerStatus.setString(1, username);
                        setPlayerStatus.setInt(2, gameID);
                        setPlayerStatus.executeUpdate();
                    } else {
                        throw new DataAccessException("Error: already taken");
                    }
                } else if (playerColor.equals("WHITE")) {
                    if (whiteName == null) {
                        var setPlayerStatus = conn.prepareStatement("UPDATE games SET whiteplayer=? WHERE gameID=?");
                        setPlayerStatus.setString(1, username);
                        setPlayerStatus.setInt(2, gameID);
                        setPlayerStatus.executeUpdate();
                    } else {
                        throw new DataAccessException("Error: already taken");
                    }
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            } else {
                    if (playerColor == null) {
                        throw new DataAccessException("Error: bad request");
                    }
                    if (playerColor.equals("BLACK")) {
                        var setPlayerStatus = conn.prepareStatement("UPDATE games SET blackplayer=? WHERE gameID=?");
                        setPlayerStatus.setString(1, username);
                        setPlayerStatus.setInt(2, gameID);
                        setPlayerStatus.executeUpdate();
                    } else if (playerColor.equals("WHITE")) {
                        var setPlayerStatus = conn.prepareStatement("UPDATE games SET whiteplayer=? WHERE gameID=?");
                        setPlayerStatus.setString(1, username);
                        setPlayerStatus.setInt(2, gameID);
                        setPlayerStatus.executeUpdate();
                    } else {
                        throw new DataAccessException("Error: bad request");
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        void configureDatabase() throws SQLException {
        // UPDATED
        try (Connection conn = database.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS mydatabase");
            createDbStatement.executeUpdate();

            // IF need to update Table run this code
/**
            var dropGameTable = "DROP TABLE IF EXISTS games";

            try (var dropTableStatement = conn.prepareStatement(dropGameTable)) {
                dropTableStatement.executeUpdate();
            }
 */

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
