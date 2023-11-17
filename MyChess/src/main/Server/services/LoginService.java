package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.LoginRequest;
import Server.Results.LoginResult;
import dataAccess.DataAccessException;

/**
 * Class to get use in information given to login
 */
public class LoginService {
    /**
     * function to access the login requests
     */
    public LoginResult login(LoginRequest request, UserDAO userDAO, AuthDAO authDAO) throws DataAccessException {

        // Login the User
        // TODO: Check to make sure the password and username match what is in the DAO
        User user = null;
        user = userDAO.find(request.getUsername());
        LoginResult loginResult = new LoginResult();
        // Create and set the variables for registerResult
        loginResult.setAuthToken(authDAO.createToken(user.getUsername()));
        loginResult.setUsername(user.getUsername());
        return loginResult;
    }
}
