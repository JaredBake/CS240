package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.CreateRequest;
import Server.Requests.LoginRequest;
import Server.Results.CreateResult;
import dataAccess.DataAccessException;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class CreateService {
    public CreateResult createGame(CreateRequest request) {
        CreateResult createResult = new CreateResult();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        // Verify authToken
        try{
            authDAO.verifyToken(request.getAuthToken());
        }catch (DataAccessException wrong_info){
            createResult.setMessage(wrong_info.getMessage());
            return createResult;
        }
        // Create a new game
        try {
            createResult.setGameID(gameDAO.create(request.getGameName()));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return createResult;
    }

}
