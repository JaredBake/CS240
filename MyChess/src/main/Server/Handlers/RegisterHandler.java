package Server.Handlers;

import Requests.RegisterRequest;
import Results.RegisterResult;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class RegisterHandler extends Handler {

    public RegisterHandler() {

    }

    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        if (registerResult.getMessage() == null){
            response.status(200);
        } else if (registerResult.getMessage().equals("Error: bad request")) {
            response.status(400);
        }else if (registerResult.getMessage().equals("Error: already taken")) {
            response.status(403);
        }else {
            response.status(500);
        }
        return new Gson().toJson(registerResult);
    }

}
