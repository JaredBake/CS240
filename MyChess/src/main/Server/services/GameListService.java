package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.Requests.GameListRequest;
import Server.Results.GameListResult;
import dataAccess.DataAccessException;

public class GameListService {
    public GameListResult gameList(GameListRequest request) {
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();
        GameListResult gameListResult = new GameListResult();
        // Verify authToken
        try{
            authDAO.verifyToken(request.getAuthToken());
        }catch (DataAccessException wrong_info){
            gameListResult.setMessage(wrong_info.getMessage());
            gameListResult.setGameListNull();
            return gameListResult;
        }
        if (gameDAO.getGameList() != null){
            gameListResult.addGameList(gameDAO.getGameList());
        }
        return gameListResult;
    }
}
