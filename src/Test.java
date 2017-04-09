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

public class Test implements Runnable {

	private List<String> lines;
	private Set<String> possibilities;
	private String currStr;
	private Thread thread;
	
	private static final String WORD_LIST_NAME = "words.txt";

	public static void main(String[] args) {	
		new Test().start();
	}

	public void start() {
		lines = new ArrayList<>();
		possibilities = new HashSet<>();
		thread = new Thread(this);

		readFile();

		possibilities = findPossible("rapnte");
		
		for (String s : possibilities) {
			System.out.println("Word: " + s);
		}
	}

	public Set<String> findPossible(String str) {
		currStr = str;
		thread.start();

		for (int i = 0; i < lines.size() / 2; i++) {
			for (String s : scrambleWord(lines.get(i))) {
				if (s.equals(str)) {
					possibilities.add(lines.get(i));
					System.out.println(possibilities.size());
					break;
				}
			}
		}

		return possibilities;
	}

	@Override
	public void run() {
		for (int i = lines.size() - 1; i >= lines.size() / 2; i--) {
			for (String s : scrambleWord(lines.get(i))) {
				if (s.equals(currStr)) {
					possibilities.add(lines.get(i));
					System.out.println(possibilities.size());
					break;
				}
			}
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