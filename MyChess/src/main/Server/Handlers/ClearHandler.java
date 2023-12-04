package Server.Handlers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.ClearRequest;
import Server.Results.ClearResult;
import Server.services.ClearService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class ClearHandler extends Handler{
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    public ClearHandler() {
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        ClearRequest clearRequest = new Gson().fromJson(request.body(), ClearRequest.class);
        ClearResult clearResult = new ClearService().clear(clearRequest);
        return new Gson().toJson(clearResult);
    }
}
