package client;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ui.EscapeSequences.*;

public class TickTacToe {

    private static final int BOARD_SIZE_IN_SQUARES = 3;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";
    private static Random rand = new Random();


    public static void main(String[] args) throws IOException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        displayWhiteChessBoard(out);
        out.println(SET_TEXT_COLOR_DARK_GREY);
        displayBlackChessBoard(out);

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
            empty.set(0,"   " + i + "   ");
            empty.set(9,"   " + i + "   ");
            if (i > 2 && i < 7){
                drawRow(out, empty, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 8){
                whitePieces.set(0,"   " + i + "   ");
                whitePieces.set(9,"   " + i + "   ");
                drawRow(out, whitePieces, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 1){
                blackPieces.set(0,"   " + i + "   ");
                blackPieces.set(9,"   " + i + "   ");
                drawRow(out, blackPieces, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 2){
                pawns.set(0,"   " + i + "   ");
                pawns.set(9,"   " + i + "   ");
                drawRow(out, pawns, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 7){
                pawns.set(0,"   " + i + "   ");
                pawns.set(9,"   " + i + "   ");
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
                blackPieces.set(0,"   " + i + "   ");
                blackPieces.set(9,"   " + i + "   ");
                drawRow(out, blackPieces, SET_TEXT_COLOR_RED, startblack);
                startblack = !startblack;
            }else if (i == 8){
                whitePieces.set(0,"   " + i + "   ");
                whitePieces.set(9,"   " + i + "   ");
                drawRow(out, whitePieces, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 7){
                whitePawns.set(0,"   " + i + "   ");
                whitePawns.set(9,"   " + i + "   ");
                drawRow(out, whitePawns, SET_TEXT_COLOR_BLUE, startblack);
                startblack = !startblack;
            }else if (i == 2){
                blackPawns.set(0,"   " + i + "   ");
                blackPawns.set(9,"   " + i + "   ");
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

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawTicTacToeBoard(PrintStream out, String piece) throws IOException {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out, piece);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                drawVerticalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, String piece) throws IOException {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                setWhite(out);

                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength -1;

                    out.print(EMPTY.repeat(prefixLength));
;
                    String output;
                    if (piece == null){
                        output = "\u2003";
                    }else if (piece.equals("X")){
                        output = "\u2654";
                    }else if (piece.equals("O")){
                        output = "\u2658";
                    }else {
                        output = "\u2003";
                    }
                    printPlayer(out, output);
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }

                if (boardCol < BOARD_SIZE_IN_SQUARES-1) {
                    // Draw right line
                    setRed(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }

                setBlack(out);
            }

            out.println();
        }
    }

    private static void drawVerticalLine(PrintStream out) {

        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;

        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
            setBlue(out);
            out.print(EMPTY.repeat(boardSizeInSpaces));

            setRed(out);
            out.println();
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setBlue(PrintStream out){
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }
}

