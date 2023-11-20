package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.RegisterRequest;
import Server.Results.RegisterResult;
import dataAccess.DataAccessException;

public class RegisterService {
    public RegisterResult register(RegisterRequest request, UserDAO userDAO, AuthDAO authDAO) throws DataAccessException {
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
        // If username is already used set error message
        try {
            userDAO.checkRegister(request.getUsername());
        }catch (DataAccessException wrong_username){
            registerResult.setMessage(wrong_username.getMessage());
            return registerResult;
        }
        User user = new User(request.getPassword(),request.getUsername(),request.getEmail());
            // Create and set the variables for registerResult
        userDAO.createUser(user);
        registerResult.setAuthToken(authDAO.createToken(user.getUsername()));
        registerResult.setUsername(user.getUsername());
        return registerResult;
    }
}
