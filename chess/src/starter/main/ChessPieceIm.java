package main;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChessPieceIm implements ChessPiece {
    private boolean in_check = false;
    private boolean in_check_mate = false;
    private boolean in_stale_mate = false;
    private boolean promotion = false;
    private boolean blocked_piece = false;
    private 
    private ChessBoard the_board = new ChessBoardIm();
    private ChessGame.TeamColor teamColor;
    private PieceType type;
    private ChessPosition position = new ChessPositionIm(1,1);

    public ChessPieceIm(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        teamColor = pieceColor;
        this.type = type;
    }

    public ChessPieceIm(){

    }

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
        ChessPosition pos = new ChessPositionIm(7,4);
        the_board = board;
        Set<ChessMove> valid_moves =  new HashSet<>();
        ChessPosition temp_position = myPosition;
        ChessGame.TeamColor pc = board.getPiece(myPosition).getTeamColor();
        setPosition(myPosition);

//        Checks to see if there is a valid piece in the start position
        if(board.getPiece(myPosition).getPieceType() != null){
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.BISHOP){
                bishopTR(temp_position,pc,valid_moves);
                bishopTL(temp_position,pc,valid_moves);
                bishopBR(temp_position,pc,valid_moves);
                bishopBL(temp_position,pc,valid_moves);
                if(valid_moves.contains(new ChessMoveIm(myPosition,pos,null))){
                    System.out.println("Has the captured piece in it");
                }
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT){
                knightMove(temp_position,pc,valid_moves);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN){
                queenD(temp_position,pc,valid_moves);
                queenL(temp_position,pc,valid_moves);
                queenR(temp_position,pc,valid_moves);
                queenU(temp_position,pc,valid_moves);
                queenTR(temp_position,pc,valid_moves);
                queenTL(temp_position,pc,valid_moves);
                queenBR(temp_position,pc,valid_moves);
                queenBL(temp_position,pc,valid_moves);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN){
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
                    blackPawnMove(temp_position,pc,valid_moves);
                }else{
                    whitePawnMove(temp_position,pc,valid_moves);
                }

                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING){
                kingMove(temp_position,pc,valid_moves);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK){
                rookD(temp_position,pc,valid_moves);
                rookL(temp_position,pc,valid_moves);
                rookR(temp_position,pc,valid_moves);
                rookU(temp_position,pc,valid_moves);
                return valid_moves;
            }
        }
        return null;
    }

    private boolean validMove(ChessPosition temp_position, ChessGame.TeamColor pc) {

        if (temp_position.getColumn() > 8 || temp_position.getRow() > 8){
            return false;
        }
        if (temp_position.getColumn() < 1 || temp_position.getRow() < 1){
            return false;
        }
        if (the_board.getPiece(temp_position) == null){
            return true;
        }
        if (the_board.getPiece(temp_position).getTeamColor() == pc){
            blocked_piece = true;
            return false;
        }
        blocked_piece = true;
        return true;
    }

    private void bishopTR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopTL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()-1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1 && temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn() - 1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece){
                    break;
                }
                if (end_position.getRow()-1 >= 1 && end_position.getColumn()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow() - 1, end_position.getColumn() - 1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }else {break;}
            }
        }
    }

    private void kingMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn());
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn());
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow(),temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow(),temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
    }

    private void knightMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+2,temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+2,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-2,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-2,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()+2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()-2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()+2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()-2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc)){
            valid_moves.add(move);
        }
    }

    private void queenU(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getRow()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() + 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getRow()+1 <= 8) {
                    end_position = new ChessPositionIm(end_position.getRow() + 1, end_position.getColumn());
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void queenD(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getRow()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow() - 1, end_position.getColumn());
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void queenL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()-1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getColumn()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow(), end_position.getColumn()-1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void queenR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getColumn()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()+1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getColumn()+1 <= 8) {
                    end_position = new ChessPositionIm(end_position.getRow(), end_position.getColumn()+1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void queenTR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void queenBR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void queenTL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()-1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void queenBL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1 && temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn() - 1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece){
                    break;
                }
                if (end_position.getRow()-1 >= 1 && end_position.getColumn()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow() - 1, end_position.getColumn() - 1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }else {break;}
            }
        }
    }

    private void blackPawnMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn());
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);

        if (temp_position.getRow() == 7) {
            if (pawnMove(end_position, pc)) {
                valid_moves.add(move);
                end_position = new ChessPositionIm(temp_position.getRow() - 2, temp_position.getColumn());
                valid_moves.add(move);
                if (pawnMove(end_position, pc)) {
                    move = new ChessMoveIm(temp_position, end_position, null);
                    valid_moves.add(move);
                }
            }
        }

        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn());
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnMove(end_position,pc)){
            if (promotion){
                move = new ChessMoveIm(temp_position,end_position,PieceType.QUEEN);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.KNIGHT);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.ROOK);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.BISHOP);
            }
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnAttack(end_position,pc)){
            if (promotion){
                move = new ChessMoveIm(temp_position,end_position,PieceType.QUEEN);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.KNIGHT);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.ROOK);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.BISHOP);
            }
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnAttack(end_position,pc)){
            if (promotion){
                move = new ChessMoveIm(temp_position,end_position,PieceType.QUEEN);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.KNIGHT);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.ROOK);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.BISHOP);
            }
            valid_moves.add(move);
        }
    }
    private void whitePawnMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){

        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn());
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);

        if (temp_position.getRow() == 2) {
            if (pawnMove(end_position, pc)) {
                end_position = new ChessPositionIm(temp_position.getRow() + 2, temp_position.getColumn());
                valid_moves.add(move);
                if (pawnMove(end_position, pc)) {
                    move = new ChessMoveIm(temp_position, end_position, null);
                    valid_moves.add(move);
                }
            }
        }

        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn());
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnMove(end_position,pc)){
            if (promotion){
                move = new ChessMoveIm(temp_position,end_position,PieceType.QUEEN);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.KNIGHT);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.ROOK);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.BISHOP);
            }
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnAttack(end_position,pc)){
            if (promotion){
                move = new ChessMoveIm(temp_position,end_position,PieceType.QUEEN);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.KNIGHT);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.ROOK);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.BISHOP);
            }
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnAttack(end_position,pc)){
            if (promotion){
                move = new ChessMoveIm(temp_position,end_position,PieceType.QUEEN);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.KNIGHT);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.ROOK);
                valid_moves.add(move);
                move = new ChessMoveIm(temp_position,end_position,PieceType.BISHOP);
            }
            valid_moves.add(move);
        }
    }

    private void rookU(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getRow()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() + 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getRow()+1 <= 8) {
                    end_position = new ChessPositionIm(end_position.getRow() + 1, end_position.getColumn());
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void rookD(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getRow()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow() - 1, end_position.getColumn());
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void rookL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()-1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getColumn()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow(), end_position.getColumn()-1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }
    private void rookR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        blocked_piece = false;
        if (temp_position.getColumn()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()+1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (blocked_piece) {
                    break;
                }
                if (temp_position.getColumn()+1 <= 8) {
                    end_position = new ChessPositionIm(end_position.getRow(), end_position.getColumn()+1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }
            }
        }
    }

    public void setPieceType(ChessPosition position){
        int row = position.getRow();
        int col = position.getColumn();
        if (row == 0){
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
        if (row == 7){
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
                type = PieceType.QUEEN;
            }
            if (col == 4){
                type = PieceType.KING;
            }
        }
        if (row == 1){
            teamColor = ChessGame.TeamColor.WHITE;
            type = PieceType.PAWN;
        }else if (row == 6){
            teamColor = ChessGame.TeamColor.BLACK;
            type = PieceType.PAWN;
        }
    }

    private boolean pawnAttack(ChessPosition temp_position, ChessGame.TeamColor pc){
        if (temp_position.getColumn() > 8 || temp_position.getRow() > 8){
            return false;
        }
        if (temp_position.getColumn() < 1 || temp_position.getRow() < 1){
            return false;
        }
        if (the_board.getPiece(temp_position) == null){
            return false;
        }
        if (the_board.getPiece(temp_position).getTeamColor() == pc){
            return false;
        }
        if (temp_position.getRow() == 8 || temp_position.getRow() == 1){
            promotion = true;
        }
        return true;
    }
    private boolean pawnMove(ChessPosition temp_position, ChessGame.TeamColor pc){
        if (temp_position.getColumn() > 8 || temp_position.getRow() > 8){
            return false;
        }
        if (temp_position.getColumn() < 1 || temp_position.getRow() < 1){
            return false;
        }
        if (the_board.getPiece(temp_position) == null){
            if (temp_position.getRow() == 8 || temp_position.getRow() == 1){
                promotion = true;
            }
            return true;
        }
        if (the_board.getPiece(temp_position).getTeamColor() == ChessGame.TeamColor.WHITE
                || the_board.getPiece(temp_position).getTeamColor() == ChessGame.TeamColor.BLACK){
            return false;
        }
        if (temp_position.getRow() == 8 || temp_position.getRow() == 1){
            promotion = true;
        }
        return true;
    }

    public void setPosition(ChessPosition p){
        position = p;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPieceIm that = (ChessPieceIm) o;
        return blocked_piece == that.blocked_piece && Objects.equals(the_board, that.the_board) && teamColor == that.teamColor && type == that.type && Objects.equals(position, that.position);
    }

    public ChessPosition getPosition(){
        return position;
    }
    @Override
    public int hashCode() {
        return Objects.hash(blocked_piece, the_board, teamColor, type, position);
    }


    public void getAllPieces(ChessBoard b, ChessGame.TeamColor tc){
        Set<ChessPosition> live_ones = new HashSet<>();
        ChessPosition k;
        ChessPosition p;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                p = new ChessPositionIm(i,j);
                if (b.getPiece(p) != null) {
                    if (b.getPiece(p).getTeamColor() == tc && b.getPiece(p).getPieceType() == PieceType.KING) {
                        k = new ChessPositionIm(i,j);
                    }
                    live_ones.add(new ChessPositionIm(i,j));
                }
            }
        }
    }
    public boolean isInCheck(ChessBoard b, ChessGame.TeamColor tc) {
        getAllPieces(b,tc);

        return false;
    }


    public boolean isInCheckmate(ChessBoard b, ChessGame.TeamColor tc) {
        return false;
    }


    public boolean isInStalemate(ChessBoard b, ChessGame.TeamColor tc) {
        return false;
    }
}
