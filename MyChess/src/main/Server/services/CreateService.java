package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.CreateRequest;
import Server.Requests.LoginRequest;
import Server.Results.CreateResult;
import dataAccess.DataAccessException;

import javax.xml.crypto.Data;

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
        createResult.setGameID(gameDAO.create(request.getGameName()));
        return createResult;
    }

}
