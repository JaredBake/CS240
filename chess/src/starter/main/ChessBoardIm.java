package main;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessBoardIm implements ChessBoard {

//    ArrayList<ArrayList<ChessPositionIm>> board = new ArrayList<ArrayList<ChessPositionIm>>();
    private ChessPiece[][] board;

    public ChessBoardIm(){
        board = new ChessPiece[8][8];
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    public void firstadds(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }

    public void removePiece(ChessPosition position) {
//        resets the position at the spot to have a new empty piece on it
        board[position.getRow()-1][position.getColumn()-1] = new ChessPieceIm();
    }

    @Override
    public void resetBoard() {
        //TODO make all the empty positions not null
        ChessPositionIm position = new ChessPositionIm(1,1);
        board = new ChessPieceIm[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (i < 2 || i > 5){
                    ChessPieceIm p = new ChessPieceIm();
                    position.setRow(i);
                    position.setColumn(j);
                    p.setPieceType(position);
                    firstadds(position, p);
                }

            }
        }
    }
}
