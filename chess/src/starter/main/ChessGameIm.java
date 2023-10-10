package main;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChessGameIm implements ChessGame {
    TeamColor turn;
    ChessBoard board;
    ChessPosition King;
    private Set<ChessPosition> live_ones = new HashSet<>();
    private Set<ChessMove> attacking_moves = new HashSet<>();
    private Set<ChessMove> attacking_moves2 = new HashSet<>();


    @Override
    public TeamColor getTeamTurn() {

        return turn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }


    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        return board.getPiece(startPosition).pieceMoves(board, startPosition);
    }


    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }

    public void getAllPieces(ChessBoard b, ChessGame.TeamColor tc){
        ChessPosition p;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                p = new ChessPositionIm(i+1,j+1);
                if (b.getPiece(p) != null) {
                    live_ones.add(new ChessPositionIm(i+1,j+1));
                }
            }
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        Set<ChessMove> opposing_moves = new HashSet<>();
        ChessPosition king = new ChessPositionIm(1,1);
        ChessMove check;
        getAllPieces(board,teamColor);
        for (ChessPosition p : live_ones) {
            if (board.getPiece(p).getPieceType() == ChessPiece.PieceType.KING && teamColor == board.getPiece(p).getTeamColor()) {
                king = p;
                King = king;
            }
            if (teamColor != board.getPiece(p).getTeamColor()) {
                attacking_moves = new HashSet<>(board.getPiece(p).pieceMoves(board, p));
            }
        }
        opposing_moves = attacking_moves;
        for (ChessMove m: opposing_moves){
            if (m.getEndPosition().getRow() == king.getRow() && m.getEndPosition().getColumn() == king.getColumn()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessBoard b = new ChessBoardIm();
        Set<ChessPosition> enemies = new HashSet<>();
        Set<ChessMove> king_moves;
        if (!isInCheck(teamColor)){
            return false;
        }
        for (ChessPosition po: live_ones){
            b.addPiece(po,board.getPiece(po));
        }
        king_moves = new HashSet<>(board.getPiece(King).pieceMoves(board, King));
        for (ChessMove km: king_moves){
            if (attacking_moves.contains(km)){
                return false;
            }
            //  the King has been moved to a new position
            b.addPiece(km.getEndPosition(),b.getPiece(km.getStartPosition()));
            b.addPiece(km.getStartPosition(),null);
            // Check to see if the new position is being covered by an enemy
            getAllPieces(b,teamColor);
            for (ChessPosition p: live_ones){
                if (b.getPiece(p).getTeamColor() != teamColor){
                    enemies.add(p);
                }
            }
            for (ChessPosition ep: enemies){

            }
//               =
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
