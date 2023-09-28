package spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

    public String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Trie trie = new Trie();
    public String similar_word = null;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner the_file = new Scanner(file);
        if (file.exists()){
            while(the_file.hasNext()){
                trie.add(the_file.next());
            }
            return;
        }
        throw new IOException("File doesn't exist");
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        INode found = trie.find(inputWord);         // This is just to see if the word is in the trie if it is all lowercase
        if (found != null){
            return inputWord;
        }

        HashSet<String> similar_words_set = new HashSet<>();            // main set that has all the possible words in it
        ArrayList<String> lowercase_words = new ArrayList<>();          // I use this list so I can get all the words to lowercase in case I missed one

        int maxcount = 1;
        int currcount = 1;

        similar_words_set.add(inputWord);
        ArrayList<INode> the_words = new ArrayList<>();             // LIst of nodes so I can find the count of each of the words in the trie
        ArrayList<String> words = new ArrayList<>();                // I could put these two in a map but since I don't know the syntax well I just made two seperate lists that work with the same index

        getSmiliarWords(similar_words_set);                         // This call is the error distance of one
        for (String s : similar_words_set) {   // After getting back the list of words from the set I order them alphabetecly and then make them all lowercase
            lowercase_words.add(s.toLowerCase());
        }
        Collections.sort(lowercase_words, String.CASE_INSENSITIVE_ORDER);
        // This tries to see if any of the suggested words are in the trie and puts them all in a list that should be ordered already
        for (String this_one : lowercase_words){
            INode node = trie.find(this_one);
            if (node != null){
                currcount = node.getValue();
                if (currcount >= maxcount){
                    maxcount = currcount;
                    the_words.add(node);
                    words.add(this_one);
                }
            }
        }
        // looks for the word that has the highest count and is alphabeticly first
        if (!the_words.isEmpty()){
            for (int i = 0; i < the_words.size(); i++){
                if (maxcount == the_words.get(i).getValue()){
                    String suggestion = "";
                    suggestion = words.get(i);
                    return suggestion;
                }
            }
        }
        // Probably not important but just makes sure that these are empty so we don't have any extra words
        the_words.clear();
        words.clear();

        // I could put this in a function so that I don't have to call it twice, reduces error chance
        getSmiliarWords(similar_words_set);
        for (String s : (Collection<String>) similar_words_set) {
            lowercase_words.add(s.toLowerCase());
        }
        Collections.sort(lowercase_words, String.CASE_INSENSITIVE_ORDER);
        for (String look : lowercase_words){
            INode node = trie.find(look);
            if (node != null){
                currcount = node.getValue();
                if (currcount >= maxcount){
                    maxcount = currcount;
                    the_words.add(node);
                    words.add(look);
                }
            }
        }
        if (!the_words.isEmpty()){
            for (int i = 0; i < the_words.size(); i++){
                if (maxcount == the_words.get(i).getValue()){
                    String suggestion = words.get(i);
                    return suggestion;
                }
            }
        }

        // if no words match return null
        return null;
    }

    public void getSmiliarWords(HashSet<String> similar_words_set){
            ArrayList<String> temp = new ArrayList<>();
            // I needed to place this list here because if I use a set or the original set then it would constantly update the list and mess up the for loop
            temp.addAll(similar_words_set);
        for (String word : temp) {
            deletionDistance(similar_words_set, word);
            transpositionDistance(similar_words_set, word);
            alterationDistance(similar_words_set, word);
            insertionDistance(similar_words_set, word);
        }
    }

    public void deletionDistance(HashSet<String> similar_words_set, String inputWord){
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < inputWord.length(); i++){
            for (int j = 0; j < alphabet.length(); j++) {
                newString.append(inputWord);
                newString.insert(i, alphabet.charAt(j));
                similar_words_set.add(newString.toString());
                newString.delete(0, newString.length());
                if (i == inputWord.length()-1){
                    newString.append(inputWord);
                    newString.insert(i+1, alphabet.charAt(j));
                    similar_words_set.add(newString.toString());
                    newString.delete(0, newString.length());
                }
            }
        }
    }

    public void transpositionDistance(HashSet<String> similar_words_set, String inputWord){
        StringBuilder newString = new StringBuilder();
        char chari;
        char charj;
        for (int i = 0; i < inputWord.length(); i++){
            chari = inputWord.charAt(i);
            for (int j = 0; j < inputWord.length(); j++){
                charj = inputWord.charAt(j);
                newString.append(inputWord);
                newString.setCharAt(i,charj);
                newString.setCharAt(j,chari);
                similar_words_set.add(newString.toString());
                newString.delete(0,newString.length());
            }
        }
    }

    public void alterationDistance(HashSet<String> similar_words_set, String inputWord){
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < inputWord.length(); i++) {
            for (int j = 0; j < alphabet.length(); j++){
                newString.append(inputWord);
                newString.setCharAt(i,alphabet.charAt(j));
                similar_words_set.add(newString.toString());
                newString.delete(0, newString.length());
            }
        }
    }

    public void insertionDistance(HashSet<String> similar_words_set, String inputWord){
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < inputWord.length(); i++){
            newString.append(inputWord);
            newString.deleteCharAt(i);
            similar_words_set.add(newString.toString());
            newString.delete(0,newString.length());
        }
    }
}
