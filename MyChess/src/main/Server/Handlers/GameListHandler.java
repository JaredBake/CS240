package Server.Handlers;

import Requests.GameListRequest;
import Results.GameListResult;
import Server.services.GameListService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class GameListHandler extends Handler{
    public GameListHandler() {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        GameListRequest gameListRequest = new GameListRequest();
        gameListRequest.setAuthToken(request.headers("Authorization"));
        GameListResult gameListResult = new GameListService().gameList(gameListRequest);
        if (gameListResult.getMessage() == null){
            response.status(200);
        } else if (gameListResult.getMessage().equals("Error: bad request")) {
            response.status(400);
        }else if (gameListResult.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        }else if (gameListResult.getMessage().equals("Error: already taken")) {
            response.status(403);
        }else{
            response.status(500);
        }
        return new Gson().toJson(gameListResult);
    }
}
