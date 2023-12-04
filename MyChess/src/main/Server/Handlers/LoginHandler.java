package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.LoginRequest;
import Server.Requests.RegisterRequest;
import Server.Results.LoginResult;
import Server.Results.RegisterResult;
import Server.services.LoginService;
import Server.services.RegisterService;
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
