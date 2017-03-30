import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;

public class JumbleSolver {

	JumbleSolverPanel panel;
	JFrame frame;
	HashMap<String, Set<String>> words;

	public static void main(String[] args) {
		new JumbleSolver().start();
	}	

	private void start() {
		words = new HashMap<String, Set<String>>();
		panel = new JumbleSolverPanel(this);
		frame = new JFrame("Jumble Solver");
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
