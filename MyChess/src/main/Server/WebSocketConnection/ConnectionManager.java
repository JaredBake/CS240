package Server.WebSocketConnection;

import chess.ChessBoardIm;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.*;
import javax.websocket.WebSocketContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String visitorName, Session session) {
        var connection = new Connection(visitorName, session);
        connections.put(visitorName, connection);
    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
    }

    public void broadcast(String excludeVisitorName, UserGameCommand command) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.token.equals(excludeVisitorName)) {
                    c.send(command.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.token);
        }
    }
}