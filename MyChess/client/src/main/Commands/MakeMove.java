package Commands;

public class MakeMove {

    private String posistion;
    private String move;
    public MakeMove(String position, String move){
        this.posistion = position;
        this.move = move;
    }

    public String getMove() {
        return move;
    }
    public String getPosistion(){
        return posistion;
    }
}
