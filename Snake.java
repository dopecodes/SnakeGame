import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

@SuppressWarnings("serial")
public class Snake extends JFrame implements ActionListener, KeyListener {

	public static final int SCALE = 30, COLUMN = 40, ROW = 30, SPEED = 60;

	public static ArrayList<Point> SnakeParts = new ArrayList<Point>();
	public static Point cherry;

	private String scoreText;
	private Timer timer;
	private Random rand = new Random();
	private JLabel screenScore = new JLabel();
	private PaintGame render = new PaintGame();
	private Dimension location = Toolkit.getDefaultToolkit().getScreenSize();
	private Point head = new Point(0, 0);

	private boolean over = false, pause = false, moved = true;
	private final int DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3,
			HEIGHT = ROW * SCALE + 40, // insets top 37, bot 3
			WIDTH = COLUMN * SCALE + 6; // insets left 3, right 3
	private int direction = DOWN, tailLength = 1, score = 0;

	Snake() {
		timer = new Timer(SPEED, this);
		this.addKeyListener(this);
		this.setTitle("Snake");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation((int) location.getWidth() / 2 - this.getWidth() / 2,
				(int) location.getHeight() / 2 - this.getHeight() / 2);
		screenScore.setFont(new Font("bestFont", Font.BOLD, SCALE));
		render.add(screenScore);
		this.add(render);
		timer.start();
		// System.out.println(this.getInsets());
	}

	private void restart() {
		SnakeParts.clear();
		over = false;
		pause = false;
		direction = DOWN;
		tailLength = 1;
		score = 0;
		moved = true;
		head = new Point(0, 0);
		cherry = null;
	}

	private boolean tailAt(int x, int y) {
		for (Point point : SnakeParts)
			if (point.x == x && point.y == y)
				return true;
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!over && !pause) {
			SnakeParts.add(new Point(head.x, head.y));
			if (SnakeParts.size() > tailLength + 1)
				SnakeParts.remove(0);
			render.repaint();
			if (direction == LEFT)
				if (head.x - 1 >= 0 && !tailAt(head.x - 1, head.y)) {
					head = new Point(head.x - 1, head.y);
					moved = true;
				} else
					over = true;
			if (direction == RIGHT)
				if (head.x + 1 < this.getContentPane().getWidth() / SCALE
						&& !tailAt(head.x + 1, head.y)) {
					head = new Point(head.x + 1, head.y);
					moved = true;
				} else
					over = true;
			if (direction == DOWN)
				if (head.y + 1 < this.getContentPane().getHeight() / SCALE
						&& !tailAt(head.x, head.y + 1)) {
					head = new Point(head.x, head.y + 1);
					moved = true;
				} else
					over = true;
			if (direction == UP)
				if (head.y - 1 >= 0 && !tailAt(head.x, head.y - 1)) {
					head = new Point(head.x, head.y - 1);
					moved = true;
				} else
					over = true;
			while (cherry == null) {
				cherry = new Point(rand.nextInt(this.getContentPane()
						.getWidth() / SCALE), rand.nextInt(this
						.getContentPane().getHeight() / SCALE));
				for (Point point : SnakeParts) {
					if (point.equals(cherry))
						cherry = null;
						break;
				}
			}
			if (cherry != null)
				if (head.equals(cherry)) {
					tailLength++;
					score += 100;
					cherry = null;
				}
			scoreText = "Score: " + score + ", Taillength: " + tailLength;
			screenScore.setText(scoreText);
		}
		// System.out.println(head.x + ", " + head.y);
		// if(cherry != null)
		// System.out.println(cherry.x + ", " + cherry.y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if ((key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
				&& direction != DOWN && moved) {
			direction = UP;
			moved = false;
		}
		if ((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
				&& direction != UP && moved) {
			direction = DOWN;
			moved = false;
		}
		if ((key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
				&& direction != LEFT && moved) {
			direction = RIGHT;
			moved = false;
		}
		if ((key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
				&& direction != RIGHT && moved) {
			direction = LEFT;
			moved = false;
		}
		if (key == KeyEvent.VK_SPACE)
			if (!over)
				if (!pause) {
					pause = true;
					scoreText = scoreText + "\t PAUSED";
					screenScore.setText(scoreText);
					timer.stop();
				} else {
					pause = false;
					if (!timer.isRunning())
						timer.start();
				}
			else
				restart();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		Snake snake = new Snake();
	}
}
