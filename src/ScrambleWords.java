import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class ScrambleWords {
		
	private String fileName;	
	private Set<String> words;
	private File file;
	private int MAX_NUM_LINES = 1000;
	private String OUTPUTFILE_NAME = "scrambledWords.txt";

	public static void main(String[] args) {
		if (args.length > 0) {
			new ScrambleWords().process(args[0]);
		} else {
			System.out.println("No file name found in args");
		}
	}

	private void process(String fileName) {
		words = new HashSet<>();
		this.fileName = fileName;
		file = new File(fileName);
		processWordList();
	}

	public Set<String> process_return(String fileName) {
		words = new HashSet<>();
		this.fileName = fileName;
		file = new File(fileName);
		processWordList();
		return words;
	}

	private void processWordList() {
		int maxlines = 100;
		int count = 0;
		String encoding = "UTF-8";
		Set<String> scrambledWords = new HashSet<>();
		BufferedReader reader;
		BufferedWriter writer;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUTFILE_NAME), encoding));
			for (String x = reader.readLine(); x != null; x = reader.readLine()) {
				// if (++count == maxlines) {
				// 	// writer.close();
				// 	// writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUTFILE_NAME), encoding));
					
				// 	// reader.close();
				// 	// reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
				// 	maxlines = 0;
				// }
				writer.write("word: " + x + "\n");
				scrambledWords = scrambleWord(x);
				for (String word : scrambledWords) {
					writer.write(word + "\n");
				}
				writer.flush();
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
			System.err.println("Could not open text file");
			e.printStackTrace();
		}
	}

	private Set<String> scrambleWord(String word) {

		List<String> scrambledWords = new LinkedList<>();
		scrambledWords.add(String.valueOf(word.charAt(0)));

		List<String> temp = new LinkedList<>();

		for (int w = 1; w < word.length(); w++) {
			for (int s = 0; s < scrambledWords.size(); s++) {
				temp.addAll(merge(word.charAt(w), scrambledWords.get(s)));
			}

			scrambledWords.clear();
			scrambledWords.addAll(temp);

			temp.clear();
		}
		return new HashSet<String>(scrambledWords);
	}

	private static Set<String> merge(Character character,  String word) {
		if (word == null || word.isEmpty()) {
			return null;
		}

		int length = word.length();
		StringBuilder stringBuilder = new StringBuilder();
		Set<String> set = new HashSet<String>();

		for (int i = 0; i <= length; i++) {
			stringBuilder = new StringBuilder();
			stringBuilder.append(word.substring(0, i) + character + word.substring(i, length));
			set.add(stringBuilder.toString());
		}
		return set;
	}

	// private Set<String> scrambleWord(String word) {
	// 	Set<String> scrambledWords = new HashSet<>();
	// 	if (word.length() == 0) {
	// 		scrambledWords.add("");
	// 		return scrambledWords;
	// 	}

	// 	char firstChar = word.charAt(0);
	// 	String remaining = word.substring(1); 
	// 	Set<String> words = scrambleWord(remaining);
	// 	for (String str : words) {
	// 		for (int i = 0; i <= str.length(); i++){
	// 			scrambledWords.add(insertChar(str, firstChar, i));
	// 		}
	// 	}
	// 	return scrambledWords;
	// }

	// private String insertChar(String str, char c, int j) {
	// 	String begin = str.substring(0, j);
	// 	String end = str.substring(j);
	// 	return begin + c + end;
	// }

}
