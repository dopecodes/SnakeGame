import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintGame extends JPanel {
	PaintGame() {
		this.setBackground(new Color(7244605));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (Point point : Snake.SnakeParts)
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE,
					Snake.SCALE, Snake.SCALE);
		g.setColor(Color.red);
		if (Snake.cherry != null)
			g.fillRect(Snake.cherry.x * Snake.SCALE, Snake.cherry.y
					* Snake.SCALE, Snake.SCALE, Snake.SCALE);
	}
}
