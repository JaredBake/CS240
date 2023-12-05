package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.JoinRequest;
import Server.Results.JoinResult;
import dataAccess.DataAccessException;

import javax.xml.crypto.Data;

public class JoinService {
    public JoinResult join(JoinRequest joinRequest) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        JoinResult joinResult = new JoinResult();
        // Verify Token
        try {
            authDAO.verifyToken(joinRequest.getAuthToken());
            joinResult.setAuthToken(joinRequest.getAuthToken());
        }catch (DataAccessException badToken){
            joinResult.setMessage(badToken.getMessage());
            return joinResult;
        }
        // Verify GameID
        try {
            gameDAO.find(joinRequest.getGameID());
        }catch (DataAccessException invalid){
            joinResult.setMessage(invalid.getMessage());
            return joinResult;
        }
        // See if we are joining or observing a game
        String username;
        try {
            username = authDAO.findUser(joinRequest.getAuthToken());
            joinResult.setUsername(username);
        }catch (DataAccessException badUser){
            joinResult.setMessage(badUser.getMessage());
            return joinResult;
        }
        if (joinRequest.getPlayerColor() == null){
            gameDAO.observeGame(joinRequest, userDAO);
        }else{
            try{
                gameDAO.claimSpot(username, joinRequest.getGameID(), joinRequest.getPlayerColor());
            }catch (DataAccessException spotTaken){
                joinResult.setMessage(spotTaken.getMessage());
                return joinResult;
            }
        }
        return joinResult;
    }

}
