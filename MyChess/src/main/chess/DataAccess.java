package chess;

import dataAccess.DataAccessException;

import java.util.HashSet;

public class DataAccess {
    HashSet<String> users = new HashSet<>();
    private String user_dao = "";
    private String auth_dao = "";
    private String game_dao = "";

    void checkAuth() throws DataAccessException {
        if (!users.contains(user_dao)) {
            throw new DataAccessException("Does not have access");
        }
    }

}
