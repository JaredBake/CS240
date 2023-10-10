package main;

import chess.*;

import java.util.Collection;

public class ChessGameIm implements ChessGame {
    TeamColor turn;
    ChessBoard board;

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

    @Override
    public boolean isInCheck(TeamColor teamColor) {

        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
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
