package main;

import chess.*;

import java.util.Collection;

public class ChessPieceIm implements ChessPiece {

    private ChessGame.TeamColor teamColor;
    private PieceType type;
    private ChessPositionIm position = new ChessPositionIm(1,1);
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public void setPieceType(ChessPosition position){
        int row = position.getRow();
        int col = position.getColumn();
        if (row == 0){
            teamColor = ChessGame.TeamColor.BLACK;
            if (col == 0 || col == 7){
                type = PieceType.ROOK;
            }
            if (col == 1 || col == 6){
                type = PieceType.KNIGHT;
            }
            if (col == 2 || col == 5){
                type = PieceType.BISHOP;
            }
            if (col == 3){
                type = PieceType.KING;
            }
            if (col == 4){
                type = PieceType.QUEEN;
            }
        }
        if (row == 7){
            teamColor = ChessGame.TeamColor.WHITE;
            if (col == 0 || col == 7){
                type = PieceType.ROOK;
            }
            if (col == 1 || col == 6){
                type = PieceType.KNIGHT;
            }
            if (col == 2 || col == 5){
                type = PieceType.BISHOP;
            }
            if (col == 3){
                type = PieceType.QUEEN;
            }
            if (col == 4){
                type = PieceType.KING;
            }
        }
        if (row == 1){
            teamColor = ChessGame.TeamColor.BLACK;
            type = PieceType.PAWN;
        }else if (row == 6){
            teamColor = ChessGame.TeamColor.BLACK;
            type = PieceType.PAWN;
        }
    }
}
