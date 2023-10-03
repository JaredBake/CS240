package chess;

public class ChessPositionIm implements ChessPosition{
    private int row = 0;
    private char column = 0;
    public void setRow(int r){
        row = r;
    }
    @Override
    public int getRow() {
        return row;
    }

    public void setColumn(char c){
        column = c;
    }
    @Override
    public int getColumn() {
        return column;
    }
}
