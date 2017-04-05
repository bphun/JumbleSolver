import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.KeyEvent;

public class JumbleSolverPanel extends JPanel {

	private JumbleSolver jumbleSolver;

	private List<Character> typedChars;
	private Map<Integer, String> possibleWords;
	
	private int numTextSquaresAdded;
	private int squareX = 300;

	private static final Dimension PANEL_DIMENSIONS = new Dimension(1300, 1000);
	private static final int SQUARE_SIZE = 30;

	public JumbleSolverPanel(JumbleSolver jumbleSolver) {
		this.typedChars = new ArrayList<>();
		possibleWords = new HashMap<>();
		this.numTextSquaresAdded = 0;
		this.jumbleSolver = jumbleSolver;
		this.setPreferredSize(PANEL_DIMENSIONS);
		this.setBackground(new Color(255, 183, 77));
		this.setUpKeyMappings();
	}

	private void setUpKeyMappings() {
		this.requestFocusInWindow();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for (char c : alphabet) {
			this.getInputMap().put(KeyStroke.getKeyStroke(c), c);
		}

		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false),"delete");

		for (char c : alphabet) {
			this.getActionMap().put(c, new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (typedChars.size() >= 10) { return; }
					typedChars.add(c);
					// possibleWords = jumbleSolver.calculatePossibilities(new String(typedChars));
					repaint();
				}
			});	
		}

		this.getActionMap().put("delete", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (typedChars.size() >= 0)  { return; }
				typedChars.remove(typedChars.size() - 1);
				// possibleWords = jumbleSolver.calculatePossibilities(new String(typedChars));
				repaint();
			}

		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawGUI(g2);
		drawTextFields(g2);
		drawPossibleWordsTextFields(g2);
	}

	private void drawGUI(Graphics2D g2) {
		Dimension ovalDimension = new Dimension(PANEL_DIMENSIONS.width - 50, PANEL_DIMENSIONS.height - 200);
		// g2.drawOval(PANEL_DIMENSIONS.width / 2 - (ovalDimension.width / 2), PANEL_DIMENSIONS.height / 2 - (ovalDimension.height / 2), ovalDimension.width,  ovalDimension.height);
		g2.setColor(new Color(187, 222, 251));
		g2.fillOval(PANEL_DIMENSIONS.width / 2 - (ovalDimension.width / 2), PANEL_DIMENSIONS.height / 2 - (ovalDimension.height / 2), ovalDimension.width,  ovalDimension.height);
		g2.setColor(Color.BLACK);


		g2.setFont(new Font("AvenirNext", Font.PLAIN, 30)); 
		g2.drawString("Entered a scrambled word", (PANEL_DIMENSIONS.width / 2) - 290, 250);
		g2.drawString("Possible words:", (PANEL_DIMENSIONS.width / 2) - 290, 450);

	}

	private void drawTextFields(Graphics2D g2) {
		for (Character c : typedChars) {
			// g2.drawRect(squareX, 300, SQUARE_SIZE, SQUARE_SIZE);
			g2.setColor(Color.WHITE);
			g2.fillRect(squareX, 300, SQUARE_SIZE, SQUARE_SIZE);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("AvenirNext", Font.PLAIN, 20)); 
			g2.drawString("" + c, squareX + (SQUARE_SIZE / 2), 300 + (SQUARE_SIZE / 2) + 10);
			
			squareX += SQUARE_SIZE;
		}
		squareX = 300;
	}

	private void drawPossibleWordsTextFields(Graphics2D g2) {
		if (possibleWords == null) { return; }

		
	}

}
