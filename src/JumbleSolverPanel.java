import javax.swing.JPanel;
import java.awt.Dimension;

public class JumbleSolverPanel extends JPanel {

	JumbleSolver jumbleSolver;

	public JumbleSolverPanel(JumbleSolver jumbleSolver) {
		this.jumbleSolver = jumbleSolver;
		this.setPreferredSize(new Dimension(1300, 1000));
	}

}
