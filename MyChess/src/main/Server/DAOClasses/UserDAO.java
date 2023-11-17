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
    User find(Integer username) throws DataAccessException{
//TODO: Fix this
        if (users_map.containsKey(username)){
            return users_map.get(username);
        }
        throw new DataAccessException("Could not find user");
    }


    /**
     * Finds all users that have been created
     */
    HashSet<Game> findAll() throws DataAccessException{

        throw new DataAccessException("There are no users in the DataBase");
    }

    /**
     * Clears all users from the database
     */
    void clear() throws DataAccessException{}

}
