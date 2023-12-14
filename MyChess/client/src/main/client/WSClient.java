package client;

import Commands.MakeMove;
import chess.ChessBoard;
import com.google.gson.Gson;
import org.javatuples.Pair;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WSClient extends Endpoint {

    private ChessBoard currentChessBoard;

    public static void main(String[] args) {
    }

    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                // Handle the received message appropriately
                System.out.println("Received from server: " + message);
                handleChessBoardUpdate(message);
            }
        });
    }


    private void handleChessBoardUpdate(String gsonString) {
        Gson gson = new Gson();
        currentChessBoard = gson.fromJson(gsonString, ChessBoard.class);

        // Now 'currentChessBoard' contains the deserialized ChessBoard object
        // You can use it to update your UI or perform other actions based on the ChessBoard state
        System.out.println("Current ChessBoard state:\n" + currentChessBoard);
    }
    public void sendMakeMoveCommand(Pair<String, String> move) throws Exception {
        MakeMove makeMove = new MakeMove(move.getValue0(), move.getValue1());
        String jsonMessage = convertMessageToJson(makeMove);
        this.session.getBasicRemote().sendText(jsonMessage);
    }

    private String convertMessageToJson(MakeMove move) {
        // Convert the message object to JSON format (you can use a library like Jackson or Gson)
        // For simplicity, I'll use a basic approach (replace with your preferred JSON library)
        return "{" +
                "\"commandType\":\"" +  "MAKE_MOVE" + "\"," +
                "\"move\":\"" + move.getMove() + "\"" +
                "}";
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // Handle the WebSocket connection open event if needed
        System.out.println("WebSocket connection opened.");
    }
}
