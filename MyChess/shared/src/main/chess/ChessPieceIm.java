package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChessPieceIm implements ChessPiece {
    private boolean promotion = false;
    private boolean blocked_piece = false;
    private Set<ChessPosition> live_ones = new HashSet<>();
    private ChessGame.TeamColor teamColor;
    private ChessPiece.PieceType type;
    private ChessPositionIm position = new ChessPositionIm(1,1);
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
        Set<ChessMove> valid_moves =  new HashSet<>();
        ChessPosition temp_position = myPosition;
        ChessGame.TeamColor pc = board.getPiece(myPosition).getTeamColor();
        setPosition(myPosition);

//        Checks to see if there is a valid piece in the start position
        if(board.getPiece(myPosition).getPieceType() != null){
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.BISHOP){
                bishopTR(temp_position,pc,valid_moves, board);
                bishopTL(temp_position,pc,valid_moves, board);
                bishopBR(temp_position,pc,valid_moves, board);
                bishopBL(temp_position,pc,valid_moves, board);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT){
                knightMove(temp_position,pc,valid_moves, board);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN){
                queenD(temp_position,pc,valid_moves, board);
                queenL(temp_position,pc,valid_moves, board);
                queenR(temp_position,pc,valid_moves, board);
                queenU(temp_position,pc,valid_moves, board);
                queenTR(temp_position,pc,valid_moves, board);
                queenTL(temp_position,pc,valid_moves, board);
                queenBR(temp_position,pc,valid_moves, board);
                queenBL(temp_position,pc,valid_moves, board);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN){
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
                    blackPawnMove(temp_position,pc,valid_moves, board);
                }else{
                    whitePawnMove(temp_position,pc,valid_moves, board);
                }

                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING){
                kingMove(temp_position,pc,valid_moves, board);
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK){
                rookD(temp_position,pc,valid_moves, board);
                rookL(temp_position,pc,valid_moves, board);
                rookR(temp_position,pc,valid_moves, board);
                rookU(temp_position,pc,valid_moves, board);
                return valid_moves;
            }
        }
        return null;
    }

    private boolean validMove(ChessPosition temp_position, ChessGame.TeamColor pc, ChessBoard the_board) {

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

    private void bishopTR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves,ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc,board)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position, pc ,board)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopTL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()-1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc, board)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1 && temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn() - 1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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

    private void kingMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn());
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn());
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow(),temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow(),temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
    }

    private void knightMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+2,temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+2,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-2,temp_position.getColumn()+1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-2,temp_position.getColumn()-1);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()+2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn()-2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()+2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn()-2);
        move = new ChessMoveIm(temp_position, end_position, null);
        if (validMove(end_position,pc, board)){
            valid_moves.add(move);
        }
    }

    private void queenU(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getRow()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() + 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc,board)) {
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
    private void queenD(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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
    private void queenL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()-1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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
    private void queenR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getColumn()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()+1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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
    private void queenTR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc, board)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void queenBR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc, board)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void queenTL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()-1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc, board)){
            valid_moves.add(move);
            if (blocked_piece){
                break;
            }
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void queenBL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1 && temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn() - 1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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

    private void blackPawnMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn());
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);

        if (temp_position.getRow() == 7) {
            if (pawnMove(end_position, pc, board)) {
                valid_moves.add(move);
                end_position = new ChessPositionIm(temp_position.getRow() - 2, temp_position.getColumn());
                valid_moves.add(move);
                if (pawnMove(end_position, pc, board)) {
                    move = new ChessMoveIm(temp_position, end_position, null);
                    valid_moves.add(move);
                }
            }
        }

        end_position = new ChessPositionIm(temp_position.getRow()-1,temp_position.getColumn());
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnMove(end_position,pc, board)){
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
        if (pawnAttack(end_position,pc, board)){
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
        if (pawnAttack(end_position,pc, board)){
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
    private void whitePawnMove(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){

        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn());
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);

        if (temp_position.getRow() == 2) {
            if (pawnMove(end_position, pc, board)) {
                end_position = new ChessPositionIm(temp_position.getRow() + 2, temp_position.getColumn());
                valid_moves.add(move);
                if (pawnMove(end_position, pc, board)) {
                    move = new ChessMoveIm(temp_position, end_position, null);
                    valid_moves.add(move);
                }
            }
        }

        end_position = new ChessPositionIm(temp_position.getRow()+1,temp_position.getColumn());
        move = new ChessMoveIm(temp_position, end_position, null);
        if (pawnMove(end_position,pc, board)){
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
        if (pawnAttack(end_position,pc, board)){
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
        if (pawnAttack(end_position,pc, board)){
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

    private void rookU(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getRow()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() + 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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
    private void rookD(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getRow()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn());
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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
    private void rookL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()-1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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
    private void rookR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves, ChessBoard board){
        blocked_piece = false;
        if (temp_position.getColumn()+1 <= 8) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow(), temp_position.getColumn()+1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc, board)) {
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

    private boolean pawnAttack(ChessPosition temp_position, ChessGame.TeamColor pc, ChessBoard board){
        if (temp_position.getColumn() > 8 || temp_position.getRow() > 8){
            return false;
        }
        if (temp_position.getColumn() < 1 || temp_position.getRow() < 1){
            return false;
        }
        if (board.getPiece(temp_position) == null){
            return false;
        }
        if (board.getPiece(temp_position).getTeamColor() == pc){
            return false;
        }
        if (temp_position.getRow() == 8 || temp_position.getRow() == 1){
            promotion = true;
        }
        return true;
    }
    private boolean pawnMove(ChessPosition temp_position, ChessGame.TeamColor pc, ChessBoard board){
        if (temp_position.getColumn() > 8 || temp_position.getRow() > 8){
            return false;
        }
        if (temp_position.getColumn() < 1 || temp_position.getRow() < 1){
            return false;
        }
        if (board.getPiece(temp_position) == null){
            if (temp_position.getRow() == 8 || temp_position.getRow() == 1){
                promotion = true;
            }
            return true;
        }
        if (board.getPiece(temp_position).getTeamColor() == ChessGame.TeamColor.WHITE
                || board.getPiece(temp_position).getTeamColor() == ChessGame.TeamColor.BLACK){
            return false;
        }
        if (temp_position.getRow() == 8 || temp_position.getRow() == 1){
            promotion = true;
        }
        return true;
    }

    public void setPosition(ChessPosition p){
        position = (ChessPositionIm) p;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPieceIm that = (ChessPieceIm) o;
        return blocked_piece == that.blocked_piece && teamColor == that.teamColor && type == that.type && Objects.equals(position, that.position);
    }

    public ChessPosition getPosition(){
        return position;
    }
    @Override
    public int hashCode() {
        return Objects.hash(blocked_piece, teamColor, type, position);
    }


    public void getAllPieces(ChessBoard b, ChessGame.TeamColor tc){
        ChessPosition p;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                p = new ChessPositionIm(i,j);
                if (b.getPiece(p) != null) {
                    live_ones.add(new ChessPositionIm(i+1,j+1));
                }
            }
        }
    }
    public boolean isInCheck(ChessBoard b, ChessGame.TeamColor tc) {
        Set<ChessMove> opposing_moves = new HashSet<>();
        ChessPosition king = new ChessPositionIm(1,1);
        getAllPieces(b,tc);
        for (ChessPosition p : live_ones){
            if (b.getPiece(p).getPieceType() == PieceType.KING && tc == b.getPiece(p).getTeamColor()){
                king = p;
            }
            if (tc != b.getPiece(p).getTeamColor()){
                opposing_moves = new HashSet<>(pieceMoves(b, p));;
            }
        }
        if (opposing_moves.contains(king)){
            return true;
        }


        return false;
    }

}
