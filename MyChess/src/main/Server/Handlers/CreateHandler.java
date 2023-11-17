package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.RegisterRequest;
import Server.Results.RegisterResult;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class CreateHandler extends Handler{
    public CreateHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        return new Gson().toJson(registerResult);
    }
}
