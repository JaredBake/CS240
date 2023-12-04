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
import java.util.*;

public class Phase4Tests {
    private String existingAuth;
    private static Database database = new Database();

    private static User user = new User("testPassword", "testPassword","urim@thummim.net");



    @BeforeAll
    public static void init() {
        ClearRequest clearRequest = new ClearRequest();
        ClearResult clearResult = new ClearService().clear(clearRequest);
    }


    @BeforeEach
    public void setup() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(user.getUsername());
        registerRequest.setPassword(user.getPassword());
        registerRequest.setEmail(user.getEmail());

        //one user already logged in
        RegisterResult regResult = null;
        try {
            regResult = new RegisterService().register(registerRequest);
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }
        existingAuth = regResult.getAuthToken();
    }


    private CreateRequest validCreateGameRequest() {
        var result = new CreateRequest();
        result.setGameName("Test Game");
        return result;
    }


    @Test
    @DisplayName("Phase4 Test")
    public void persistenceTest() throws DataAccessException {
        //insert a bunch of data
        //-------------------------------------------------------------------------------------------------------------
        //register 2nd user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Emma");
        registerRequest.setPassword(user.getPassword());
        registerRequest.setEmail(user.getEmail());

        RegisterResult registerResult = new RegisterService().register(registerRequest);
        if (registerResult.getMessage() != null){
            throw new DataAccessException("Error: bad registration");
        }
        String newAuth = registerResult.getAuthToken();

        //create 2 games
        CreateRequest createRequest = validCreateGameRequest();

        //first one has both players in it
        createRequest.setGameName("test1");
        createRequest.setAuthToken(existingAuth);
        CreateResult createResult = new CreateService().createGame(createRequest);

        //have first user join
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setGameID(createResult.getGameID());
        joinRequest.setPlayerColor("WHITE");
        JoinResult joinResult = new JoinService().join(joinRequest);
        if (joinResult.getMessage() != null){
            throw new DataAccessException("Error: Could not join game");
        }


        //have second user join
        joinRequest.setPlayerColor("BLACK");
        joinResult = new JoinService().join(joinRequest);
        if (joinResult.getMessage() != null){
            throw new DataAccessException("Error: Could not join game");
        }

        //second empty game
        createRequest.setGameName("test2");
        CreateResult createResult2 = new CreateService().createGame(createRequest);

        //get list of games
        GameListRequest listRequest = new GameListRequest();
        GameListResult listResult1 = new GameListService().gameList(listRequest);
        //------------------------------------------------------------------------------------------------------------


        //Will wait on the statement scanner.nextLine() till you push enter in the terminal window.
        //You may need to follow the steps under the heading "Setting up for the Persistence Test" in the "How To Get Started"
        Scanner scanner = new Scanner(System.in);
        System.out.println("Shut down the server, wait a few seconds, then restart the server. Then press ENTER.");
        scanner.nextLine();


        //Verify Data still there
        //-------------------------------------------------------------------------------------------------------------
        //list games, see both user in game
        //also checks that first user still has auth in database
        listRequest = new GameListRequest();
        GameListResult listResult2 = new GameListService().gameList(listRequest);

        Assertions.assertEquals(listResult1.getGames(), listResult2.getGames(), "Missing game(s) in database after restart");

        //set games & check if swapped
        ArrayList<Game> game1 = listResult1.getGames();
        ArrayList<Game> game2 = listResult2.getGames();
        if (Objects.equals(game1.get(0).getGameID(), game2.get(1).getGameID())) { //swap games if needed
            ArrayList<Game> tempGame = game1;
            game1 = game2;
            game2 = tempGame;
        }


        //check that both tests are there
        Assertions.assertEquals("test1", game1.get(0).getGameName(), "Game name changed after restart");
        Assertions.assertEquals(createResult.getGameID(), game1.get(0).getGameID(), "Game ID Changed after restart");
        Assertions.assertEquals("test2", game2.get(1).getGameName(), "Game name changed after restart");
        Assertions.assertEquals(createResult2.getGameID(), game2.get(1).getGameID(), "Game ID changed after restart");

        //check players in test1 game
        Assertions.assertEquals(user.getUsername(), game1.get(0).getWhiteUsername(),
                "White player username changed after restart");
        Assertions.assertEquals("Emma", game1.get(0).getBlackUsername(), "Black player username changed after restart");

        //make sure second user can log in
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Emma");
        loginRequest.setPassword(user.getPassword());
        LoginResult loginResult = new LoginService().login(loginRequest);

        Assertions.assertNull(loginResult.getMessage(), "Second user not able to log in after restart");
        //-------------------------------------------------------------------------------------------------------------
    }


}