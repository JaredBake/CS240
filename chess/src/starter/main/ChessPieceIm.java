package main;

import chess.*;

import java.util.Collection;
import java.util.Iterator;

public class ChessPieceIm implements ChessPiece {

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
        the_board = board;
        Collection<ChessMove> valid_moves =  new Collection<ChessMove>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<ChessMove> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(ChessMove chessMove) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends ChessMove> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
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
                return valid_moves;
            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT){

            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN){

            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN){

            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING){

            }
            if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK){

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
            return false;
        }
        return true;
    }

    private void bishopTR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){

        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBR(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){

        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()+1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()-1, end_position.getColumn()+1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopTL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){

        ChessPosition end_position = new ChessPositionIm(temp_position.getRow()+1, temp_position.getColumn()-1);
        ChessMove move = new ChessMoveIm(temp_position, end_position, null);
        while(validMove(end_position,pc)){
            valid_moves.add(move);
            end_position = new ChessPositionIm(end_position.getRow()+1, end_position.getColumn()-1);
            move = new ChessMoveIm(temp_position, end_position, null);
        }
    }
    private void bishopBL(ChessPosition temp_position, ChessGame.TeamColor pc, Collection<ChessMove> valid_moves){
        if (temp_position.getRow()-1 >= 1 && temp_position.getColumn()-1 >= 1) {
            ChessPosition end_position = new ChessPositionIm(temp_position.getRow() - 1, temp_position.getColumn() - 1);
            ChessMove move = new ChessMoveIm(temp_position, end_position, null);
            while (validMove(end_position, pc)) {
                valid_moves.add(move);
                if (end_position.getRow()-1 >= 1 && end_position.getColumn()-1 >= 1) {
                    end_position = new ChessPositionIm(end_position.getRow() - 1, end_position.getColumn() - 1);
                    move = new ChessMoveIm(temp_position, end_position, null);
                }else {break;}
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

    public void setPosition(ChessPosition p){
        position = p;
    }
}
