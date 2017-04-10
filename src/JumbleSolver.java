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

public class JumbleSolver implements Runnable {

	private static final String SCRAMBLE_WORD_LIST_NAME = "scrambledWords.txt";
	private static final String WORD_LIST_NAME = "words.txt";

	private JumbleSolverPanel panel;
	private JFrame frame;
	private String currStr;
	private List<String> lines;
	private Thread thread;
	private Set<String> possible;

	public static void main(String[] args) {
		new JumbleSolver().start();
	}	

	private void start() {
		lines = new ArrayList<>();
		possible = new HashSet<>();
		panel = new JumbleSolverPanel(this);
		frame = new JFrame("Jumble Solver");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		readFile();
		thread = new Thread(this);
	}

	public Set<String> findPossible(String str) {
		currStr = str;
		thread.start();

		List<String> right = new ArrayList<>();
		for (int i = 0; i < lines.size() / 2; i++) {
			right.add(get(i));
		}

		for (int i = 0; i < right.size(); i++) {
			String unscramble = right.get(i);
			for (String scramble : scrambleWord(str)) {
				if (scramble.equals(unscramble)) {
					add(unscramble);
				}
			}
		}

		join();

		return possible;
	}

	@Override
	public void run() {
		List<String> left = new ArrayList<>();
		for (int i = lines.size() / 2; i < lines.size(); i++) {
			left.add(get(i));
		}
		for (int i = 0; i < left.size(); i++) {
			String unscramble = left.get(i);
			for (String scramble : scrambleWord(currStr)) {
				if (scramble.equals(unscramble)) {
					add(unscramble);
				}
			}
		}		
	}

	private void add(String s) {
		this.possible.add(s);
	}

	private String get(int i) {
		return lines.get(i);
	}

	private void join() {
		try {
			thread.join();
		} catch (Exception e) {

		}
	}

	private void readFile() {
		try {
			lines = Files.readAllLines(Paths.get(WORD_LIST_NAME), Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println("Could not load " + WORD_LIST_NAME);
			e.printStackTrace(); 
		}
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
