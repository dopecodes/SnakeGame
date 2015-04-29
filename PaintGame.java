import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintGame extends JPanel {
	PaintGame() {
		this.setBackground(Color.black);
	}

	private void paintGrid(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i <= Snake.COLUMN; i++)
			for (int j = 0; j <= Snake.ROW; j++) {
				g.drawLine(i * Snake.SCALE, j * Snake.SCALE, i * Snake.SCALE,
						(j + 1) * Snake.SCALE);
				g.drawLine(i * Snake.SCALE, j * Snake.SCALE, (i + 1)
						* Snake.SCALE, j * Snake.SCALE);
			}
	}

	private void paintEyes(Graphics g) {
		g.setColor(Color.BLACK);
		// Drawing eyes ...
		if (!Snake.SnakeParts.isEmpty())
			switch (Snake.direction) {
			case Snake.DOWN:
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE / 2,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + (Snake.SCALE / 10 * 9));
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE / 2,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + (Snake.SCALE / 10 * 9));
				break;
			case Snake.UP:
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE
								- (Snake.SCALE / 10 * 9),
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 2);
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE
								- (Snake.SCALE / 10 * 9),
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 2);
				break;
			case Snake.LEFT:
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE - (Snake.SCALE / 10 * 9),
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 2,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE / 3);
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE - (Snake.SCALE / 10 * 9),
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 2,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3);
				break;
			case Snake.RIGHT:
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE / 2,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + +(Snake.SCALE / 10 * 9),
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE / 3);
				g.drawLine(Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
						* Snake.SCALE + Snake.SCALE / 2,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3,
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).x
								* Snake.SCALE + (Snake.SCALE / 10 * 9),
						Snake.SnakeParts.get(Snake.SnakeParts.size() - 1).y
								* Snake.SCALE + Snake.SCALE - Snake.SCALE / 3);
				break;
			default:
				break;
			}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Point point : Snake.SnakeParts) {
			g.setColor(Color.GREEN);
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE,
					Snake.SCALE, Snake.SCALE);
		}
		this.paintEyes(g);
		this.paintGrid(g);
		g.setColor(Color.red);
		if (Snake.cherry != null)
			g.fillOval(Snake.cherry.x * Snake.SCALE, Snake.cherry.y
					* Snake.SCALE, Snake.SCALE, Snake.SCALE);
	}
}
