import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class TextSquare {

	private char c;
	private int x, y;
	private boolean selected;

	private static final int SQUARE_SIZE = 30;

	public TextSquare(char c, int x, int y) {
		this.c = c;
		this.x = x;
		this.y = y;
	} 

	public Character character() {
		return (Character)this.c;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean containsPoint(Point p) {
		return new Rectangle(x, y, SQUARE_SIZE, SQUARE_SIZE).contains(p);
	} 

	public void setSelected() {
		this.selected = !this.selected;
	}

	public boolean selected() {
		return this.selected;
	}

	@Override
	public String toString() {
		return "Character: " + c + " X: " + x + " Y: " + y + " Selected: " + selected; 
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("AvenirNext", Font.PLAIN, 20)); 
		g2.drawString("" + c, x + (SQUARE_SIZE / 2) - 10, y + (SQUARE_SIZE / 2) + 10);
		System.out.println(selected);
		if (selected) {
			g2.setColor(Color.GREEN);
			g2.fillOval(x, y, SQUARE_SIZE, SQUARE_SIZE);
		}
	}
}


