package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.LoginRequest;
import Server.Results.LoginResult;
import dataAccess.DataAccessException;

import javax.xml.crypto.Data;

/**
 * Class to get use in information given to login.
 */
public class LoginService {
    /**
     * function to access the login requests
     */
    public LoginResult login(LoginRequest request, UserDAO userDAO, AuthDAO authDAO) throws DataAccessException {
        // Login the User
        User user = null;
        LoginResult loginResult = new LoginResult();
        // Check for errors
        try{
            user = userDAO.find(request.getUsername(), request.getPassword());
        }catch (DataAccessException wrong_info){
            loginResult.setMessage(wrong_info.getMessage());
            return loginResult;
        }
        // Create and set the variables for registerResult
        loginResult.setAuthToken(authDAO.createToken(user.getUsername()));
        loginResult.setUsername(user.getUsername());
        return loginResult;
    }
}
