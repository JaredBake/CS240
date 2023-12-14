package Server.Servers;

import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Handlers.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;

public class Server implements Route {

    /**
     * Handler needs to interpret a Json into the pieces and then put it back together.
     */
    private final WebSocketHandler webSocketHandler = new WebSocketHandler();

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);
        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");
        Spark.webSocket("/connect", webSocketHandler);

        Spark.before((request, response) -> {System.out.println(requestInfoToString(request));});

        // Register handlers for each endpoint using the method reference syntax
        Spark.delete("/db", new ClearHandler());
        Spark.post("/user", new RegisterHandler());
        Spark.post("/session", new LoginHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.get("/game", new GameListHandler());
        Spark.post("/game", new CreateHandler());
        Spark.put("/game", new JoinHandler());
    }

    private static String requestInfoToString(Request request) {
        return request.requestMethod() + " " + request.url() + " " + request.body() + " " + request.headers("Authorization");
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}

