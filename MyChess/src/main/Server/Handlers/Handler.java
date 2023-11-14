package Server.Handlers;

import Server.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Handler {

    /**
     * Handler needs to interpret a Json into the pieces and then put it back together.
     */
    public String Request;
    public String Result;
    public JsonReader reader;
    public JsonWriter writer;

    public String JsonRead() {
        return null;
    }

    String jsonString = "{\"username\":\"DuroBake\", \"password\":114766}";

    GsonBuilder builder = new GsonBuilder();

    Gson gson = builder.create();
    LoginResult result = gson.fromJson(jsonString, LoginResult.class);

}

