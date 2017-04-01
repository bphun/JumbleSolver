import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class JumbleSolverPanel extends JPanel {

	private JumbleSolver jumbleSolver;

	private static final Dimension PANEL_DIMENSIONS = new Dimension(1300, 1000);

	public JumbleSolverPanel(JumbleSolver jumbleSolver) {
		this.jumbleSolver = jumbleSolver;
		this.setPreferredSize(PANEL_DIMENSIONS);
		this.setBackground(new Color(255, 183, 77));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawGUI(g2);
	}

	private void drawGUI(Graphics2D g2) {
		Dimension ovalDimension = new Dimension(PANEL_DIMENSIONS.width - 50, PANEL_DIMENSIONS.height - 200);
		g2.drawOval(PANEL_DIMENSIONS.width / 2 - (ovalDimension.width / 2), PANEL_DIMENSIONS.height / 2 - (ovalDimension.height / 2), ovalDimension.width,  ovalDimension.height);
		g2.setColor(new Color(187, 222, 251));
		g2.fillOval(PANEL_DIMENSIONS.width / 2 - (ovalDimension.width / 2), PANEL_DIMENSIONS.height / 2 - (ovalDimension.height / 2), ovalDimension.width,  ovalDimension.height);
		g2.setColor(Color.BLACK);

		g2.drawString("Entered a scrambled word", PANEL_DIMENSIONS.width, 300);

	}

}
