package Server.Handlers;

import Server.Requests.ClearRequest;
import Server.Requests.RegisterRequest;
import Server.Results.ClearResult;
import Server.Results.RegisterResult;
import Server.services.ClearService;
import Server.services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class ClearHandler extends Handler{
    @Override
    public Object handle(Request request, Response response) throws Exception {
        ClearRequest clearRequest = new Gson().fromJson(request.body(), ClearRequest.class);
        ClearResult clearResult = new ClearService().clear(clearRequest);
        return new Gson().toJson(clearResult);
    }
}
