package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Requests.CreateRequest;
import Results.CreateResult;
import dataAccess.DataAccessException;

import java.sql.SQLException;

public class CreateService {
    public CreateResult createGame(CreateRequest request) {
        CreateResult createResult = new CreateResult();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        String username;
        // Verify authToken
        try{
            username = authDAO.verifyToken(request.getAuthToken());
        }catch (DataAccessException wrong_info){
            createResult.setMessage(wrong_info.getMessage());
            return createResult;
        }
        if (username == null){
            createResult.setMessage("Error: unauthorized");
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
