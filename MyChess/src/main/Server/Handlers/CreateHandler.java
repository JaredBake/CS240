package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.CreateRequest;
import Server.Requests.RegisterRequest;
import Server.Results.CreateResult;
import Server.Results.RegisterResult;
import Server.services.CreateService;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class CreateHandler extends Handler{
    private UserDAO userDAO;
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    public CreateHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        CreateRequest createRequest = new Gson().fromJson(request.body(), CreateRequest.class);
        createRequest.setAuthToken(request.headers("Authorization"));
        CreateResult createResult = new CreateService().createGame(createRequest, userDAO, authDAO, gameDAO);
        return new Gson().toJson(createResult);
    }
}
