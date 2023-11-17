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
    public UserDAO userDAO;
    public AuthDAO authDAO;
    public LoginHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
        LoginResult loginResult = new LoginService().login(loginRequest,userDAO,authDAO);
        return new Gson().toJson(loginResult);
    }
}
