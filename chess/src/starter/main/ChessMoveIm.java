package main;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessMoveIm implements ChessMove {
    private ChessPosition start_pos;
    private ChessPosition end_pos;
    private ChessPiece.PieceType promo_piece;


    public ChessMoveIm(ChessPosition s, ChessPosition e, ChessPiece.PieceType promo){
        start_pos = s;
        end_pos = e;
        promo_piece = promo;
    }
    @Override
    public ChessPosition getStartPosition() {
        return start_pos;
    }

    @Override
    public ChessPosition getEndPosition() {
        return end_pos;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promo_piece;
    }
}
