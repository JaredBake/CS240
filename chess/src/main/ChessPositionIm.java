import chess.ChessPosition;

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
}
