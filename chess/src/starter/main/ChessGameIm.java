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
        Collection<ChessMove> valid_moves =  null;
        ChessPosition temp_position = startPosition;
        TeamColor pc = board.getPiece(startPosition).getTeamColor();


//        Checks to see if there is a valid piece in the start position
        if(board.getPiece(startPosition).getPieceType() != null){
            if (board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.BISHOP){
                bishopTR(temp_position,pc,valid_moves);
                bishopTL(temp_position,pc,valid_moves);
                bishopBR(temp_position,pc,valid_moves);
                bishopBL(temp_position,pc,valid_moves);
                return valid_moves;
            }
            if (board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.KNIGHT){

            }
            if (board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.QUEEN){

            }
            if (board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.PAWN){

            }
            if (board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.KING){

            }
            if (board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.ROOK){

            }
        }


        return null;
    }

    private boolean validMove(ChessPosition temp_position, TeamColor pc) {
        if (temp_position.getColumn() > 8 || temp_position.getRow() > 8){
            return false;
        }
        if (board.getPiece(temp_position).getTeamColor() == pc){
            return false;
        }
        return true;
    }

    private void bishopTR(ChessPosition temp_position, TeamColor pc, Collection<ChessMove> valid_moves){
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);

        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBR(ChessPosition temp_position, TeamColor pc, Collection<ChessMove> valid_moves){
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);

        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopTL(ChessPosition temp_position, TeamColor pc, Collection<ChessMove> valid_moves){
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()-1);

        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBL(ChessPosition temp_position, TeamColor pc, Collection<ChessMove> valid_moves){

        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1, temp_position.getColumn()-1);

        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
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
