package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.ClearRequest;
import Server.Results.ClearResult;
import dataAccess.DataAccessException;
import dataAccess.Database;

public class ClearService {
    public ClearResult clear(ClearRequest request, UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        ClearResult clearResult = new ClearResult();
        // No authToken needed for this part
        userDAO.clearAll();
        authDAO.clearAll();
        gameDAO.clearAll();
        return clearResult;
    }
}

