import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class ChessBoardIm implements ChessBoard {

//    ArrayList<ArrayList<ChessPositionIm>> board = new ArrayList<ArrayList<ChessPositionIm>>();
    private ChessPiece[][] board;

    private ChessBoardIm(){
        board = new ChessPiece[8][8];
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    @Override
    public void resetBoard() {
        //TODO make all the empty positions not null
        ChessPositionIm position = new ChessPositionIm(1,1);
        board = new ChessPieceIm[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (i < 3 || i > 6){
                    position.setRow(i);
                    position.setColumn(j);
                    addPiece(position,board[i][j]);
                }

            }
        }
    }
}
