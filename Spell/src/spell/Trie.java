package spell;

import java.io.OptionalDataException;
import java.util.Objects;

public class Trie implements ITrie{

    public Node root;

    public Trie(){
        root = new Node();
        word_count = 0;
        node_count = 1;
        hash = 0;
    }
    int hash;
    public int word_count;
    public int node_count;

    @Override
    public void add(String word) {
        INode node = find(word);    // This checks to see if the word is in the trie
                                    // if the word is found it increments the value or count and then ends
        if (node != null) {
            node.incrementValue();
        }else{
            // if the word is not found it needs to be added to the trie at the root
            // and then the value nodecount and wordcount are incremented accordingly
            node = root;
            word = word.toLowerCase();
            var index = 0;
            for (var i = 0; i < word.length(); i++){
                index = word.charAt(i) - 'a';
                if (node.getChildren()[index] == null) {    // checks to see if the node at that index exists
                                                            // if it does then it doesn't increase the nodecount
                    node_count++;
                    node.getChildren()[index] = new Node();
                }
                ///////////hash code///////// this just gives a unique number to each word
                hash += index * (i+1);
                node = node.getChildren()[index];
            }
            node.incrementValue();
            word_count++;
        }


    }


    @Override
    public INode find(String word) {
        Node node = root;
        word = word.toLowerCase();
        char c;
        int index;
        for (int i = 0; i < word.length(); i++)
        {
            c = word.charAt(i);
            index = c - 'a';
            //if the root node doesnt have the character then return nothing
            if (node.getChildren()[index] == null)
            {
                return null;
            }
            node = node.getChildren()[index];
        }
        if (node.getValue() == 0) return null;
        else return node;
    }

    @Override
    public int getWordCount() {
        return word_count;
    }

    @Override
    public int getNodeCount() {
        return node_count;
    }

    public String toString(){   // when looking to see if the tries are equal it goes here first to print out the trie
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toString_Helper(root, curWord, output);
        return output.toString();

    }

    private void toString_Helper(Node n, StringBuilder curWord, StringBuilder output){

        if (n.getValue() > 0){
            output.append(curWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < n.getChildren().length; ++i){
            Node child = n.getChildren()[i];
            if (child != null){

                char child_letter = (char)('a' + i);
                curWord.append(child_letter);
                toString_Helper(child, curWord, output);

                curWord.deleteCharAt(curWord.length()-1);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o.getClass() != getClass()){
            return false;
        }
        if (this == o){
            return true;
        }
        Trie t = (Trie) o;

        // Find out if the obect passed through is a Trie and if it contains the same words in it
        return equals_Helper(this.root, t.root);
    }

    private boolean equals_Helper(Node n1, Node n2){
        // Need to check if the node we are at is null and if they both are null
        if (n1 == null && n2 == null){
            return true;
        }
        // if only one is null than return false
        if (n1 == null ^ n2 == null){
            return false;
        }
        // The counts need to be the same
        if (n1.getValue() != n2.getValue()){
            return false;
        }
        // Recurse on the children and compare child subtrees
        for (int i = 0; i < 26; i++){
            if (n1.getChildren()[i] == null ^ n2.getChildren()[i] == null){
                return false;
            }
            // This fixed the code for me when I added in the if statment
            if (!equals_Helper(n1.getChildren()[i],n2.getChildren()[i])){
                return false;
            }
        }

        return true;
    }
    @Override
    public int hashCode() {
        return hash;
    }



}
