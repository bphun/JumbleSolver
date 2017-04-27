package com.brandonphan.jumblesolver_android;

import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by BrandonPhan on 4/18/17.
 */

public class JumbleSolver {
    private List<String> lines;
    private HashMap<String, HashSet<String>> words;

    public JumbleSolver(List<String> lines) {
        this.lines = lines;
        processLines();
    }

    private void processWord(String word) {
        for (String s : scrambleWord(word)) {
            System.out.println("Scrambled: " + s);
        }
    }

    public HashSet<String> unscramble(String word) {
        for (Map.Entry<String, HashSet<String>> map : words.entrySet()) {
            if (map.getKey().equals(alphabetize(word))) {
                return map.getValue();
            }
        }
        return null;
    }

    private String alphabetize(String s) {
        if (s.length() == 1) { return s; }
        char[] alphabatizedChars = s.toCharArray();
        Arrays.sort(alphabatizedChars);
        return new String(alphabatizedChars);
    }

    private void processLines() {
        words = new HashMap<>();
        for (String s : lines) {
            String alphabetizedString = alphabetize(s);

            if (words.containsKey(alphabetizedString)) {
                words.get(alphabetizedString).add(s);
            } else {
                HashSet<String> set = new HashSet<>();
                set.add(s);
                words.put(alphabetizedString, set);
            }
        }
    }

    private Set<String> scrambleWord(String word) {
        Set<String> scrambledWords = new HashSet<>();
        if (word.length() == 0) {
            scrambledWords.add("");
            return scrambledWords;
        }

        Set<String> words = scrambleWord(word.substring(1));
        for (String str : words) {
            for (int i = 0; i <= str.length(); i++){
                scrambledWords.add(insertChar(str, word.charAt(0), i));
            }
        }
        return scrambledWords;
    }

    private String insertChar(String str, char c, int j) {
        return str.substring(0, j) + c + str.substring(j);
    }
}
