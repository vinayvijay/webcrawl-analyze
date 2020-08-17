package org.txtana.vo;

import java.util.List;

public class RankResponse {

    private List<WordStats> words;

    private List<WordStats> wordPairs;

    public RankResponse(List<WordStats> words, List<WordStats> wordPairs) {
        this.words = words;
        this.wordPairs = wordPairs;
    }
    
    public List<WordStats> getWords() {
        return words;
    }

    public void setWords(final List<WordStats> words) {
        this.words = words;
    }

    public List<WordStats> getWordPairs() {
        return wordPairs;
    }

    public void setWordPairs(List<WordStats> wordPairs) {
        this.wordPairs = wordPairs;
    }

    @Override
    public String toString() {
        return "RankResponse : words -> "+words.toString()+ " wordPairs -> "+wordPairs.toString();
    }

}