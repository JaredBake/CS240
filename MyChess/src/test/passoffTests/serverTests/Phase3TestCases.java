package passoffTests.serverTests;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Requests.*;
import Server.Results.*;
import Server.services.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Locale;

public class Phase3TestCases {
    private String existingAuth;
    private UserDAO userDAO = new UserDAO();
    private GameDAO gameDAO = new GameDAO();
    private AuthDAO authDAO = new AuthDAO();
    String username = "jared";
    String password = "mypass";
    String email = "email.com";
    Integer gameID;


    @BeforeEach
    public void setup() throws DataAccessException {
        ClearRequest clearRequest = new ClearRequest();
        ClearService clearService = new ClearService();
        clearService.clear(clearRequest);
        try {
            existingAuth = authDAO.createToken(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Create a test User
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setEmail(email);
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        // Create a test Game
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(existingAuth);
        createRequest.setGameName("TestGame");
        CreateResult createResult = new CreateService().createGame(createRequest);
        gameID = createResult.getGameID();
    }

    @Test
    @Order(1)
    @DisplayName("Normal User Registration")
    public void successRegister() throws DataAccessException{
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Emmiy");
        registerRequest.setPassword("passess");
        registerRequest.setEmail(email);
        Integer before;
        Integer after;
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        // Test registration
        Assertions.assertEquals(registerResult.getMessage(),null, "Gave and error when should have not");
    }


    @Test
    @Order(2)
    @DisplayName("Register Bad Request")
    public void failRegister() throws DataAccessException{
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("OtherName");
        registerRequest.setPassword(null);
        registerRequest.setEmail(email);
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        // Test registration
        Assertions.assertNotNull(registerResult.getMessage(),"Gave success when should have failed");
    }
    @Test
    @Order(3)
    @DisplayName("Normal User Login")
    public void successLogin() throws DataAccessException {
        Integer before;
        Integer after;

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);
        before = authDAO.listSize();

        LoginResult loginResult = new LoginService().login(loginRequest);

        after = authDAO.listSize();
        Assertions.assertNotEquals(before, after, "User was not added correctly");
    }


    @Test
    @Order(4)
    @DisplayName("Login Wrong Username")
    public void loginWrongPassword() throws DataAccessException{
        Integer before;
        Integer after;

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername("");
        before = authDAO.listSize();

        LoginResult loginResult = new LoginService().login(loginRequest);
        after = authDAO.listSize();
        Assertions.assertEquals(before, after, "Logged in with incorrect username/password relation");
    }


    @Test
    @Order(5)
    @DisplayName("Normal Logout")
    public void successLogout() {
        //log out existing user
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAuthToken(existingAuth);

        LogoutResult logoutResult = new LogoutService().logout(logoutRequest);

        Assertions.assertNull(logoutResult.getMessage(), "Response didn't return successful");
    }


    @Test
    @Order(6)
    @DisplayName("Invalid AuthToken Logout")
    public void failLogout() {
        // log out using a wrong authToken
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAuthToken("5656844");

        LogoutResult logoutResult = new LogoutService().logout(logoutRequest);

        Assertions.assertNotNull(logoutResult.getMessage(), "Got a successful response for bad authToken");
    }


    @Test
    @Order(7)
    @DisplayName("Valid Creation")
    public void goodCreate() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(existingAuth);
        createRequest.setGameName("Game1");
        CreateResult createResult = new CreateService().createGame(createRequest);

        Assertions.assertNotNull(createResult.getGameID(), "Result did not return a game ID");
    }


    @Test
    @Order(8)
    @DisplayName("Create with Bad Authentication")
    public void badAuthCreate() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken("doesn't work");
        createRequest.setGameName("BadGame");
        CreateResult createResult = new CreateService().createGame(createRequest);

        Assertions.assertNull(createResult.getGameID(), "Result returned a gameID when it shouldn't have");

    }

    @Test
    @Order(9)
    @DisplayName("Join Created Game")
    public void goodJoin() throws DataAccessException {

        //join as white
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setAuthToken(existingAuth);
        joinRequest.setGameID(gameID);
        joinRequest.setPlayerColor("WHITE");
        JoinResult joinWhite = new JoinService().join(joinRequest);

        joinRequest.setPlayerColor(null);
        //try join to observe
        JoinResult joinResult = new JoinService().join(joinRequest);

        //check
        Assertions.assertFalse(
                joinResult.getMessage() != null && joinResult.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }


    @Test
    @Order(10)
    @DisplayName("Join Bad Game ID")
    public void badGameIDJoin() throws DataAccessException {
        //join as white
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setAuthToken(existingAuth);
        joinRequest.setGameID(21112);
        joinRequest.setPlayerColor("WHITE");
        JoinResult joinWhite = new JoinService().join(joinRequest);

        joinRequest.setPlayerColor(null);
        //try join to observe
        JoinResult joinResult = new JoinService().join(joinRequest);

        //check
        Assertions.assertTrue(
                joinResult.getMessage() != null && joinResult.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }


    @Test
    @Order(11)
    @DisplayName("Success List Games")
    public void noGamesList() {
        GameListRequest gameListRequest = new GameListRequest();
        gameListRequest.setAuthToken(existingAuth);

        GameListResult gameListResult = new GameListService().gameList(gameListRequest);

        Assertions.assertFalse(gameListResult.getMessage() != null && gameListResult.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Did not return game list correctly");
    }


    @Test
    @Order(12)
    @DisplayName("List Games with Bad AuthToken")
    public void gamesList() {
        GameListRequest gameListRequest = new GameListRequest();
        gameListRequest.setAuthToken("this is not right");

        GameListResult gameListResult = new GameListService().gameList(gameListRequest);

        Assertions.assertTrue(gameListResult.getMessage() != null && gameListResult.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Did not return game list correctly");
    }


    @Test
    @Order(13)
    @DisplayName("Clear Test")
    public void clearData() throws DataAccessException {
        //create filler games
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(existingAuth);
        createRequest.setGameName("Game1");
        CreateResult createResult = new CreateService().createGame(createRequest);
        createRequest.setGameName("Game1");
        CreateResult createResult2 = new CreateService().createGame(createRequest);

        //log in new user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Jimmy");
        registerRequest.setPassword("paster");
        registerRequest.setEmail(email);
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        // clear all
        ClearRequest clearRequest = new ClearRequest();
        ClearResult clearResult = new ClearService().clear(clearRequest);


        //test clear successful
        Assertions.assertFalse(
                clearResult.getMessage() != null && clearResult.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Clear Result returned an error message");

    }



}