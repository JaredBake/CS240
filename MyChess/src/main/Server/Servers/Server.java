package Server.Servers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Handlers.*;
import Server.Model.User;
import Server.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;
import java.util.*;

public class Server implements Route {

    /**
     * Handler needs to interpret a Json into the pieces and then put it back together.
     */
    private ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);
        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");

        // Create the DAO's that will hold all the data
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();

        // Register handlers for each endpoint using the method reference syntax
        Spark.delete("/db", new ClearHandler(userDAO, authDAO, gameDAO));
        Spark.post("/user", new RegisterHandler(userDAO, authDAO));
        Spark.post("/session", new LoginHandler(userDAO, authDAO));
        Spark.delete("/session", new LogoutHandler(authDAO));
        Spark.get("/game", new GameListHandler(authDAO, gameDAO));
        Spark.post("/game", new CreateHandler(userDAO, authDAO, gameDAO));
        Spark.put("/game", new JoinHandler(userDAO, authDAO, gameDAO));
    }


//    private Object joinGame(Request request, Response response) {
//        return null;
//    }
//
//    private Object creatGame(Request request, Response response) {
//        return null;
//    }
//
//    private Object listGames(Request request, Response response) {
//        return null;
//    }
//
//    private Object logout(Request request, Response response) {
//        return null;
//    }
//
//    private Object login(Request request, Response response) {
//        return null;
//    }
//
//    private Object register(Request request, Response response) {
//        RegisterHandler reg_handler = new RegisterHandler();
//        reg_handler.setUsername(request.params(":name"));
//        return new Gson().toJson(Map.of("name", names));
//    }
//
//    private Object clearApp(Request request, Response response) {
//        return null;
//    }
//
//    private Object addName(Request req, Response res) {
//        names.add(req.params(":name"));
//        return listNames(req, res);
//    }
//
//    private Object listNames(Request req, Response res) {
//        res.type("application/json");
//        return new Gson().toJson(Map.of("name", names));
//    }
//
//    private Object deleteName(Request req, Response res) {
//        names.remove(req.params(":name"));
//        return listNames(req, res);
//    }
//
//
//    public String make_a_string(){
//        User user = new User("DuroBake", "password","email.com");
//        // Change into a JsonString
//        String user_json = gson.toJson(user);
//        System.out.println(user_json);
//        // Change into a User object from JsonString
//        User user_from_json = gson.fromJson(user_json,User.class);
//        return user_from_json.getUsername();
//    }

/*
UUID.randomUUID().toString()
 */

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}

