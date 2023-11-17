package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.JoinRequest;
import Server.Requests.RegisterRequest;
import Server.Results.JoinResult;
import Server.Results.RegisterResult;
import Server.services.JoinService;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class JoinHandler extends Handler{
    public JoinHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        JoinRequest joinRequest = new Gson().fromJson(request.body(), JoinRequest.class);
        JoinResult joinResult = new JoinService().join(joinRequest);
        return new Gson().toJson(joinResult);
    }
}
