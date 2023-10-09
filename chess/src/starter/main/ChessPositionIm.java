package main;

import chess.ChessPosition;


public class ChessPositionIm implements ChessPosition {
    private int row = 0;
    private int column = 0;

    public ChessPositionIm(int row, int column){
        this.row = row;
        this.column = column;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }
}
