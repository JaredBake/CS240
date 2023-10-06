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
}
