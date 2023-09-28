package spell;

import java.util.List;

public class Node implements INode{
    public Node[] childNodes;
    private int count;

    Node() {
        count = 0;
        childNodes = new Node[26];
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
    public Node[] getChildren() {
        return childNodes;
    }
}
