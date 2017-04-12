import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

public class JumbleSolver {

	private static final String SCRAMBLE_WORD_LIST_NAME = "scrambledWords.txt";
	private static final String WORD_LIST_NAME = "words.txt";

	private JumbleSolverPanel panel;
	private JFrame frame;
	private String currStr;
	private HashMap<String, Set<String>> words;

	public static void main(String[] args) {
		new JumbleSolver().start();
	}	

	private void start() {
		panel = new JumbleSolverPanel(this);
		frame = new JFrame("Jumble Solver");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		readFile();
	}

	public Set<String> findPossible(String str) {
		for (Map.Entry<String, Set<String>> map : words.entrySet()) {
			if (map.getKey().equals(alphebatize(str))) {
				for (String s : map.getValue()) {
					return map.getValue();
				}
			}
		}
		// return words.get(alphebatize(str));

		return null;
	}

	private void readFile() {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get(WORD_LIST_NAME), Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println("Could not load " + WORD_LIST_NAME);
			e.printStackTrace(); 
		}
		words = new HashMap<>();
		for (String s : lines) {
			String alphabetizedString = alphebatize(s);

			if (words.containsKey(alphabetizedString)) {
				words.get(alphabetizedString).add(s);
			} else {
				Set<String> set = new HashSet<>();
				set.add(s);
				words.put(alphabetizedString, set);
			}
		}
	}

	private String alphebatize(String s) {
		if (s.length() == 1) { return s; }
		char[] alphabetizedChars = s.toCharArray();
		Arrays.sort(alphabetizedChars);
		return new String(alphabetizedChars);
	}

	private Set<String> scrambleWord(String word) {
		Set<String> scrambledWords = new HashSet<>();
		switch (word.length()) {
			case 0:
			scrambledWords.add("");
			return scrambledWords;
		}
		
		Set<String> words = scrambleWord(word.substring(1));
		for (String str : words) {
			for (int i = 0; i <= str.length(); i++){
				scrambledWords.add(insertChar(str, word.charAt(0), i));
			}
		}
		words.clear();
		return scrambledWords;
	}

	private String insertChar(String str, char c, int j) {
		return str.substring(0, j) + c + str.substring(j);
	}
}
