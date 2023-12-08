package Server.Handlers;

import Requests.JoinRequest;
import Results.JoinResult;
import Server.services.JoinService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class JoinHandler extends Handler{
    public JoinHandler() {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        JoinRequest joinRequest = new Gson().fromJson(request.body(), JoinRequest.class);
        joinRequest.setAuthToken(request.headers("Authorization"));
        JoinResult joinResult = new JoinService().join(joinRequest);
        if (joinResult.getMessage() == null){
            response.status(200);
        } else if (joinResult.getMessage().equals("Error: bad request")) {
            response.status(400);
        }else if (joinResult.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        }else if (joinResult.getMessage().equals("Error: already taken")) {
            response.status(403);
        }else{
            response.status(500);
        }
        return new Gson().toJson(joinResult);
    }
}
