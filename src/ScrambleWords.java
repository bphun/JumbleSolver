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
import java.util.Arrays;
import java.util.Map;

public class ScrambleWords {
		
	private String fileName;
	private List<String> lines;
	private HashMap<String, Set<String>> words;
	private Thread thread;
	private String word;

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

	private void unscramble(String word) {
		readFile();
		processLines();

		for (Map.Entry<String, Set<String>> map : words.entrySet()) {
			if (map.getKey().equals(alphebatize(word))) {
				for (String s : map.getValue()) {
					if (s.equals(word)) { continue; }
					System.out.println("Possible: " + s);
				}
				return;
			}
		}
	}

	private String alphebatize(String s) {
		if (s.length() == 1) { return s; }
		char[] alphabetizedChars = s.toCharArray();
		Arrays.sort(alphabetizedChars);
		return new String(alphabetizedChars);
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
				} 
				
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

	private void processLines() {
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

	private void readFile() {
		try {
			lines = Files.readAllLines(Paths.get("words.txt"), Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println("Could not load words.txt");
			e.printStackTrace(); 
		}
	}

	private void print(String s) {
		System.out.print(String.format("\033[%dA",1));
		System.out.print("\033[2K");
		System.out.println(s);
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
