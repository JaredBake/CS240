package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.ClearRequest;
import Server.Requests.RegisterRequest;
import Server.Results.ClearResult;
import Server.Results.RegisterResult;
import Server.services.ClearService;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class ClearHandler extends Handler{
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    public ClearHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        ClearRequest clearRequest = new Gson().fromJson(request.body(), ClearRequest.class);
        ClearResult clearResult = new ClearService().clear(clearRequest, userDAO, authDAO, gameDAO);
        return new Gson().toJson(clearResult);
    }
}
