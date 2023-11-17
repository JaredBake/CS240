package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.RegisterRequest;
import Server.Results.RegisterResult;
import dataAccess.DataAccessException;

public class RegisterService {
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        // Create a new user to register
        // TODO: Check to make sure the password and username are present
        User user = new User(request.getPassword(),request.getUsername(),request.getEmail());
        RegisterResult registerResult = new RegisterResult();
        userDAO.createUser(user);
        // Create and set the variables for registerResult
        registerResult.setAuthToken(authDAO.createToken(user.getUsername()));
        registerResult.setUsername(user.getUsername());
        return registerResult;
    }

    // UUID.randomUUID().toString()
}
