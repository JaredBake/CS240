package ClientTests;

import Requests.*;
import Results.*;
import org.junit.jupiter.api.*;
import client.ServerFacade;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientTests {

    public static final ServerFacade server = new ServerFacade();

    @BeforeEach
    @DisplayName("Phase4 Test")
    public void setup() throws Exception {
        ClearRequest clearRequest = new ClearRequest();
        clearRequest.setUsername("testUsername");
        ClearResult clearResult = server.clear(clearRequest);
    }

    @Test
    @Order(1)
    @DisplayName("Success Register")
    public void goodRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testUsername");
        registerRequest.setPassword("testPassword");
        registerRequest.setEmail("testEmail");
        RegisterResult registerResult = server.register(registerRequest);
        Assertions.assertNotNull(registerResult.getAuthToken(), "Did not return authtoken");
    }

    @Test
    @Order(2)
    @DisplayName("Failed Register")
    public void badRegsiter() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(null);
        registerRequest.setPassword("Pass");
        registerRequest.setEmail("Email");
        RegisterResult registerResult = server.register(registerRequest);
        Assertions.assertNotNull(registerResult.getMessage(), "Did not throw an error");
    }

    @Test
    @Order(3)
    @DisplayName("Success Logout")
    public void goodLogout() throws Exception {
        // Create AuthToken
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("dave");
        registerRequest.setPassword("testPassword");
        registerRequest.setEmail("testEmail");
        RegisterResult registerResult = server.register(registerRequest);
        String token = registerResult.getAuthToken();

        // Logout with token
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAuthToken(token);
        LogoutResult logoutResult = null;
        try {
            logoutResult = server.logout(logoutRequest);
        } catch (Exception exception) {
            Assertions.assertTrue(false, exception.getMessage());
            return;
        }
        Assertions.assertNull(logoutResult.getAuthToken(), "Did not delete authtoken");
    }

    @Test
    @Order(4)
    @DisplayName("Failed Logout")
    public void badLogout() throws Exception {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAuthToken("jdksl");
        LogoutResult logoutResult = server.logout(logoutRequest);

        Assertions.assertNotNull(logoutResult.getMessage(), "Did not delete authtoken");
    }

    @Test
    @Order(5)
    @DisplayName("Success Login")
    public void goodLogin() throws Exception {
        createUser("Jacob");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Jacob");
        loginRequest.setPassword("password");
        LoginResult loginResult;

        loginResult = server.login(loginRequest);

        Assertions.assertNotNull(loginResult.getAuthToken(), "Did not return authToken");
    }

    @Test
    @Order(6)
    @DisplayName("Failed Login")
    public void badLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUsername");
        loginRequest.setPassword(null);
        LoginResult loginResult = new LoginResult();
        try {
            loginResult = server.login(loginRequest);
        }catch (Exception exception){
            Assertions.assertTrue(true, exception.getMessage());
            return;
        }

        Assertions.assertNull(loginResult.getAuthToken(), "Did not throw an error");
    }

    @Test
    @Order(7)
    @DisplayName("Success Create Game")
    public void goodCreate() throws Exception {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(createUser("Me"));
        createRequest.setGameName("game1");
        try {
            CreateResult result = server.create(createRequest);
        }catch (Exception exception){
            Assertions.assertTrue(false, exception.getMessage());
            return;
        }
        Assertions.assertTrue(true, "Threw an error");
    }

    @Test
    @Order(8)
    @DisplayName("Failed Create Game")
    public void badCreate() throws Exception {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken("heyss");
        createRequest.setGameName("game1");

        CreateResult result = server.create(createRequest);
        Assertions.assertNotNull(result.getMessage(), "Did not throw an error");

    }

    @Test
    @Order(9)
    @DisplayName("Success List")
    public void goodList() throws Exception{
        // Create a game to call
        createGame("Game2");

        // Create a authToken
        GameListRequest request = new GameListRequest();
        request.setAuthToken(createUser("Oberma"));
        GameListResult result = new GameListResult();
        result = server.list(request);
        Assertions.assertNotNull(result.getGames(),"Did not return Games");
    }

    @Test
    @Order(10)
    @DisplayName("Failed List")
    public void badList() throws Exception {
        // Create a game to call
        createGame("Game3");

        // Create a authToken
        GameListRequest request = new GameListRequest();
        request.setAuthToken(createUser("Aberma"));
        GameListResult result = new GameListResult();
        result = server.list(request);
        Assertions.assertNotNull(result.getGames(),"Did not return Games");
    }

    @Test
    @Order(11)
    @DisplayName("Successful Join")
    public void goodJoin() throws Exception {
        // Create User
        String token = createUser("Steve");
        // Create Game to join
        Integer gameID = createGame("Game4");

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setPlayerColor("WHITE");
        joinRequest.setGameID(gameID);
        joinRequest.setUsername("Steve");
        joinRequest.setAuthToken(token);
        JoinResult result = server.join(joinRequest);
        Assertions.assertNull(result.getMessage(), "Threw an error");
    }

    @Test
    @Order(12)
    @DisplayName("Failed Join")
    public void badJoin() throws Exception {
        // Create User
        String token = createUser("Klye");
        // Create Game to join
        Integer gameID = createGame("Game5");

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setPlayerColor("WHITE");
        joinRequest.setGameID(546);
        joinRequest.setUsername("Klye");
        joinRequest.setAuthToken(token);
        JoinResult result = server.join(joinRequest);
        Assertions.assertNotNull(result.getMessage(), "Did not throw an error");
    }


    @Test
    @Order(13)
    @DisplayName("Clear all")
    public void clearAll() throws Exception{
        ClearRequest clearRequest = new ClearRequest();
        clearRequest.setUsername("testUsername");
        ClearResult clearResult = server.clear(clearRequest);

        LoginRequest request = new LoginRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        LoginResult result = new LoginResult();
        try {
            result = server.login(request);
        } catch (Exception exception) {
            Assertions.assertTrue(false, exception.getMessage());
            return;
        }
        Assertions.assertNull(result.getAuthToken(), "Did not throw and Error");
    }



    public String createUser(String name) throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername(name);
        request.setPassword("password");
        request.setEmail("email");
        return server.register(request).getAuthToken();
    }
    public Integer createGame(String name) throws Exception {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(createUser("Lik"));
        createRequest.setGameName(name);

        CreateResult result = server.create(createRequest);
        return result.getGameID();
    }
}