package spell;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

    Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner the_scan = new Scanner(file);

        if (file.exists()){
            while(the_scan.hasNext()){
                trie.add(the_scan.next());
            }
        }else{
            throw new IOException("File doesn't exist");
        }


    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        ArrayList<String> the_words = new ArrayList<>();
        HashSet<String> master_set = new HashSet<>();
        inputWord = inputWord.toLowerCase();
        INode node = trie.find(inputWord);
        String suggested_word = null;
        if (node != null){
            return inputWord;
        }
        master_set.add(inputWord);
        getSimilarWord(master_set);

        suggested_word = errorDistance(master_set, the_words);
        if (suggested_word != null){
            return suggested_word;
        }
        getSimilarWord(master_set);
        suggested_word = errorDistance(master_set, the_words);


        return suggested_word;
    }
    public String errorDistance(HashSet<String> master_set, ArrayList<String> the_words){
        int maxcount = 1;
        int cur_count = 0;
        for (String word : master_set){
            INode node = trie.find(word);
            if (node != null){
                cur_count = node.getValue();
                if (cur_count >= maxcount){
                    maxcount = cur_count;
                    the_words.add(word);
                }
            }
        }
        Collections.sort(the_words,String.CASE_INSENSITIVE_ORDER);
        if (!the_words.isEmpty()){
            for (String chosen : the_words){
                INode the_node = trie.find(chosen);
                if (the_node.getValue() >= maxcount){
                    return chosen;
                }
            }
        }
        return null;
    }

    public void getSimilarWord(HashSet<String> master_set){
        ArrayList<String> possible_words = new ArrayList<>(master_set);
        for (String word : possible_words){
            deletion(master_set, word);
            transposition(master_set, word);
            alteration(master_set, word);
            insertion(master_set, word);
        }

    }

    public void deletion(HashSet<String> master_set, String word){
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < word.length(); i++){
            newString.append(word);
            for (int j = 0; j < 26; j++){
                newString.insert(i, (char )(j + 'a'));
                master_set.add(newString.toString());
                newString.deleteCharAt(i);
                if (i == word.length()-1){
                    newString.insert(i+1, (char )(j + 'a'));
                    master_set.add(newString.toString());
                    newString.deleteCharAt(i+1);
                }
            }
            newString.delete(0,newString.length());
        }

    }

    public void transposition(HashSet<String> master_set, String word){

        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < word.length()-1; i++){
            newString.append(word);
            char c = newString.charAt(i);
            newString.deleteCharAt(i);
            newString.insert(i+1,c);
            master_set.add(newString.toString());
            newString.delete(0,newString.length());
        }




    }

    public void alteration(HashSet<String> master_set, String word){
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < word.length(); i++){
            newString.append(word);
            for (int j = 0; j < 26; j++){
                newString.setCharAt(i, (char) (j + 'a'));
                master_set.add(newString.toString());
            }
            newString.delete(0, newString.length());
        }
    }

    public void insertion(HashSet<String> master_set, String word){
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < word.length(); i++){
            newString.append(word);
            newString.deleteCharAt(i);
            master_set.add(newString.toString());

            newString.delete(0, newString.length());
        }
    }


}
