import javax.swing.JPanel;
import javax.swing.JComponent;
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
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.HashSet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;

public class JumbleSolverPanel extends JPanel {

	private JumbleSolver jumbleSolver;

	private char[] typedChars;
	private List<TextSquare> textSquares;
	private Set<String> possibleWords;
	private List<Character> selectedChars;

	private int squareX = 380;

	private static final Dimension PANEL_DIMENSIONS = new Dimension(1300, 1000);
	private static final int SQUARE_SIZE = 30;

	public JumbleSolverPanel(JumbleSolver jumbleSolver) {
		this.typedChars = new char[0];
		this.possibleWords = new HashSet<>();
		this.textSquares = new ArrayList<>();
		this.selectedChars = new ArrayList<>();
		this.setBackground(new Color(71, 42, 63));
		this.setPreferredSize(PANEL_DIMENSIONS);
		this.jumbleSolver = jumbleSolver;
		this.setUpKeyMappings();
		this.setUpClickListener();
	}

	private void setUpClickListener() {
		this.requestFocusInWindow();

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
			@Override
			public void mouseEntered(MouseEvent arg0) {

			}
			@Override
			public void mouseExited(MouseEvent arg0) {

			}
			@Override
			public void mousePressed(MouseEvent click) {
				clickedAt(click);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}
		});
	}

	private void clickedAt(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		for (int i = 0; i < textSquares.size(); i++) {
			TextSquare textSquare = textSquares.get(i);
			if (textSquare.containsPoint(new Point(x, y))) {
				textSquare.setSelected();
				System.out.println(textSquare.selected());
				if (textSquare.selected()){
					selectedChars.add(textSquare.character());
				} else {
					selectedChars.remove(textSquare.character());
				}
				repaint();
				break;
			}
		}
	}

	private void setUpKeyMappings() {
		this.requestFocusInWindow();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for (char c : alphabet) {
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(c), c);
		}
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false),"delete");

		for (char c : alphabet) {
			this.getActionMap().put(c, new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					add(c);
					possibleWords = jumbleSolver.findPossible(new String(typedChars));
					repaint();
				}
			});	
		}

		this.getActionMap().put("delete", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove();
				possibleWords = jumbleSolver.findPossible(new String(typedChars));
				repaint();
			}
		});
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
		g2.setColor(new Color(130, 134, 18));
		g2.fillOval(PANEL_DIMENSIONS.width / 2 - (ovalDimension.width / 2), PANEL_DIMENSIONS.height / 2 - (ovalDimension.height / 2), ovalDimension.width,  ovalDimension.height);
		g2.setColor(Color.BLACK);


		g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 30)); 
		g2.drawString("Entered a scrambled word", (PANEL_DIMENSIONS.width / 2) - 290, 250);
		g2.drawString("Possible words:", (PANEL_DIMENSIONS.width / 2) - 290, 450);
	}

	private void drawTextFields(Graphics2D g2) {
		for (char c : typedChars) {
			TextSquare textSquare = new TextSquare(c, squareX, 300);
			textSquare.draw(g2);
			textSquares.add(textSquare);
			
			squareX += SQUARE_SIZE;
		}
		squareX = 380;
	}

	private void drawPossibleWordsTextFields(Graphics2D g2) {
		if (possibleWords == null) { possibleWords = new HashSet<>(); }
		if (possibleWords.size() <= 0) { return; }

		int x = 380;
		int y = 500;

		for (String s : possibleWords) {
			char[] chars = s.toCharArray();
			for (char c : chars) {
				TextSquare textSquare = new TextSquare(c, x, y);
				textSquare.draw(g2);
				textSquares.add(textSquare);

				x += SQUARE_SIZE;
			}
			x = 380;
			y += 60;
		}
	}
}
