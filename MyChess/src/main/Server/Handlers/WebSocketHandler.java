package Server.Handlers;

import Server.WebSocketConnection.ConnectionManager;
import com.google.gson.Gson;
//import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.*;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_PLAYER -> join(action.getAuthString(), session);
            case LEAVE -> leave(action.getAuthString(), session);
            case JOIN_OBSERVER -> observe(action.getAuthString(), session);
            case RESIGN -> resign(action.getAuthString(), session);
            case MAKE_MOVE -> move(action.getAuthString(), session);
        }
    }

    private void move(String token, Session session) throws IOException {
        connections.add(token, session);
        var command = new UserGameCommand(token);
        connections.broadcast(token, command);
    }

    private void resign(String token, Session session) throws IOException {
        connections.add(token, session);
        var command = new UserGameCommand(token);
        connections.broadcast(token, command);
    }

    private void observe(String token, Session session) throws IOException {
        connections.add(token, session);
        var command = new UserGameCommand(token);
        connections.broadcast(token, command);
    }

    private void join(String token, Session session) throws IOException {
        connections.add(token, session);
        var command = new UserGameCommand(token);
        connections.broadcast(token, command);
    }

    private void leave(String token, Session session) throws IOException {
        connections.add(token, session);
        var command = new UserGameCommand(token);
        connections.broadcast(token, command);
    }

    public void makeComment(String tokens, String sound) throws Exception {
        try {
            var token = String.format("%s says %s", tokens, sound);
            var command = new UserGameCommand(token);
            connections.broadcast(token, command);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}