package Server.services;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Requests.JoinRequest;
import Results.JoinResult;
import dataAccess.DataAccessException;

public class JoinService {
    public JoinResult join(JoinRequest joinRequest) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        JoinResult joinResult = new JoinResult();
        String username;
        // Verify Token
        try {
            username = authDAO.verifyToken(joinRequest.getAuthToken());
        }catch (DataAccessException badToken){
            joinResult.setMessage(badToken.getMessage());
            return joinResult;
        }
        if (username == null){
            joinResult.setMessage("Error: unauthorized");
            return joinResult;
        }
        joinRequest.setUsername(username);
        // Verify GameID
        try{
            gameDAO.find(joinRequest.getGameID());
        } catch (DataAccessException badID) {
            joinResult.setMessage(badID.getMessage());
            return joinResult;
        }
        // See if we are joining or observing a game
        if (joinRequest.getPlayerColor() == null){
            gameDAO.observeGame(joinRequest.getUsername());
        }else if (joinRequest.getPlayerColor().equals("BLACK") || joinRequest.getPlayerColor().equals("WHITE")){
            try {
                gameDAO.claimSpot(joinRequest.getUsername(),joinRequest.getGameID(),joinRequest.getPlayerColor());
            }catch (DataAccessException badinfo){
                joinResult.setMessage(badinfo.getMessage());
                return joinResult;
            }
        }else {
            joinResult.setMessage("Error: already taken");
            return joinResult;
        }
        return joinResult;
    }

}
