package passoffTests.serverTests;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Handlers.CreateHandler;
import Server.Requests.CreateRequest;
import chess.ChessGame;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

public class Phase3TestCases {

    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;

    private static TestModels.TestUser existingUser;
    private static TestModels.TestUser newUser;
    private static TestModels.TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private String existingAuth;
    private UserDAO userDAO = new UserDAO();


    @BeforeAll
    public static void init() {
        existingUser = new TestModels.TestUser();
        existingUser.username = "Jared";
        existingUser.password = "Bake";
        existingUser.email = "urim@thummim.net";


        newUser = new TestModels.TestUser();
        newUser.username = "testUsername";
        newUser.password = "testPassword";
        newUser.email = "testEmail";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";

        serverFacade = new TestServerFacade("localhost", TestFactory.getServerPort());
    }


    @BeforeEach
    public void setup() {
        serverFacade.clear();

        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = existingUser.password;
        registerRequest.email = existingUser.email;

        //one user already logged in
        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
        existingAuth = regResult.authToken;
    }


    @Test
    @Order(1)
    @DisplayName("Normal User Login")
    public void successLogin() {
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;

        TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(loginResult.success, "Response returned not successful");
        Assertions.assertFalse(
                loginResult.message != null && loginResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response returned error message");
        Assertions.assertEquals(existingUser.username, loginResult.username,
                "Response did not give the same username as user");
        Assertions.assertNotNull(loginResult.authToken, "Response did not return authentication String");
    }

    @Test
    @Order(3)
    @DisplayName("Login Wrong Password")
    public void loginWrongPassword() {
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = newUser.password;

        TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);

        Assertions.assertEquals(HTTP_UNAUTHORIZED, serverFacade.getStatusCode(),
                "Server response code was not 401 Unauthorized");
        Assertions.assertFalse(loginResult.success, "Response didn't return not successful");
        Assertions.assertTrue(loginResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response missing error message");
        Assertions.assertNull(loginResult.username, "Response incorrectly returned username");
        Assertions.assertNull(loginResult.authToken, "Response incorrectly return authentication String");
    }


    @Test
    @Order(4)
    @DisplayName("Unique Authtoken Each Login")
    public void uniqueAuthorizationTokens() {
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;

        TestModels.TestLoginRegisterResult loginOne = serverFacade.login(loginRequest);
        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertNotNull(loginOne.authToken, "Login result did not contain an authToken");

        TestModels.TestLoginRegisterResult loginTwo = serverFacade.login(loginRequest);
        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertNotNull(loginTwo.authToken, "Login result did not contain an authToken");

        Assertions.assertNotEquals(existingAuth, loginOne.authToken,
                "Authtoken returned by login matched authtoken from prior register");
        Assertions.assertNotEquals(existingAuth, loginTwo.authToken,
                "Authtoken returned by login matched authtoken from prior register");
        Assertions.assertNotEquals(loginOne.authToken, loginTwo.authToken,
                "Authtoken returned by login matched authtoken from prior login");
    }


    @Test
    @Order(5)
    @DisplayName("Normal User Registration")
    public void successRegister() {
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = newUser.username;
        registerRequest.password = newUser.password;
        registerRequest.email = newUser.email;



        //submit register request
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        Assertions.assertTrue(registerResult.success,
                "The return response is not success or 200.");
    }


    @Test
    @Order(7)
    @DisplayName("Register Bad Request")
    public void failRegister() {
        //attempt to register a user without a username
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "";
        registerRequest.password = newUser.password;
        registerRequest.email = newUser.email;

        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        Assertions.assertNull(registerResult.username, "Response incorrectly contained username");
    }


    @Test
    @Order(8)
    @DisplayName("Normal Logout")
    public void successLogout() {
        //log out existing user
        TestModels.TestResult result = serverFacade.logout(existingAuth);

        Assertions.assertTrue(result.success, "Response didn't return successful");
    }


    @Test
    @Order(9)
    @DisplayName("Invalid Auth Logout")
    public void failLogout() {
        // log out using a wrong authToken
        TestModels.TestResult result = serverFacade.logout("55");

        // Checks for the word "error" in the message
        Assertions.assertTrue(result.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response did not return error message");
    }


    @Test
    @Order(10)
    @DisplayName("Valid Creation")
    public void goodCreate() {

        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        Assertions.assertNotNull(createResult.gameID, "Result did not return a game ID");
    }


    @Test
    @Order(11)
    @DisplayName("Create with Bad Authentication")
    public void badAuthCreate() {
        //log out user so auth is invalid
        serverFacade.logout(existingAuth);

        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        Assertions.assertTrue(createResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Bad result did not return an error message");
    }


    @Test
    @Order(16)
    @DisplayName("Join Created Game")
    public void goodJoin() {
        //create game
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        //join as white
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;

        //try join
        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, existingAuth);

        //check
        Assertions.assertFalse(
                joinResult.message != null && joinResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }


    @Test
    @Order(19)
    @DisplayName("Join Bad Game ID")
    public void badGameIDJoin() {
        //create game
        createRequest = new TestModels.TestCreateRequest();
        //join as white
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = 0;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;

        //try join
        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, existingAuth);

        //check
        Assertions.assertFalse(joinResult.success, "The request was not correct or successful");
    }


    @Test
    @Order(20)
    @DisplayName("List No Games")
    public void noGamesList() {
        TestModels.TestListResult result = serverFacade.listGames(existingAuth);

        Assertions.assertFalse(result.message != null && result.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }


    @Test
    @Order(21)
    @DisplayName("List Multiple Games")
    public void gamesList() {
        //register a few users to create games
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "a";
        registerRequest.password = "A";
        registerRequest.email = "a.A";
        TestModels.TestLoginRegisterResult userA = serverFacade.register(registerRequest);

        registerRequest.username = "b";
        registerRequest.password = "B";
        registerRequest.email = "b.B";
        TestModels.TestLoginRegisterResult userB = serverFacade.register(registerRequest);

        //create games

        //1 as black from A
        createRequest.gameName = "game1";
        TestModels.TestCreateResult game1 = serverFacade.createGame(createRequest, userA.authToken);

        //1 as white from B
        createRequest.gameName = "game2";
        TestModels.TestCreateResult game2 = serverFacade.createGame(createRequest, userB.authToken);

        //A join game 1 as black
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game1.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userA.authToken);

        //B join game 2 as white
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game2.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userB.authToken);

        //create expected entry items
        Collection<TestModels.TestListResult.TestListEntry> expectedList = new HashSet<>();

        //game 1
        TestModels.TestListResult.TestListEntry entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game1.gameID;
        entry.gameName = "game1";
        entry.blackUsername = userA.username;
        entry.whiteUsername = null;
        expectedList.add(entry);

        //game 2
        entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game2.gameID;
        entry.gameName = "game2";
        entry.blackUsername = null;
        entry.whiteUsername = userB.username;
        expectedList.add(entry);

        TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);
        //check


        Assertions.assertEquals(2, listResult.games.length);
    }


    @Test
    @Order(22)
    @DisplayName("Clear Test")
    public void clearData() {
        //create filler games
        createRequest.gameName = "Game1";
        serverFacade.createGame(createRequest, existingAuth);

        createRequest.gameName = "game2";
        serverFacade.createGame(createRequest, existingAuth);

        //log in new user
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "Emma";
        registerRequest.password = "Sue";
        registerRequest.email = "water@apple.com";
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        //create and join game for new user
        createRequest.gameName = "Tanner";
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, registerResult.authToken);

        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        serverFacade.verifyJoinPlayer(joinRequest, registerResult.authToken);

        //do clear
        TestModels.TestResult clearResult = serverFacade.clear();

        //test clear successful
        Assertions.assertFalse(
                clearResult.message != null && clearResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Clear Result returned an error message");

    }


}
