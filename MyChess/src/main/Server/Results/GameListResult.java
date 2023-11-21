package Server.Results;

import Server.Model.Game;

import java.util.ArrayList;

public class GameListResult {
    /**
     * Variable for gameID
     */
    private String gameID;
    /**
     * the message returned about the result
     */
    private String message;

    /**
     * List of games
     */
    private ArrayList<Game> games = new ArrayList<>();
    public GameListResult() {}

    /**
     * Getters and Setters for message, list of games properties
     */
    public String getMessage() {
        return message;
    }
    public void setGameListNull(){
        games = null;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void addGameList(ArrayList<Game> gameList){
        games.addAll(gameList);
    }

    public ArrayList<Game> getGames() {
        return games;
    }

}
