package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.UserDAO;
import Server.Model.AuthToken;
import Server.Requests.LogoutRequest;
import Server.Requests.RegisterRequest;
import Server.Results.LogoutResult;
import Server.Results.RegisterResult;
import Server.services.LogoutService;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handler{
    public LogoutHandler() {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAuthToken(request.headers("Authorization"));
        LogoutResult logoutResult = new LogoutService().logout(logoutRequest);
        if (logoutResult.getMessage() == null){
            response.status(200);
        } else if (logoutResult.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        }else if (logoutResult.getMessage().equals("Error: already taken")) {
            response.status(403);
        }else {
            response.status(500);
        }
        return new Gson().toJson(logoutResult);
    }
}
