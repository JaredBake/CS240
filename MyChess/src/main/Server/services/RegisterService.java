package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.RegisterRequest;
import Server.Results.RegisterResult;
import dataAccess.DataAccessException;

public class RegisterService {
    public RegisterResult register(RegisterRequest request, UserDAO userDAO, AuthDAO authDAO) throws DataAccessException {
        // Create a new user to register
        // TODO: Check to make sure the password and username are present
        User user = new User(request.getPassword(),request.getUsername(),request.getEmail());
        RegisterResult registerResult = new RegisterResult();
        // If username is already used set error message
        try {
            userDAO.createUser(user);
        }catch (DataAccessException wrong_username){
            registerResult.setMessage(wrong_username.getMessage());
            return registerResult;
        }
            // Create and set the variables for registerResult
        registerResult.setAuthToken(authDAO.createToken(user.getUsername()));
        registerResult.setUsername(user.getUsername());
        return registerResult;
    }

    // UUID.randomUUID().toString()
}
