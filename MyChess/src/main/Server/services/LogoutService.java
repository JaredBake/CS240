package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.LogoutRequest;
import Server.Results.LogoutResult;
import dataAccess.DataAccessException;

public class LogoutService {

    public LogoutResult logout(LogoutRequest request) {
        AuthDAO authDAO = new AuthDAO();
        LogoutResult logoutResult = new LogoutResult();
        String username;
        try {
            username = authDAO.verifyToken(request.getAuthToken());
        }catch (DataAccessException wrong_token){
            logoutResult.setMessage(wrong_token.getMessage());
            return logoutResult;
        }
        if (username == null){
            logoutResult.setMessage("Error: unauthorized");
            return logoutResult;
        }
        authDAO.deleteToken(request.getAuthToken());
        return logoutResult;
    }
}
