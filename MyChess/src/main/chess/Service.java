package chess;

import dataAccess.DataAccessException;
import netscape.javascript.JSObject;

public class Service {

    private String login_handler = "";
    private String register_handler = "";
    private String join_game_handler = "";

    void checkService() throws DataAccessException {
        Request r = new Request();
        if (!r.userAccess(login_handler)){
            throw new DataAccessException("Does not have access");
        }
    }
}
