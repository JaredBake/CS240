package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.User;
import Server.Requests.LogoutRequest;
import Server.Results.LogoutResult;
import dataAccess.DataAccessException;

public class LogoutService {

    public LogoutResult logout(LogoutRequest request, AuthDAO authDAO) {
        LogoutResult logoutResult = new LogoutResult();
        try {
            authDAO.verifyToken(request.getAuthToken());
        }catch (DataAccessException wrong_token){
            logoutResult.setMessage(wrong_token.getMessage());
            return logoutResult;
        }
        authDAO.deleteToken(request.getAuthToken());
        return logoutResult;
    }
}
