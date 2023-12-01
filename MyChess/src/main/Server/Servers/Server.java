package Server.Servers;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Handlers.*;
import Server.Model.User;
import Server.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.Database;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;
import java.util.*;

public class Server implements Route {

    /**
     * Handler needs to interpret a Json into the pieces and then put it back together.
     */
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

/*
UUID.randomUUID().toString()
 */

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}

