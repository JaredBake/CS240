package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.LoginRequest;
import Server.Results.LoginResult;
import dataAccess.DataAccessException;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * Class to get use in information given to login.
 */
public class LoginService {
    /**
     * function to access the login requests
     */
    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
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
        try {
            loginResult.setAuthToken(authDAO.createToken(user.getUsername()));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        loginResult.setUsername(user.getUsername());
        return loginResult;
    }
}
