package Server.Results;

import Server.Model.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameListResult {
    /**
     * the message returned about the result
     */
    private String message;

    /**
     * List of games
     */
    private ArrayList<Game> game_list = new ArrayList<>();
    public GameListResult() {}

    /**
     * Getters and Setters for message, list of games properties
     */
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void addGameList(ArrayList<Game> gameList){
        game_list.addAll(gameList);
    }

    public ArrayList<Game> getGame_list(){
        return game_list;
    }

}
