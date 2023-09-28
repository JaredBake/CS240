package spell;

public class Node implements INode{

    public INode[] children;
    public int count;

    public Node(){
        count = 0;
        children = new INode[26];
    }


    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
