package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Requests.ClearRequest;
import Results.ClearResult;

import java.sql.SQLException;

public class ClearService {
    public ClearResult clear(ClearRequest request) {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        ClearResult clearResult = new ClearResult();
        // No authToken needed for this part
        try {
            userDAO.clearAll();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        authDAO.clearAll();
        gameDAO.clearAll();
        return clearResult;
    }
}

