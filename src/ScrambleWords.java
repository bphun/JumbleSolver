import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Collections;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.File;

public class ScrambleWords implements Runnable {
		
	private String fileName;
	private List<String> lines;
	private Thread thread;
	private String word;
	private Set<String> possible;
	private int currLine;
	private List<String> right;
	private List<String> left;
	private static final String OUTPUTFILE_NAME = "scrambledWords.txt";

	public static void main(String[] args) {
		if (args.length == 1) {
			if (args[0].contains(".txt")) {
				new ScrambleWords().processFile(args[0]);
			} else {
				new ScrambleWords().processWord(args[0]);
			}  
		} else if (args.length == 2) { 
			if (args[0].toLowerCase().contains("unscramble")) {
				new ScrambleWords().unscramble(args[1]);
			}
		} else {
			System.out.println("You did not specify the text file that you want to process");
		}
	}

	private void processFile(String fileName) {
		readFile();
		this.fileName = fileName;
		processWordList();
	}

	private void processWord(String word) {
		for (String s : scrambleWord(word)) {
			System.out.println("Scrambled: " + s);
		}
	}

	// private void unscramble(String word) {
	// 	possible = new HashSet<>();
	// 	readFile();
	// 	System.out.println("Unscrambling " + word);
	// 	for (int i = 0; i < lines.size(); i++) {
	// 		String unscramble = lines.get(i);
	// 		for (String scramble : scrambleWord(word)) {
	// 			if (scramble.equals(unscramble)) {
	// 				possible.add(unscramble);
	// 				// System.out.println(possible.size());
	// 			}
	// 		}
	// 	} 
	// 	for (String s : possible) {
	// 		// System.out.println("Possible: " + s);
	// 	}
	// }

	private void unscramble(String word) {
		readFile();

		this.word = word;
		possible = new HashSet<>();

		System.out.println("Unscrambling " + word);
		
		right = new ArrayList<>();
		left = new ArrayList<>();

		for (int i = 0; i < lines.size() / 2; i++) {
			right.add(lines.get(i));
		}
		for (int i = lines.size() / 2; i < left.size(); i++) {
			left.add(lines.get(i));
		}

		System.out.println("Left: " + left.size());
		System.out.println("Right: " + right.size());

		thread = new Thread(this);
		thread.start();

		for (int i = 0; i < right.size(); i++) {
			String unscramble = right.get(i);
			for (String scramble : scrambleWord(word)) {
				if (scramble.equals(unscramble)) {
					add(unscramble);
					// System.out.println(possible.size());
				}
			}
			currLine = i;
			// System.out.println("Main Thread currLine:" + currLine);
		} 

		join();

		if (possible.size() == 0) {
			System.out.println("No possible words");
			return;
		} else {
			for (String s : possible) {
				if (s.equals(word)) { continue; }
				System.out.println("Possible: " + s);
			}
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < left.size(); i++) {
			String unscramble = left.get(i);
			for (String scramble : scrambleWord(this.word)) {
				if (scramble.equals(unscramble)) {
					add(unscramble);
					// System.out.println(possible.size());
				}
			}
			currLine = i;
			// System.out.println("Thread 1 currLine:" + currLine);
		} 
	}

	public synchronized void add(String s) {
		this.possible.add(s);
	}

	public synchronized void join() {
		try {
			thread.join();
		} catch (Exception e) {

		}
	}

	private void processWordList() {
		String encoding = "UTF-8";
		// Set<String> scrambledWords = new HashSet<>();
		BufferedWriter writer;
		int count = 0;
		readFile();
		try {
			List<String> words = new ArrayList<>();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUTFILE_NAME), encoding));
			float size = lines.size();
			for (int i = 0; i < lines.size(); i++) {
				String word = lines.get(i).toLowerCase();
				if (word.length() > 9) {
					continue; 
				} /*else if (word.equals("Abbasside")) {
					return;
				}*/ 
				if (count >= 1000000) {
					// System.out.println("Writing " + words.size() + " lines to " + OUTPUTFILE_NAME);
					print("Writing " + words.size() + " lines to " + OUTPUTFILE_NAME);
					for (String s : words) {
						writer.write(s + "\n");
						writer.flush();
					}
					count = 0;
					words.clear();
					// System.out.println("Finished writing to " + OUTPUTFILE_NAME);
					print("Finished writing to " + OUTPUTFILE_NAME);
				}
				
				// System.out.println("Current Word: " + word + ", Progress: " + ((i / size) * 100) + "%");
				print("Current Word: " + word + " , Progress: " + ((i / size) * 100) + "%");

				words.add("word: " + word);
				words.addAll(scrambleWord(word));
				count = words.size();
			}
			lines.clear();

			if (words.size() > 0) {
				for (String s : words) {
					writer.write(s + "\n");
					writer.flush();
				}
				words.clear();
			}

			writer.close();
		} catch (IOException e) {
			System.err.println("Could not open text file");
			e.printStackTrace();
		}		
	}

	private void readFile() {
		try {
			lines = Files.readAllLines(Paths.get("words.txt"), Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println("Could not load words.txt");
			e.printStackTrace(); 
		}
		System.out.println(lines.size());
	}

	private void print(String s) {
		System.out.print(String.format("\033[%dA",1));
		System.out.print("\033[2K");
		System.out.println(s);
	}

	private synchronized Set<String> scrambleWord(String word) {
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

	private synchronized String insertChar(String str, char c, int j) {
		return str.substring(0, j) + c + str.substring(j);
	}


}
