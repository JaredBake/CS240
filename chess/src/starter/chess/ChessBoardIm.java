package chess;

import java.util.ArrayList;

public class ChessBoardIm implements ChessBoard{

    ArrayList<ArrayList<ChessPositionIm>> board = new ArrayList<ArrayList<ChessPositionIm>>();
    private ChessBoardIm(){

    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {

    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return null;
    }

    @Override
    public void resetBoard() {

    }
}
