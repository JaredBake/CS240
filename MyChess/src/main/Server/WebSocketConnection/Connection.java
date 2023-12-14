package Server.WebSocketConnection;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public String token;
    public Session session;

    public Connection(String token, Session session) {
        this.token = token;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
        session.getRemote().sendString(token);
    }
}