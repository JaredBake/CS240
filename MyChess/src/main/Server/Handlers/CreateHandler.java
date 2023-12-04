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
