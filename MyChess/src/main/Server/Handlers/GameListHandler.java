package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.Model.Game;
import Server.Requests.GameListRequest;
import Server.Requests.RegisterRequest;
import Server.Results.GameListResult;
import Server.Results.RegisterResult;
import Server.services.GameListService;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class GameListHandler extends Handler{
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    public GameListHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameListRequest gameListRequest = new GameListRequest();
        gameListRequest.setAuthToken(request.headers("Authorization"));
        GameListResult gameListResult = new GameListService().gameList(gameListRequest, authDAO, gameDAO);
        return new Gson().toJson(gameListResult);
    }
}
