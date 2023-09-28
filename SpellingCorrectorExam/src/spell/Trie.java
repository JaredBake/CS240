package spell;

import java.awt.event.WindowStateListener;
import java.util.Objects;

public class Trie implements ITrie {

    public int word_count;
    public int node_count;
    public INode root = new Node();
    public int hash;
    public Trie(){
        root = this.root;
        word_count = 0;
        node_count  = 1;
        hash = 0;
    }


    @Override
    public void add(String word) {
        INode node = find(word);
        if (node != null){
            node.incrementValue();
        }else{
            node = root;
            int index = 0;
            for (int i = 0; i < word.length(); i++){
                index = word.charAt(i) - 'a';
                if (node.getChildren()[index] == null){
                    node_count++;
                    node.getChildren()[index] = new Node();
                }
                node = node.getChildren()[index];
            }
            word_count++;
            node.incrementValue();
        }

        ////////Hash Code ///////

        for (int i = 1; i < word.length(); i++){
            int c = word.charAt(i-1) - 'a';
            hash += i * c;
        }
    }

    @Override
    public INode find(String word) {
        int index;
        INode node = root;
        if (word != null) {
            for (int i = 0; i < word.length(); i++) {
                index = word.charAt(i) - 'a';
                if (node.getChildren()[index] == null) {
                    return null;
                }
                node = node.getChildren()[index];
            }
            if (node.getValue() > 0){
                return node;
            }
        }

        return null;
    }

    @Override
    public int getWordCount() {
        return word_count;
    }

    @Override
    public int getNodeCount() {
        return node_count;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        StringBuilder cur_word = new StringBuilder();

        toString_Helper(root, cur_word, output);

        return output.toString();
    }

    public StringBuilder toString_Helper(INode n, StringBuilder cur_word, StringBuilder output){
        if (n.getValue() > 0){
            output.append(cur_word.toString());
            output.append("\n");
        }
        for (int i = 0; i < n.getChildren().length; i++){
            if (n.getChildren()[i] != null){
                char c = (char) ('a' + i);
                cur_word.append(c);
                toString_Helper(n.getChildren()[i],cur_word, output );
                cur_word.deleteCharAt(cur_word.length() - 1);
            }
        }

        return cur_word;
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()){
            return false;
        }
        if (this.getWordCount() != ((Trie) o).getWordCount()){
            return false;
        }

        Trie t = (Trie) o;
        return equals_Helper(root, t.root);
    }

    public boolean equals_Helper(INode n1, INode n2){
        if (n1 == null && n2 == null){
            return true;
        }

        if (n1 == null ^ n2 == null){
            return false;
        }

        if (n1.getValue() != n2.getValue()){
            return false;
        }
        for (int i = 0; i < n1.getChildren().length; i++){
            if (n1.getChildren()[i] == null ^ n2.getChildren()[i] == null){
                return false;
            }
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
