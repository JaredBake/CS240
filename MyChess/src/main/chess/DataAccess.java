package chess;

import dataAccess.DataAccessException;

import java.util.HashSet;
// private variables for model
public class DataAccess {// private variables for model
    // private variables for DataAccess
    HashSet<String> users = new HashSet<>();// private variables for DataAccess
    private String user_dao = "";// private variables for DataAccess
    private String auth_dao = "";// private variables for DataAccess
    private String game_dao = "";// private variables for DataAccess

    void checkAuth() throws DataAccessException {// private variables for DataAccess
        if (!users.contains(user_dao)) {// private variables for DataAccess
            throw new DataAccessException("Does not have access");// private variables for DataAccess
        }
    }

}
