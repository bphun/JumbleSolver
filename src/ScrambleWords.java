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
import org.apache.commons.io.FileUtils;
import java.io.File;

public class ScrambleWords {
		
	private String fileName;
	
	private static final String OUTPUTFILE_NAME = "scrambledWords.txt";

	public static void main(String[] args) {
		if (args.length > 0) {
			new ScrambleWords().process(args[0]);
		} else {
			System.out.println("No file name found in args");
		}
	}

	private void process(String fileName) {
		this.fileName = fileName;
		processWordList();
	}

	// public Set<String> process_return(String fileName) {
		// this.fileName = fileName;
		// processWordList();
		//return words;
	// }

	private void processWordList() {
		String encoding = "UTF-8";
		// Set<String> scrambledWords = new HashSet<>();
		BufferedWriter writer;
	
		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
			List<String> words = new ArrayList<>();
			float size = lines.size();
			for (int i = 0; i < lines.size(); i++) {
				String word = lines.get(i);
				if (word.length() > 10) {
					continue; 
				} /*else if (word.equals("Abbasside")) {
					return;
				}*/
				
				System.out.println("Current Word: " + word + ", Progress: " + ((i / size) * 100) + "%");

				words.add("word: " + word);
				words.addAll(scrambleWord(word));

				// scrambledWords = scrambleWord(word);
				// for (String str : scrambledWords) {
				// 	words.add(str);
				// }
				// scrambledWords.clear();
			}
			File f = new File("scrambledWords.txt");
			FileUtils.writeLines(f, words);
		} catch (IOException e) {
			System.err.println("Could not open text file");
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
		return scrambledWords;
	}

	private String insertChar(String str, char c, int j) {
		return str.substring(0, j) + c + str.substring(j);
	}


}
