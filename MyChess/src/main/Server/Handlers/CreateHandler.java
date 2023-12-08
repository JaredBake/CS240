package Server.Handlers;

import Requests.CreateRequest;
import Results.CreateResult;
import Server.services.CreateService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class CreateHandler extends Handler{
    public CreateHandler() {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        CreateRequest createRequest = new Gson().fromJson(request.body(), CreateRequest.class);
        createRequest.setAuthToken(request.headers("Authorization"));
        CreateResult createResult = new CreateService().createGame(createRequest);
        if (createResult.getMessage() == null){
            response.status(200);
        } else if (createResult.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        }else if (createResult.getMessage().equals("Error: bad request")) {
            response.status(400);
        }else {
            response.status(500);
        }
        return new Gson().toJson(createResult);
    }
}
