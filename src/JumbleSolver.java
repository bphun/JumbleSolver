import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class JumbleSolver {

	private static final String WORD_LIST_NAME = "words.txt";

	private JumbleSolverPanel panel;
	private JFrame frame;
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
				return map.getValue();
			}
		}
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
}
