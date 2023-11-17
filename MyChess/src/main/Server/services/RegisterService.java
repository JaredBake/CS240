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
        User user = new User(request.getPassword(),request.getUsername(),request.getEmail());
        RegisterResult registerResult = new RegisterResult();
        userDAO.createUser(user);
        registerResult.setAuthToken(authDAO.createToken(user.getUsername()));
        registerResult.setUsername(user.getUsername());
        return registerResult;
    }

    // UUID.randomUUID().toString()
}
