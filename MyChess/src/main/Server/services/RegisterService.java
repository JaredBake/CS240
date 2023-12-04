package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.RegisterRequest;
import Server.Results.RegisterResult;
import dataAccess.DataAccessException;

import java.sql.SQLException;

public class RegisterService {

    private UserDAO userDAO = new UserDAO();
    private AuthDAO authDAO = new AuthDAO();
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        RegisterResult registerResult = new RegisterResult();
        // Checks to make sure the password and username are present
        try {
            if (request.getPassword() == null || request.getUsername() == null || request.getEmail().isBlank()
                    || request.getPassword().isBlank() || request.getUsername().isBlank() || request.getEmail().isEmpty()) {
                throw new DataAccessException("Error: bad request");
            }
        }catch (DataAccessException requestError){
            registerResult.setMessage(requestError.getMessage());
            return registerResult;
        }

        User user = new User(request.getPassword(),request.getUsername(),request.getEmail());
            // Create and set the variables for registerResult
        try {
            userDAO.createUser(user);
        } catch (SQLException exception) {
            registerResult.setMessage(exception.getMessage());
            return registerResult;
        }
        try {
            registerResult.setAuthToken(authDAO.createToken(user.getUsername()));
        } catch (SQLException exception) {
            registerResult.setMessage(exception.getMessage());
            return registerResult;
        }
        registerResult.setUsername(user.getUsername());
        return registerResult;
    }
}
