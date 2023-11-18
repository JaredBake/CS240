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
        /**
         * Checks to see if the username is already being used and checks to make sure there
         * is a username and password before creating a new user and authToken
         */
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()
        || user.getPassword().isBlank() || user.getUsername().isBlank()){
            throw new DataAccessException("Error: bad request");
        }
        if (users_map.containsKey(user.getUsername())){
            throw new DataAccessException("Error: already taken");
        }
        users_map.put(user.getUsername(),user);
        //throw new DataAccessException("Error: description");
    }

    /**
     * Tries to find the desired user from the database by username
     */
    public User find(String username, String password) throws DataAccessException{
        if (users_map.containsKey(username)){
            if (!users_map.get(username).getPassword().equals(password)) {
                throw new DataAccessException("Error: unauthorized");
            }
            return users_map.get(username);
        }
        throw new DataAccessException("Error: unauthorized");
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
