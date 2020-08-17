package org.txtana.rank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import org.txtana.vo.WordStats;

public class Ranker {
    
    public static List<WordStats> mostFrequentWords(List<String> words, int k) {
        List<WordStats> wordList = new ArrayList<WordStats>();
        Map<String, Integer> count = new HashMap<String, Integer>();
        //create a map of words with count
        for (String word: words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }

        List<String> candidates = new ArrayList<String>(count.keySet());
        Collections.sort(candidates, (w1, w2) -> count.get(w1).equals(count.get(w2)) ?
                w1.compareTo(w2) : count.get(w2) - count.get(w1));

        if(candidates.size() < k) k = candidates.size();

        for(int i = 0; i < k; i++) {
            WordStats word = new WordStats(candidates.get(i), count.get(candidates.get(i)));
            System.out.println(word.toString());
            wordList.add(word);
        }

        return wordList;
    }

}