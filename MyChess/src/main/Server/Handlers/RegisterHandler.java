package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.RegisterRequest;
import Server.Results.RegisterResult;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class RegisterHandler extends Handler {
    public UserDAO userDAO;
    public AuthDAO authDAO;
    public RegisterHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResult registerResult = new RegisterService().register(registerRequest, userDAO, authDAO);
        return new Gson().toJson(registerResult);
    }

}
