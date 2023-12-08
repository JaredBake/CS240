package Server.Handlers;

import Requests.LoginRequest;
import Results.LoginResult;
import Server.services.LoginService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class LoginHandler extends Handler{
    public LoginHandler() {
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
        LoginResult loginResult = new LoginService().login(loginRequest);
        if (loginResult.getMessage() == null){
            response.status(200 );
        }else if (loginResult.getMessage().equals("Error: unauthorized")){
            response.status(401 );
        }
        return new Gson().toJson(loginResult);
    }
}
