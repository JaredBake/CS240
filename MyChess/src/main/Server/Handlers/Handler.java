package Server.Handlers;

import Server.Model.User;
import Server.Requests.ClearRequest;
import Server.Requests.RegisterRequest;
import Server.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;
import java.util.*;

public class Handler implements Route {


    public String make_a_string(){
        User user = new User("DuroBake", "password","email.com");
        // Change into a JsonString
        String user_json = gson.toJson(user);
        System.out.println(user_json);
        // Change into a User object from JsonString
        User user_from_json = gson.fromJson(user_json,User.class);
        return user_from_json.getUsername();
    }

/*
UUID.randomUUID().toString()
 */



    String jsonString = "{\"username\":\"DuroBake\", \"password\":114766}";

    GsonBuilder builder = new GsonBuilder();

    Gson gson = builder.create();
    LoginResult result = gson.fromJson(jsonString, LoginResult.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}

