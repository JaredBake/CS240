package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.LogoutRequest;
import Server.Requests.RegisterRequest;
import Server.Results.LogoutResult;
import Server.Results.RegisterResult;
import dataAccess.DataAccessException;

public class LogoutService {

    public LogoutResult logout(LogoutRequest request) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        User user = new User(request.getPassword(),request.getUsername(),request.getEmail());
        LogoutResult logoutResult = new LogoutResult();
        userDAO.createUser(user);
        logoutResult.setAuthToken(authDAO.createToken(user.getUsername()));
        logoutResult.setUsername(user.getUsername());
        return logoutResult;
    }
}
