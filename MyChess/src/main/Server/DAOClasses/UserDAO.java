package Server.DAOClasses;

import Server.Model.Game;
import Server.Model.User;
import dataAccess.DataAccessException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UserDAO {

    public UserDAO(){}
    private Map<String, User> users_map = new HashMap<>();


    public void createUser(User user) throws DataAccessException {
        if (users_map.containsKey(user.getUsername())){
            throw new DataAccessException("Error: already taken");
        }
        users_map.put(user.getUsername(),user);
    }

    /**
     * Tries to find the desired game from the database by gameID
     */
    public User find(String username) throws DataAccessException{
        if (users_map.containsKey(username)){
            return users_map.get(username);
        }
        throw new DataAccessException("Could not find user");
    }


    /**
     * Finds all users that have been created
     */
    public HashSet<Game> findAll() throws DataAccessException{

        throw new DataAccessException("There are no users in the DataBase");
    }

    /**
     * Clears all users from the database
     */
    public void clearAll() throws DataAccessException{
        users_map.clear();
    }

}
