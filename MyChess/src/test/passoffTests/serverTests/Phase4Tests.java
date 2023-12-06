package passoffTests.serverTests;

import Server.DAOClasses.AuthDAO;
import Server.DAOClasses.GameDAO;
import Server.DAOClasses.UserDAO;
import Server.Handlers.CreateHandler;
import Server.Model.Game;
import Server.Model.User;
import Server.Requests.*;
import Server.Results.*;
import Server.services.*;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.*;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

import javax.xml.crypto.Data;
import java.awt.image.RGBImageFilter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Phase4Tests {
    private static String existingAuth;
    private static Database database = new Database();
    static String username = "jared";
    String password = "mypass";
    String email = "email.com";
    String testusername = "testu";
    String testpassword = "testp";
    String testemail = "test.com";
    static Integer gameID;

    private static User user = new User("testPassword", "testUsername","urim@thummim.net");

    @BeforeAll
    @DisplayName("Phase4 Test")
    public static void setup() {
        AuthDAO authDAO = new AuthDAO();
        ClearRequest clearRequest = new ClearRequest();
        ClearService clearService = new ClearService();
        clearService.clear(clearRequest);
        try {
            existingAuth = authDAO.createToken(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Create a test Game
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(existingAuth);
        createRequest.setGameName("TestGame");
        CreateResult createResult = new CreateService().createGame(createRequest);
        gameID = createResult.getGameID();
    }


//
//    @Test
//    @DisplayName("Phase4 Test")
//    public void persistenceTest() throws DataAccessException {
//        //insert a bunch of data
//        //-------------------------------------------------------------------------------------------------------------
//        //register 2nd user
//
//
//        //create 2 games
//        CreateRequest createRequest = validCreateGameRequest();
//
//        //first one has both players in it
//        createRequest.setGameName("test1");
//        createRequest.setAuthToken(existingAuth);
//        CreateResult createResult = new CreateService().createGame(createRequest);
//
//
//
//
//
//
//        //second empty game
//        createRequest.setGameName("test2");
//        CreateResult createResult2 = new CreateService().createGame(createRequest);
//
//
//
//
//
//        //set games & check if swapped
//        ArrayList<Game> game1 = listResult1.getGames();
//        ArrayList<Game> game2 = listResult2.getGames();
//        if (Objects.equals(game1.get(0).getGameID(), game2.get(1).getGameID())) { //swap games if needed
//            ArrayList<Game> tempGame = game1;
//            game1 = game2;
//            game2 = tempGame;
//        }
//
//
//        //check that both tests are there
//        Assertions.assertEquals("test1", game1.get(0).getGameName(), "Game name changed after restart");
//        Assertions.assertEquals(createResult.getGameID(), game1.get(0).getGameID(), "Game ID Changed after restart");
//        Assertions.assertEquals("test2", game2.get(1).getGameName(), "Game name changed after restart");
//        Assertions.assertEquals(createResult2.getGameID(), game2.get(1).getGameID(), "Game ID changed after restart");
//
//        //check players in test1 game
//        Assertions.assertEquals(user.getUsername(), game1.get(0).getWhiteUsername(),
//                "White player username changed after restart");
//        Assertions.assertEquals("Emma", game1.get(0).getBlackUsername(), "Black player username changed after restart");
//
//        //make sure second user can log in
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("Emma");
//        loginRequest.setPassword(user.getPassword());
//        LoginResult loginResult = new LoginService().login(loginRequest);
//
//        Assertions.assertNull(loginResult.getMessage(), "Second user not able to log in after restart");
//        //-------------------------------------------------------------------------------------------------------------
//    }


    @Test
    @Order(1)
    @DisplayName("Normal User Registration")
    public void UserDAOCREATE() throws DataAccessException, SQLException {
        UserDAO userDAO = new UserDAO();
        User user = new User("water","Dave",email);
        User testUser;

        // Create and find user
        userDAO.createUser(user);
        testUser = userDAO.find("Dave", "water");

        // Test registration
        Assertions.assertEquals(user.getUsername(), testUser.getUsername(), "Found Wrong user");
    }

    @Test
    @Order(16)
    @DisplayName("Normal Create Token")
    public void createAUTHtoken() throws SQLException {
        String token;
        // Create authToken
        AuthDAO authDAO = new AuthDAO();
        token = authDAO.createToken(user.getUsername());

        Assertions.assertNotNull(token, "Did not return a authToken");

    }


    @Test
    @Order(2)
    @DisplayName("Create User Bad Username")
    public void failCreateUser() throws DataAccessException, SQLException {
        UserDAO userDAO = new UserDAO();
        User user = new User(password, null,email);
        User testUser = null;

        // Create and Find with bad username
        userDAO.createUser(user);
        testUser = userDAO.find(user.getUsername(), user.getPassword());


        // Test registration
        Assertions.assertNull(testUser,"Gave success when should have failed");
    }
    @Test
    @Order(17)
    @DisplayName("Create AuthToken bad")
    public void failCreateToken() throws DataAccessException, SQLException {
        AuthDAO authDAO = new AuthDAO();
        String token;

        // Create Token with bad username
        token = authDAO.createToken(null);

        // Test
        Assertions.assertNull(token, "Returned a token when did not register user");
    }
    @Test
    @Order(3)
    @DisplayName("Normal Find user")
    public void successFindUser() throws DataAccessException, SQLException {
        UserDAO userDAO = new UserDAO();
        User user = new User("water","Tim",email);
        User testUser;

        // Create and find user
        userDAO.createUser(user);
        testUser = userDAO.find("Tim", "water");

        Assertions.assertEquals(testUser.getUsername(), user.getUsername(), "Login was not successful");
    }


    @Test
    @Order(4)
    @DisplayName("bad FindUser")
    public void findWrongPassword() throws DataAccessException, SQLException {
        UserDAO userDAO = new UserDAO();
        User user = new User(password, "Luke",email);
        User testUser = null;

        // Create and Find with bad username
        userDAO.createUser(user);
        testUser = userDAO.find(user.getUsername(), null);


        // Test registration
        Assertions.assertNull(testUser,"Gave success when should have failed");
    }


    @Test
    @Order(5)
    @DisplayName("Normal Logout")
    public void successDeleteToken() throws SQLException, DataAccessException {
        AuthDAO authDAO = new AuthDAO();
        String token = null;
        User user = new User(testpassword, testusername,testemail);
        // Create authToken to use for logging out
        token = authDAO.createToken(user.getUsername());

        // log out existing user
        authDAO.deleteToken(token);

        String username = authDAO.verifyToken(token);
        // token has been deleted from database and does not give access
        Assertions.assertNull(username, "Token is still valid");
    }


    @Test
    @Order(6)
    @DisplayName("Invalid AuthToken Logout")
    public void badDeleteToken() throws DataAccessException {
        AuthDAO authDAO = new AuthDAO();
        // log out using a wrong authToken
        // DeleteToken wont ever be called if the authToken
        // is bad since it will be caught before it ever gets to it
        String token = "555";
        String response = null;
        authDAO.deleteToken(token);
        // There were no errors thrown since weather the Token
        // is in the database or not it no longer is in it
        Assertions.assertNull(response, "Got a successful response for bad authToken");
    }


    @Test
    @Order(7)
    @DisplayName("Valid Creation")
    public void goodCreate() throws SQLException {
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        String game = "Game4";
        Integer gameID = gameDAO.create(game);

        Assertions.assertNotNull(gameID, "Result did not return a game ID");
    }


    @Test
    @Order(8)
    @DisplayName("Create with no name")
    public void badCreate() throws SQLException {
        GameDAO gameDAO = new GameDAO();
        try {
            gameDAO.create(null);
        }catch (SQLException exception){
            Assertions.assertEquals(exception.getMessage(),"java.sql.SQLIntegrityConstraintViolationException: Column 'gamename' cannot be null",
                    "Did not throw error");
            return;
        }
        Assertions.assertFalse(true, "Did not throw and error");
    }

    @Test
    @Order(9)
    @DisplayName("claimSpot Created Game")
    public void goodclaim() throws DataAccessException {
        GameDAO gameDAO = new GameDAO();
        ArrayList<Game> list1 = new ArrayList<>();
        ArrayList<Game> list2 = new ArrayList<>();
        //have first user join
        gameDAO.claimSpot(user.getUsername(),gameID,"WHITE");

        list1 = gameDAO.getGameList();
        //have second user join
        gameDAO.claimSpot("Emma",gameID,"BLACK");
        list2 = gameDAO.getGameList();

        Assertions.assertNotEquals(list1, list2,"Result returned an error message");
    }


    @Test
    @Order(10)
    @DisplayName("ClaimSpot that is already Taken")
    public void badGameIDClaimSpot() throws DataAccessException, SQLException {
        GameDAO gameDAO = new GameDAO();
        Integer game = gameDAO.create("testgame1");
        //join as white
        gameDAO.claimSpot("Steve", game, "WHITE");
        //try to join as white
        try {
            gameDAO.claimSpot("Jess", game, "WHITE");
        }catch (DataAccessException taken){
            Assertions.assertEquals("Error: already taken", taken.getMessage(),
                    "Did not throw error with unaccetable join");
        }
    }

    @Test
    @Order(11)
    @DisplayName("Good Watch Game")
    public void goodObserve() throws DataAccessException, SQLException {
        GameDAO gameDAO = new GameDAO();
        ArrayList<Game> list1 = new ArrayList<>();
        ArrayList<Game> list2 = new ArrayList<>();
        Integer game = gameDAO.create("TestGame");
        list1 = gameDAO.getGameList();
        //try join to observe
        gameDAO.observeGame("Jared");

        list2 = gameDAO.getGameList();
        Assertions.assertEquals(list1.size(), list2.size(), "Did not observe correctly");

    }

    @Test
    @Order(12)
    @DisplayName("Watch username")
    public void badObserve() throws DataAccessException, SQLException {
        GameDAO gameDAO = new GameDAO();
        Integer game = gameDAO.create("TestGame");
        //try join to observe
        try {
            gameDAO.observeGame(null);
        }catch (DataAccessException message){
            Assertions.assertEquals("Error: bad request", message.getMessage(),
                    "Joined with bad gameID");
        }

    }

    @Test
    @Order(13)
    @DisplayName("Success List Games")
    public void goodGamesList() throws SQLException {
        GameDAO gameDAO = new GameDAO();
        ArrayList<Game> list1 = new ArrayList<>();
        ArrayList<Game> list2 = new ArrayList<>();
        list1 = gameDAO.getGameList();
        gameDAO.create("thisgame");
        list2 = gameDAO.getGameList();
        Assertions.assertNotEquals(list1.size(), list2.size(),"Did not return game list correctly");
    }


    @Test
    @Order(14)
    @DisplayName("Bad List Games")
    public void GamesList() throws DataAccessException {
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        ArrayList<Game> list = new ArrayList<>();
        //get list of games
        String username = authDAO.verifyToken("what is this");
        list = gameDAO.getGameList();
        Assertions.assertNull(username,"Gave permission to get list when should not have");
    }


    @Test
    @Order(15)
    @DisplayName("Clear Test")
    public void clearData() throws DataAccessException, SQLException {
        GameDAO gameDAO = new GameDAO();
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        //create filler games
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAuthToken(existingAuth);
        createRequest.setGameName("Game1");
        new CreateService().createGame(createRequest);
        createRequest.setGameName("Game1");
        new CreateService().createGame(createRequest);

        //log in new user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Jimmy");
        registerRequest.setPassword("paster");
        registerRequest.setEmail(email);
        new RegisterService().register(registerRequest);

        // clear all
        authDAO.clearAll();
        gameDAO.clearAll();
        userDAO.clearAll();

        ArrayList<Game> list = new ArrayList<>();
        list = gameDAO.getGameList();
        //test clear successful
        Assertions.assertEquals(0, list.size(), "Did not clear all from database6");

    }



}