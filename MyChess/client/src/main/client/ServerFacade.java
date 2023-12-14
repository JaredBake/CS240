package client;

import Adapters.PieceAdapter;
import Requests.*;
import Results.*;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class ServerFacade {
    private final Gson gson;

    public ServerFacade() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessPiece.class, new PieceAdapter());
        this.gson = gsonBuilder.create();
    }
    public static void main(String[] args) throws Exception {

    }

    public RegisterResult register(RegisterRequest request) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/user");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

// Specify that we are going to write out data
        http.setDoOutput(true);

// Write out a header
        http.addRequestProperty("Content-Type", "application/json");


// Write out the body
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
        // Make the request
        http.connect();


        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, RegisterResult.class);
            }
        }else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, RegisterResult.class);
            }
        }

    }

    public LoginResult login(LoginRequest request) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

// Specify that we are going to write out data
        http.setDoOutput(true);

// Write out a header
        http.addRequestProperty("Content-Type", "application/json");


// Write out the body
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
        // Make the request
        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, LoginResult.class);
            }
        }else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, LoginResult.class);
            }
        }
    }

    public CreateResult create(CreateRequest request) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

// Specify that we are going to write out data
        http.setDoOutput(true);

// Write out a header
        http.addRequestProperty("Content-Type", "application/json");
        http.addRequestProperty("Authorization", request.getAuthToken());
// Write out the body
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
        // Make the request

        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, CreateResult.class);
            }
        }else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, CreateResult.class);
            }
        }
    }

    public LogoutResult logout(LogoutRequest request) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");

// Specify that we are going to write out data
        http.setDoOutput(true);

// Write out a header
        http.addRequestProperty("Content-Type", "application/json");
        http.addRequestProperty("Authorization",request.getAuthToken());

// Write out the body
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }


        // Make the request
        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, LogoutResult.class);
            }
        }else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, LogoutResult.class);
            }
        }
    }

    public GameListResult list(GameListRequest request) throws Exception {

    // desired endpoint
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");

    // Specify that we are going to write out data
        http.setDoOutput(true);

    // Write out a header
        http.addRequestProperty("Content-Type", "application/json");
        http.addRequestProperty("Authorization", request.getAuthToken());


        // Make the request
        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return gson.fromJson(inputStreamReader, GameListResult.class);
            }
        }else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                String resonseString = readString(respBody);
                return new Gson().fromJson(inputStreamReader, GameListResult.class);
            }
        }
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

//    public JoinResult join(JoinRequest request) throws Exception {
//        // Specify the desired endpoint
//        URI uri = new URI("http://localhost:8080/game");
//        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
//        http.setRequestMethod("PUT");
//
//        // Specify that we are going to write out data
//        http.setDoOutput(true);
//
//        // Write out a header
//        http.addRequestProperty("Content-Type", "application/json");
//        http.addRequestProperty("Authorization", request.getAuthToken());
//
//        // Write out the body
//        try (var outputStream = http.getOutputStream()) {
//            var jsonBody = new Gson().toJson(request);
//            outputStream.write(jsonBody.getBytes());
//        }
//        // Make the request
//
//        http.connect();
//
//        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
//
//            // Output the response body
//            try (InputStream respBody = http.getInputStream()) {
//                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
//                return new Gson().fromJson(inputStreamReader, JoinResult.class);
//            }
//        }else {
//            try (InputStream respBody = http.getErrorStream()) {
//                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
//                return new Gson().fromJson(inputStreamReader, JoinResult.class);
//            }
//        }
//    }

    private Session webSocketSession;  // WebSocket session for communication

    public JoinResult join(JoinRequest request) throws Exception {
        // Specify the desired endpoint for the HTTP request
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("PUT");

        // Specify that we are going to write out data
        http.setDoOutput(true);

        // Write out a header
        http.addRequestProperty("Content-Type", "application/json");
        http.addRequestProperty("Authorization", request.getAuthToken());

        // Write out the body
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }

        // Make the HTTP request
        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Successfully joined the game, now establish a WebSocket connection
            establishWebSocketConnection(request.getAuthToken());

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, JoinResult.class);
            }
        } else {
            // Handle error response
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, JoinResult.class);
            }
        }
    }

    private void establishWebSocketConnection(String authToken) throws Exception {
        // Establish WebSocket connection with the server
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String wsEndpoint = "ws://localhost:8080/connect";
        webSocketSession = container.connectToServer(Client.class, new URI(wsEndpoint));

        // Send authentication token or any other necessary information over the WebSocket
        webSocketSession.getBasicRemote().sendText(authToken);
    }

    public ClearResult clear(ClearRequest request)throws Exception{
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/db");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");

        // Specify that we are going to write out data
        http.setDoOutput(true);

        // Write out a header
        http.addRequestProperty("Content-Type", "application/json");


        // Write out the body
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
        // Make the request
        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, ClearResult.class);
            }
        }else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return new Gson().fromJson(inputStreamReader, ClearResult.class);
            }
        }
    }
}
