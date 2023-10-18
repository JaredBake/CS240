package chess;

import dataAccess.DataAccessException;
import netscape.javascript.JSObject;
// private variables for model
public class Service {// private variables for model
    // private variables for service
    private String login_handler = "";// private variables for model
    private String register_handler = "";// private variables for model
    private String join_game_handler = "";// private variables for model

    void checkService() throws DataAccessException {// private variables for model
        Request r = new Request();// private variables for model
        if (!r.userAccess(login_handler)){// private variables for model
            throw new DataAccessException("Does not have access");// private variables for model
        }
    }
}
