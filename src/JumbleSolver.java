import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

public class JumbleSolver {

	private static final String WORD_LIST_DIRECTORY = "words.txt";

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

		// if (!textFileIsFormated) {
		// 	System.out.println("The text file that is selected has not yet been formatted... processing word file now.");
		// 	ScrambleWords scrambler = new ScrambleWords();
		// 	words = scrambler.process_return(words.txt);
		// }

		// readInFile();
	}

	public void calculatePossibilities(String str) {
		
	}

	// private void readInFile() {
	// 	try {
	// 		BufferedReader reader = new BufferedReader(new FileReader(new File(WORD_LIST_DIRECTORY)));	
	// 		for (String x = reader.readLine(); x != null; x = reader.readLine()) {
	// 			words.add(x);
	// 		}
	// 		reader.close();
	// 	} catch (IOException e) {
	// 		System.err.println("Could not read text file");
	// 		e.printStackTrace();
	// 	}
	// }

	// private boolean textFileIsFormated() {
	// 	try {
	// 		BufferedReader reader = new BufferedReader(new FileReader(new File(WORD_LIST_DIRECTORY)));	
	// 		if (!reader.readLine().contains("word: ")) {
	// 			return false;
	// 		}
	// 	} catch (IOException e) {
	// 		System.err.println("Could not read text file");
	// 		e.printStackTrace();
	// 	}

	// 	return true;
	// }


}
