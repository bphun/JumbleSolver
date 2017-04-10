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
import java.util.Set;
import java.util.HashSet;

public class JumbleSolverPanel extends JPanel implements Runnable {

	private JumbleSolver jumbleSolver;

	private Thread thread;

	private char[] typedChars;
	private Set<String> possibleWords;
	private int numTextSquaresAdded;
	private int squareX = 300;

	private static final Dimension PANEL_DIMENSIONS = new Dimension(1300, 1000);
	private static final int SQUARE_SIZE = 30;

	public JumbleSolverPanel(JumbleSolver jumbleSolver) {
		this.typedChars = new char[0];
		this.possibleWords = new HashSet<>();
		this.numTextSquaresAdded = 0;
		this.jumbleSolver = jumbleSolver;
		this.setPreferredSize(PANEL_DIMENSIONS);
		this.setBackground(new Color(255, 183, 77));
		this.setUpKeyMappings();
		this.thread = new Thread(this);
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
					add(c);

					thread.start();

					repaint();
					join();
				}
			});	
		}

		this.getActionMap().put("delete", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove();

				thread.start();

				repaint();
				join();
			}

		});
	}

	public synchronized void join() {
		try {
			thread.join();
		} catch (Exception e) {
			System.err.println("Could not join thread");
		}
	}

	@Override
	public void run() {
		Set<String> possibilities = jumbleSolver.findPossible(new String(typedChars));
		if (possibilities == null) { return; }
		this.possibleWords = possibilities;
	}

	private void add(char c) {
		char[] temp = new char[typedChars.length + 1];
		for (int i = 0; i < typedChars.length; i++) {
			temp[i] = typedChars[i];
		}
		temp[temp.length - 1] = c;

		typedChars = temp;
	}

	private void remove() {
		if (typedChars.length <= 0)  { return; }
		char[] temp = new char[typedChars.length - 1];
		for (int i = 0; i < typedChars.length - 1; i++) {
			temp[i] = typedChars[i];
		}
		typedChars = temp;
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
		g2.setColor(new Color(187, 222, 251));
		g2.fillOval(PANEL_DIMENSIONS.width / 2 - (ovalDimension.width / 2), PANEL_DIMENSIONS.height / 2 - (ovalDimension.height / 2), ovalDimension.width,  ovalDimension.height);
		g2.setColor(Color.BLACK);


		g2.setFont(new Font("AvenirNext", Font.PLAIN, 30)); 
		g2.drawString("Entered a scrambled word", (PANEL_DIMENSIONS.width / 2) - 290, 250);
		g2.drawString("Possible words:", (PANEL_DIMENSIONS.width / 2) - 290, 450);

	}

	private void drawTextFields(Graphics2D g2) {
		for (char c : typedChars) {
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
		if (possibleWords.size() <= 0) { return; }

		int x = 300;
		int y = 800;

		for (String s : possibleWords) {
			char[] chars = s.toCharArray();
			System.out.println("Word: " + new String(chars));
			for (char c : chars) {
				g2.setColor(Color.WHITE);
				g2.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("AvenirNext", Font.PLAIN, 20)); 
				g2.drawString("" + c, x + (SQUARE_SIZE / 2), y + (SQUARE_SIZE / 2) + 10);

				x += SQUARE_SIZE;
			}
			y += 80;
		}
		
	}

}
