package client;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.*;

import Model.Game;
import Requests.*;
import Results.*;
import org.javatuples.*;
import ui.EscapeSequences;

import static ui.EscapeSequences.*;

public class main {

    private static final ServerFacade server = new ServerFacade();


    public static void main(String[] args) throws Exception {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        displayWelcome(out);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            line = line.toLowerCase();
            if (line.equals("help")) {
                displayPreLoginCommands(out);
            } else if (line.equals("login")) {
                Pair<String, String> login = displayLogin(out);
                if (login == null){
                    badRequest(out);
                    displayPreLoginCommands(out);
                }else {
                    LoginResult result = new LoginResult();
                    LoginRequest request = new LoginRequest();
                    request.setUsername(login.getValue0());
                    request.setPassword(login.getValue1());
                    try {
                        result = server.login(request);
                    }catch (Exception message){
                        System.out.println("Username or Password was not valid");
                        badRequest(out);
                        continue;
                    }
                    // if login was successful give access to all commands for members
                    String token = result.getAuthToken();
                    String reason_for_exit = memberAccess(out, token);
                    if (reason_for_exit.equals("logout")){
                        displayLogout(out);
                    }else {
                        badRequest(out);
                    }
                }
            } else if (line.equals("register")) {
                Triplet<String, String, String> registration;
                registration = displayRegisterInfo(out);
                if (registration == null){
                    badRequest(out);
                    displayPreLoginCommands(out);
                }else {
                    String token;
                    RegisterRequest request = new RegisterRequest();
                    request.setUsername(registration.getValue0());
                    request.setPassword(registration.getValue1());
                    request.setEmail(registration.getValue2());
                    try {
                        RegisterResult result = server.register(request);
                        token = result.getAuthToken();
                    }catch (Exception message){
                        System.out.println("Username or Password was not valid");
                        badRequest(out);
                        continue;
                    }
                    // if registration successful give access to all commands for members
                    String reason_for_exit = memberAccess(out, token);
                    if (reason_for_exit.equals("logout")){
                        displayLogout(out);
                    }else {
                        badRequest(out);
                    }
                }
            } else if (line.equals("quit")) {
                displayQuit(out);
                break;
            } else {
                badRequest(out);
            }
        }
    }

    public static Pair<String, String> displayLogin(PrintStream out){
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD );
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf("Type in your username!%n >>>  ");

        username = scanner.next();
        System.out.printf("Type in your password%n >>>  ");
        password = scanner.next();
        if (username == null || password == null){
            return null;
        }
        return new Pair<>(username, password);
    }

    public static Triplet<String, String, String> displayRegisterInfo(PrintStream out){
        String username;
        String password;
        String email;
        Scanner scanner = new Scanner(System.in);
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD );
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf("Type in your username%n >>>  ");

        username = scanner.next();
        System.out.printf("Type in your password%n >>>  ");
        password = scanner.next();
        System.out.printf("Type in your email%n >>>  ");
        email = scanner.next();
        if (username == null || password == null || email == null){
            return null;
        }
        return new Triplet<>(username, password, email);
    }

    public static void displayLogout(PrintStream out){
        out.println(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_ITALIC);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print("Successfully logged out!! Come back soon!!");
        displayPreLoginCommands(out);
    }
    public static void displayQuit(PrintStream out){
        out.println(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_ITALIC );
        out.print(SET_TEXT_COLOR_RED);
        System.out.printf(" \u2639 \u2639 \u2639 YOUR LEAVING???" +
                " I WILL FIND YOU!! \u263b \u263b \u263b%n>>> ");
    }

    public static void badRequest(PrintStream out){
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD );
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf(" \u2623 \u2623 \u2623 Oh no!! That is unacceptable!! " +
                "Please type a correct response!! \u2623 \u2623 \u2623%n>>> ");
    }

    public static void displayWelcome(PrintStream out){
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD );
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf(" \u2623 Welcome to the best chess game ever made!!" +
                " Please type \"help\" to get started! \u2623%n>>> ");
    }

    public static void displayPreLoginCommands(PrintStream out) {
        out.println(EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.SET_TEXT_BOLD +
                EscapeSequences.SET_TEXT_COLOR_YELLOW +
                "help : Displays user actions available.\n" +
                "quit : Exits the program.\n" +
                "login : Login a registered account.\n" +
                "register : Create a new account." + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_BG_COLOR +
                "\n>>> ");
    }

    public static void memberAccessCommands(PrintStream out){
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD );
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf("help : Displays available access.\n" +
                "logout : Logs you out.\n" +
                "Create : Create a game.\n" +
                "List : List all games in progress.\n" +
                "Join : Join a created game as a player.\n" +
                "Observe : Join a game as an observer.%n>>> ");
    }

    public static void displayMustLogout(PrintStream out){
        out.println(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_ITALIC );
        out.print(SET_TEXT_COLOR_RED);
        System.out.printf("You must logout before you can leave " +
                "\u263b \u263b \u263b%n>>> ");
    }

    public static String memberAccess(PrintStream out, String token){
        memberAccessCommands(out);
        while (true){
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line != null) {
            line = line.toLowerCase();
            }
            if (line == null){
                badRequest(out);
            } else if (line.equals("logout")) {

                LogoutRequest request = new LogoutRequest();
                request.setAuthToken(token);
                try {
                    LogoutResult result = server.logout(request);
                } catch (Exception message) {
                    System.out.println("Could not logout correctly");
                    badRequest(out);
                    continue;
                }
                return "logout";
            }else if (line.equals("create")){
                String create = displayCreate(out);
                if (create == null){
                    badRequest(out);
                    displayPreLoginCommands(out);
                }else {
                    CreateRequest request = new CreateRequest();
                    request.setGameName(create);
                    request.setAuthToken(token);
                    Integer gameID = null;
                    try {
                        CreateResult result = server.create(request);
                        gameID = result.getGameID();
                    }catch (Exception message){
                        System.out.println("Could not create game");
                        badRequest(out);
                        continue;
                    }
                    displaySuccessCreate(out, gameID);
                }
            }else if (line.equals("list")) {
                ArrayList<Game> list = new ArrayList<>();
                GameListRequest request = new GameListRequest();
                request.setAuthToken(token);
                try {
                    GameListResult result = server.list(request);
                    list = result.getGames();
                } catch (Exception message) {
                    System.out.println("Could not create list");
                    badRequest(out);
                    continue;
                }
                displayList(out, list);
            }else if (line.equals("join")){
                Pair<Integer, String> join = memberJoin(out);
                if (join == null){
                    badRequest(out);
                    continue;
                }
                JoinRequest request = new JoinRequest();
                request.setGameID(join.getValue0());
                request.setPlayerColor(join.getValue1());
                request.setAuthToken(token);
                try {
                    JoinResult result = server.join(request);
                    joinGameUI(out, token, join);
                    // TODO: Return the game String to display
                    return "logout";
                } catch (Exception message) {
                    System.out.println("Could not create list");
                    badRequest(out);
                    continue;
                }
//                displayGame(out, join.getValue1());
            }else if (line.equals("observe")) {
                Integer observe = memberObserve(out);
                JoinRequest request = new JoinRequest();
                request.setGameID(observe);
                request.setPlayerColor(null);
                request.setAuthToken(token);
                try {
                    JoinResult result = server.join(request);
                    observeGameUI();
                    // TODO: Return the game String to display
                } catch (Exception message) {
                    System.out.println("Could not create list");
                    badRequest(out);
                    continue;
                }
                displayGame(out, "null");
            }else if (line.equals("quit")){
                displayMustLogout(out);
            }else if (line.equals("stop")){
                return "logout";
            }else if (line.equals("help")){
                memberAccessCommands(out);
            }else {
                badRequest(out);
            }
        }
    }

    public static void joinGameUI(PrintStream out, String token, Pair<Integer, String> info) throws Exception {
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_YELLOW);
        WSClient client = new WSClient();

        try {
            // Assuming ChessClient has a method to send messages to the server
            // Update this based on your actual implementation
            client.send("JOIN_GAME " + info.getValue0() + " " + info.getValue1());

            // TODO: Call the ChessClient method to handle the game joining logic
            handleGameJoin(client, info.getValue0(), info.getValue1());
        } catch (Exception message) {
            System.out.println("Could not join the game");
            badRequest(out);
        }
    }

    private static void handleGameJoin(WSClient client, Integer value0, String value1) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String command;
        // Display available commands to the user
        displayGameJoinCommands();
        while (true) {


            // Read user input
            command = scanner.nextLine().trim().toLowerCase();

            // Process user commands
            switch (command) {
                case "help":
                    displayGameJoinCommands();
                    continue;
                case "redraw":
                    redrawChessBoard();
                    continue;
                case "leave":
                    leaveGame();
                    return;  // exit the loop and method
                case "move":
                    Pair<String, String> moves = getMove();
                    client.sendMakeMoveCommand(moves);
                    continue;
                case "resign":
                    resign();
                    break;
                case "highlight":
                    highlightLegalMoves();
                    continue;
                default:
                    System.out.println("Invalid command. Type 'help' for available commands.");
            }
        }
    }

    private static void displayGameJoinCommands() {
        System.out.println("Available Commands:");
        System.out.println(" - help:\t\t\tDisplays what actions you can take.");
        System.out.println(" - redraw:\tRedraws the chess board.");
        System.out.println(" - leave:\t\tLeave the game.");
        System.out.println(" - move:\t\tInput position of piece to move and position of where to move it.");
        System.out.println(" - resign:\t\tThe user forfeits the game.");
        System.out.println(" - highlight:\tHighlights legal moves.");
        System.out.print(">>> ");
    }

    private static void redrawChessBoard() {
        // Redraw the chess board
        System.out.println("Redrawing chess board...");
    }

    private static void leaveGame() {
        // Perform actions to leave the game
        System.out.println("Leaving the game. Transitioning back to Post-Login UI.");
        // Additional logic to handle leaving the game
    }

    private static Pair<String, String> getMove() {
        // Allow the user to input a move and update the board
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please type the position of the piece you would like to move:");
        var piece = scanner.next();
        System.out.println("Please type the position of where you would like to move to:");
        var move = scanner.next();
        // Additional logic for making a move

        return new Pair<>(piece,move);
    }

    private static void resign() {
        // Prompt the user to confirm resignation and forfeit the game
        System.out.println("Are you sure you want to resign? (Type 'yes' to confirm)");
        Scanner scanner = new Scanner(System.in);
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if ("yes".equals(confirmation)) {
            System.out.println("Resigning... The game is over.");
            // Additional logic for resigning
        } else {
            System.out.println("Resignation canceled.");
        }
    }

    private static void highlightLegalMoves() {
        // Allow the user to input a piece to highlight legal moves
        System.out.println("Highlighting legal moves for a piece...");
        // Additional logic for highlighting legal moves
    }

    private static void observeGameUI(){

    }

    private static void displayGame(PrintStream out, String color) {
        out.print(ERASE_SCREEN);
        switch (color) {
            case "null" -> {
                displayWhiteChessBoard(out);
                out.println(SET_TEXT_COLOR_DARK_GREY);
                displayBlackChessBoard(out);
            }
            case "WHITE" -> displayWhiteChessBoard(out);
            case "BLACK" -> displayBlackChessBoard(out);
        }
//        out.println(SET_TEXT_COLOR_DARK_GREY);
    }
    public static void displayWhiteChessBoard(PrintStream out){
        drawWhiteHeaders(out);
        drawWhiteRows(out);
        drawWhiteHeaders(out);
    }

    public static void displayBlackChessBoard(PrintStream out){
        drawBlackHeaders(out);
        drawBlackRows(out);
        drawBlackHeaders(out);
    }

    public static void drawRow(PrintStream out, ArrayList<String> row, String setText, Boolean startblack){
        boolean first = true;
        boolean isWhiteSquare = startblack;
        for (String num: row){
            if (row.get(0).equals(num) || row.get(9).equals(num)) {
                first = !first;
                out.print(SET_BG_COLOR_DARK_GREEN);
                out.print(SET_TEXT_COLOR_WHITE);
                out.print(num);
            }else {
                if (isWhiteSquare) {
                    out.print(setText);
                    out.print(SET_BG_COLOR_DARK_GREY);
                } else {
                    out.print(setText);
                    out.print(SET_BG_COLOR_WHITE);
                }
                out.print(num);
                out.print(EMPTY.repeat(row.size() / 6));
                isWhiteSquare = !isWhiteSquare;
            }
        }


        out.println(SET_BG_COLOR_BLACK);
    }

    private static void drawWhiteRows(PrintStream out){
        String[] emptyRow = {"   8   ","    ","    ","    ","    ","    ","    ","    ","    ", "    "};
        ArrayList<String> empty = new ArrayList<>(List.of(emptyRow));
        String[] piece = {"   8   ","   R", "   N", "   B", "   K", "   Q", "   B", "   N", "   R", ""};
        ArrayList<String> whitePieces = new ArrayList<>(List.of(piece));
        ArrayList<String> blackPieces = new ArrayList<>(List.of(piece));
        String[] pawnPiece = {"   8   ","   P", "   P", "   P", "   P", "   P", "   P", "   P", "   P", ""};
        ArrayList<String> pawns = new ArrayList<>(List.of(pawnPiece));
        boolean startblack = true;
        for (int i = 8; i > 0; i--){
            empty.set(0,"   " + i + "    ");
            empty.set(9,"   " + i + "    ");
            if (i > 2 && i < 7){
                drawRow(out, empty, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 8){
                whitePieces.set(0,"   " + i + "    ");
                whitePieces.set(9,"   " + i + "    ");
                drawRow(out, whitePieces, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 1){
                blackPieces.set(0,"   " + i + "    ");
                blackPieces.set(9,"   " + i + "    ");
                drawRow(out, blackPieces, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 2){
                pawns.set(0,"   " + i + "    ");
                pawns.set(9,"   " + i + "    ");
                drawRow(out, pawns, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 7){
                pawns.set(0,"   " + i + "    ");
                pawns.set(9,"   " + i + "    ");
                drawRow(out, pawns, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }

        }

    }

    private static void drawBlackRows(PrintStream out){

        // Blue is white
        String[] emptyRow = {"   8   ","    ","    ","    ","    ","    ","    ","    ","    ", "    "};
        ArrayList<String> empty = new ArrayList<>(List.of(emptyRow));
        String[] piece = {"   8   ","   R", "   N", "   B", "   K", "   Q", "   B", "   N", "   R", ""};
        ArrayList<String> whitePieces = new ArrayList<>(List.of(piece));
        ArrayList<String> blackPieces = new ArrayList<>(List.of(piece));
        String[] pawnPiece = {"   8   ","   P", "   P", "   P", "   P", "   P", "   P", "   P", "   P", ""};
        ArrayList<String> blackPawns = new ArrayList<>(List.of(pawnPiece));
        ArrayList<String> whitePawns = new ArrayList<>(List.of(pawnPiece));
        boolean startblack = true;
        for (int i = 0; i <= 8; i++){
            empty.set(0,"   " + i + "   ");
            empty.set(9,"   " + i + "   ");
            if (i > 2 && i < 7){
                drawRow(out, empty, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 1){
                blackPieces.set(0,"   " + i + "    ");
                blackPieces.set(9,"   " + i + "    ");
                drawRow(out, blackPieces, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 8){
                whitePieces.set(0,"   " + i + "    ");
                whitePieces.set(9,"   " + i + "    ");
                drawRow(out, whitePieces, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 7){
                whitePawns.set(0,"   " + i + "    ");
                whitePawns.set(9,"   " + i + "    ");
                drawRow(out, whitePawns, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 2){
                blackPawns.set(0,"   " + i + "    ");
                blackPawns.set(9,"   " + i + "    ");
                drawRow(out, blackPawns, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }

        }

    }
    private static void drawBlackHeaders(PrintStream out) {

        out.print(SET_TEXT_COLOR_WHITE);
        out.print(SET_BG_COLOR_DARK_GREEN);
        String[] headers = { "    ","   h", "   g", "   f","   e","   d","   c","   b","   a", "    " };
        boolean isWhiteSquare = true;
        for (String num: headers){
            out.print(num);
            out.print(EMPTY.repeat(headers.length / 6));
            isWhiteSquare = !isWhiteSquare;
        }
        out.println(SET_BG_COLOR_BLACK);
    }

    private static void drawWhiteHeaders(PrintStream out) {

        out.print(SET_TEXT_COLOR_WHITE);
        out.print(SET_BG_COLOR_DARK_GREEN);
        String[] headers = { "    ","   a", "   b", "   c","   d","   e","   f","   g","   h", "    " };
        boolean isWhiteSquare = true;
        for (String num: headers){
            out.print(num);
            out.print(EMPTY.repeat(headers.length / 6));
            isWhiteSquare = !isWhiteSquare;
        }
        out.println(SET_BG_COLOR_BLACK);
    }

    private static Integer memberObserve(PrintStream out) {
        int gameID;
        Scanner scanner = new Scanner(System.in);
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf("Please input the gameID of the game you want to observe!%n >>>  ");
        gameID = scanner.nextInt();

        return gameID;
    }

    private static Pair<Integer, String> memberJoin(PrintStream out) {
        int gameID;
        String color;
        Scanner scanner = new Scanner(System.in);
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf("Please input the gameID of the game you want to join!%n >>>  ");
        try {
            gameID = scanner.nextInt();
        }catch (Exception exception){
            return null;
        }
        System.out.printf("Please choose your color: WHITE or BLACK!%n >>>  ");
        color = scanner.next();

        return new Pair<>(gameID, color);
    }

    private static void displayList(PrintStream out, ArrayList<Game> list) {
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_YELLOW);

        System.out.println("Here is the list of Games current open:\n");
        for (Game game: list){
            System.out.println("\n" +
                    "Game: " + game.getGameName() + ",\n" +
                    "\tWhite player: " + game.getWhiteUsername() + ",\n" +
                    "\tBlack player: " + game.getBlackUsername() + ",\n" +
                    "\tGameID: " + game.getGameID() + "\n\n");
        }
    }

    private static void displaySuccessCreate(PrintStream out, Integer gameID) {
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.println("Awesome!! Your game has been created successfully!! You can join the game now!!\n" +
                "The GameID is " + gameID + "!!");
    }

    public static String displayCreate(PrintStream out){
        String gamename;
        Scanner scanner = new Scanner(System.in);
        out.println(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_YELLOW);
        System.out.printf("Give the name of the game you would like to create!%n >>>  ");

        gamename = scanner.next();
        return gamename;
    }

}

