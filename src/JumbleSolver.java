import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashSet;

public class JumbleSolver {

	private static final String SCRAMBLE_WORD_LIST_DIRECTORY = "scrambledWords.txt";

	private JumbleSolverPanel panel;
	private JFrame frame;
	private HashMap<String, Set<String>> words;

	public static void main(String[] args) {
		new JumbleSolver().start();
	}	

	private void start() {
		words = new HashMap<String, Set<String>>();
		panel = new JumbleSolverPanel(this);
		frame = new JFrame("Jumble Solver");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// readInFile();
	}

	public Set<String> calculatePossibilities(String str) {
		Set<String> possibilities = new HashSet<>();

		for (Map.Entry<String, Set<String>> map : words.entrySet()) {
			if (map.getValue().contains(str)) {
				possibilities.add(map.getKey());
			}
		}

		if (possibilities.size() == 0) {
			return null;
		} else {
			return possibilities;
		}
	}

	private void readInFile() {
		String key = "";
		try {
			List<String> lines = Files.readAllLines(Paths.get(SCRAMBLE_WORD_LIST_DIRECTORY), Charset.defaultCharset());
			Set<String> scrambled = new HashSet<>();
			for (String s : lines) {
				if (s.contains("word: ")) {
					if (scrambled.size() > 0) {
						words.put(key, scrambled);
						scrambled.clear();
					}
					key = s;
				} else {
					scrambled.add(s);
				}
			}
		} catch (IOException e) {
			System.err.println("Could not read text file");
			e.printStackTrace();
		}
	}


}
