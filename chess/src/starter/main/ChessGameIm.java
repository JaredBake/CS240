package main;

import chess.*;

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
        Collection<ChessMove> all_moves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Set<ChessMove> valid_moves = new HashSet<>();
        ChessPiece piece = board.getPiece(startPosition);
        TeamColor tc = piece.getTeamColor();
        // Go through each move and see if it will put me in check
        for (ChessMove am: all_moves){
            board.addPiece(am.getEndPosition(),board.getPiece(am.getStartPosition()));
            board.addPiece(am.getStartPosition(),null);
            if (!isInCheck(tc)){
                valid_moves.add(am);
            }
            board.addPiece(am.getStartPosition(),board.getPiece(am.getEndPosition()));
            board.addPiece(am.getEndPosition(),null);
        }
        return valid_moves;
    }


    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPosition pos = move.getStartPosition();
        // Check to see if the king is in danger
        if (isInCheck(board.getPiece(pos).getTeamColor())){
            throw new InvalidMoveException("King is in Check");
        }
        board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));
        board.addPiece(move.getStartPosition(),null);
        pos = move.getEndPosition();
        if (isInCheck(board.getPiece(pos).getTeamColor())){
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
                king = p;
                King = king;
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
        ChessPosition king = new ChessPositionIm(1,1);
        attacking_moves = getAttacking_moves(board,teamColor);
        king = King;
        for (ChessMove m: attacking_moves){
            if (m.getEndPosition().getRow() == king.getRow() && m.getEndPosition().getColumn() == king.getColumn()){
                return true;
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
