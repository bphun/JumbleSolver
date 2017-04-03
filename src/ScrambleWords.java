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
		Set<String> scrambledWords = new HashSet<>();
		BufferedReader reader;
		BufferedWriter writer;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUTFILE_NAME), encoding));
			for (String x = reader.readLine(); x != null; x = reader.readLine()) {
				writer.write("word: " + x + "\n");
				scrambledWords = scrambleWord(x);
				for (String word : scrambledWords) {
					writer.write(word + "\n");
					writer.flush();
				}
				scrambledWords.clear();
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
			System.err.println("Could not open text file");
			e.printStackTrace();
		}
	}

	// private Set<String> scrambleWord(String word) {
	// 	Set<String> scrambled = new HashSet<>();
	// 	List<String> strList;

	// 	while (scrambled.size() < numCombinations(word)) {
	// 		strList = new ArrayList<>();

	// 		for (String s : word.toLowerCase().split("")) {
	// 			strList.add(s);
	// 		}

	// 		Collections.shuffle(strList);

	// 		StringBuilder stringBuilder = new StringBuilder();
	// 		for (String s : strList) {
	// 			stringBuilder.append(s);
	// 		}

	// 		String str = stringBuilder.toString();
	// 		if (!scrambled.contains(str)) {
	// 			scrambled.add(str);
	// 		}
	// 	}
	// 	return scrambled;
	// }

	// public int numCombinations(String word){
	// 	HashMap<String, Integer> tempStorage = new HashMap<String, Integer>();

	// 	String[] temp = word.split("");

	// 	int index = 1;
	// 	for (String s : temp){
	// 		if( !tempStorage.containsKey(s)){
	// 			tempStorage.put(s, index);
	// 		} else {
	// 			tempStorage.put(s, tempStorage.get(s) + 1);
	// 		}
	// 	}

	// 	int divide = 1;
	// 	for (int d : tempStorage.values()){
	// 		divide *= factorial(d);
	// 	}
	// 	return (factorial(word.length()) / divide);
	// }

	// public int factorial(int n){
	// 	int d = 1;
	// 	while(n > 1){
	// 		d *= n--;
	// 	}
	// 	return d;
	// }






























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
