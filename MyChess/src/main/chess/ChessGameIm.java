package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChessGameIm implements ChessGame {
    TeamColor turn;
    ChessBoard board;
    ChessPosition King;

    @Override
    public TeamColor getTeamTurn() {

        return turn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }


    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Set<ChessMove> valid_moves = new HashSet<>();
        boolean piece_removed = false;
        if (board.getPiece(startPosition) == null){
            return valid_moves;
        }
        Collection<ChessMove> all_moves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        ChessPiece temp_piece = new ChessPieceIm();
        ChessPiece piece = board.getPiece(startPosition);
        TeamColor tc = piece.getTeamColor();
        // Go through each move and see if it will put me in check
        for (ChessMove am: all_moves){
//            if (isInCheck(tc)){
//                board.addPiece(am.getEndPosition(),board.getPiece(am.getStartPosition()));
//                board.addPiece(am.getStartPosition(),null);
//                if (!isInCheck(tc)){
//                    valid_moves.add(am);
//                }
//                board.addPiece(am.getStartPosition(),board.getPiece(am.getEndPosition()));
//                board.addPiece(am.getEndPosition(),null);
//            }
            piece_removed = false;
            if (board.getPiece(am.getEndPosition()) != null){
                temp_piece = board.getPiece(am.getEndPosition());
                piece_removed = true;
            }
            board.addPiece(am.getEndPosition(),board.getPiece(am.getStartPosition()));
            board.addPiece(am.getStartPosition(),null);
            if (!isInCheck(tc)){
                valid_moves.add(am);
            }
            board.addPiece(am.getStartPosition(),board.getPiece(am.getEndPosition()));
            board.addPiece(am.getEndPosition(),null);
            if (piece_removed){
                board.addPiece(am.getEndPosition(),temp_piece);
            }
        }
        return valid_moves;
    }


    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean promo = false;
        if (move.getPromotionPiece() != null){
            promo = true;
        }
        Collection<ChessMove> valid_moves = new HashSet<>();
        valid_moves = validMoves(move.getStartPosition());
        if (valid_moves.isEmpty()){
            throw new InvalidMoveException("There are no valid moves");
        }
        if (!valid_moves.contains(move)){
            throw new InvalidMoveException("Piece can't move there");
        }
        ChessPosition pos = move.getStartPosition();
        if (board.getPiece(move.getStartPosition()).getTeamColor() != turn){
            throw new InvalidMoveException("Not your ture stupid!");
        }
        // Check to see if the king is in danger
        board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));
        board.addPiece(move.getStartPosition(),null);
        pos = move.getEndPosition();
        if (promo){
            ChessPiece promoted = new ChessPieceIm(turn,move.getPromotionPiece());
            board.addPiece(move.getEndPosition(),promoted);
        }
        if (turn == TeamColor.WHITE){
            turn = TeamColor.BLACK;
        }else{
            turn = TeamColor.WHITE;
        }
        if (isInCheck(board.getPiece(pos).getTeamColor())){
            board.addPiece(move.getStartPosition(),board.getPiece(move.getEndPosition()));
            board.addPiece(move.getEndPosition(),null);
            throw new InvalidMoveException("Left king in check");
        }



    }

    public Collection<ChessMove> getPieceMoves(ChessBoard b, ChessPosition p){
        Collection<ChessMove> pm = new HashSet<>();
        pm = b.getPiece(p).pieceMoves(b, p);
        return pm;
    }

    public Set<ChessPosition> getAllPieces(ChessBoard b, ChessGame.TeamColor tc){
        Set<ChessPosition> live_ones = new HashSet<>();
        ChessPosition p;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                p = new ChessPositionIm(i+1,j+1);
                if (b.getPiece(p) != null) {
                    live_ones.add(new ChessPositionIm(i+1,j+1));
                }
            }
        }
        return live_ones;
    }

    public Set<ChessMove> getAttacking_moves(ChessBoard b, TeamColor tc){
        Set<ChessPosition> all_pieces;
        Set<ChessMove> attacking_moves = new HashSet<>();
        ChessPosition king = new ChessPositionIm(1,1);
        all_pieces = getAllPieces(b,tc);
        for (ChessPosition p : all_pieces) {
            if (b.getPiece(p).getPieceType() == ChessPiece.PieceType.KING && tc == b.getPiece(p).getTeamColor()) {
                King = p;
            }
            if (tc != b.getPiece(p).getTeamColor()) {
                attacking_moves.addAll(b.getPiece(p).pieceMoves(b, p));
            }
        }
        return attacking_moves;
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        Set<ChessMove> attacking_moves = new HashSet<>();
//        ChessPosition king = new ChessPositionIm(1,1);
        attacking_moves = getAttacking_moves(board,teamColor);
        for (ChessMove m: attacking_moves){
            if (King != null) {
                if (m.getEndPosition().getRow() == King.getRow() && m.getEndPosition().getColumn() == King.getColumn()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessBoard b = new ChessBoardIm();
        Set<ChessMove> enemy_moves = new HashSet<>();
        Collection<ChessMove> king_moves = new HashSet<>();
        if (!isInCheck(teamColor)){
            return false;
        }
//        enemy_moves = getAttacking_moves(board,teamColor);
        Set<ChessPosition> all_pieces = getAllPieces(board,teamColor);
        king_moves = getPieceMoves(board,King);
        // creat a new board for the future moves of the King in check
        for (ChessPosition p: all_pieces){
            b.addPiece(p,board.getPiece(p));
        }
        //  Make a new board for each move of the king and check to see if he is still in check
        for (ChessMove last_move: king_moves){
            b.addPiece(last_move.getEndPosition(),b.getPiece(last_move.getStartPosition()));
            b.addPiece(last_move.getStartPosition(),null);
            enemy_moves = getAttacking_moves(b,teamColor);
            for (ChessMove m: enemy_moves){
                if (m.getEndPosition().getColumn() == last_move.getEndPosition().getColumn()
                        && m.getEndPosition().getRow() == last_move.getEndPosition().getRow()){
                    return true;
                }
            }
            b.addPiece(last_move.getStartPosition(),b.getPiece(last_move.getEndPosition()));
            b.addPiece(last_move.getEndPosition(), null);
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        Set<ChessMove> all_pieces = getAttacking_moves(board,teamColor);
        Collection<ChessMove> valid_moves = new HashSet<>();

        valid_moves = validMoves(King);
        if (valid_moves.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
