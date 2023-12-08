package chess;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMoveIm that = (ChessMoveIm) o;
        return Objects.equals(start_pos, that.start_pos) && Objects.equals(end_pos, that.end_pos) && promo_piece == that.promo_piece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start_pos, end_pos, promo_piece);
    }
}
