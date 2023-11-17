package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.ClearRequest;
import Server.Requests.RegisterRequest;
import Server.Results.ClearResult;
import Server.Results.RegisterResult;
import dataAccess.DataAccessException;

public class ClearService {
    public ClearResult clear(ClearRequest request) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        ClearResult clearResult = new ClearResult();
        userDAO.clearAll();
        authDAO.clearAll();
        gameDAO.clearAll();
        // TODO: return the success response 200
        return clearResult;
    }
}
