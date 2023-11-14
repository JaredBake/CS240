package Server.Handlers;

import Server.Model.User;
import Server.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Handler {
    String u = "username:";
    String p = "password:";
    String e = "email:";

    /**
     * Handler needs to interpret a Json into the pieces and then put it back together.
     */
    public Handler(){}


    public String make_a_string(){
        StringBuilder output = new StringBuilder();
        String username = "DuroBake";
        String password = "114766";
        String email = "a@gmail.com";
        output.append("{");
        output.append(u).append(gson.toJson(username)).append(",");
        output.append(p).append(gson.toJson(password)).append(",");
        output.append(e).append(gson.toJson(email));
        output.append("}");
        String end = output.toString();
        end = gson.toJson(end);
        return end;
    }

    public void make_not_a_string(){
        String output = make_a_string().toString();
        output = gson.toJson(output);
        String password = gson.fromJson(jsonString, String.class);
    }





    String jsonString = "{\"username\":\"DuroBake\", \"password\":114766}";

    GsonBuilder builder = new GsonBuilder();

    Gson gson = builder.create();
    LoginResult result = gson.fromJson(jsonString, LoginResult.class);

}

