package chess;

import java.util.Objects;


public class ChessPositionIm implements ChessPosition {
    private int row = 0;
    private int column = 0;

    public ChessPositionIm(int row, int column){
        this.row = row;
        this.column = column;
    }

    public void setRow(int r){
        row = r;
    }

    @Override
    public void setColoumn(int c) {

    }

    @Override
    public int getRow() {
        return row;
    }


    public void setColumn(int c){
        column = c;
    }
    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPositionIm that = (ChessPositionIm) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
