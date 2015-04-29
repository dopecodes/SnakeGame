import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Snake implements ActionListener, KeyListener {

	public static final int SCALE = 38, ROW = 25, COLUMN = 25, SPEED = 90;
	public static final int DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3;

	public static ArrayList<Point> SnakeParts = new ArrayList<Point>();
	public static Point cherry;
	public static int direction = DOWN;

	private String scoreText;
	private Timer timer;
	private JFrame frame = new JFrame("Snake");
	private Random rand = new Random();
	private JSplitPane split = new JSplitPane();
	private JPanel statsDisplay = new JPanel();
	private JLabel pauseText = new JLabel();
	private JLabel totalScore = new JLabel();
	private JLabel cherryScore = new JLabel();
	private JLabel cherryseaten = new JLabel();
	private PaintGame render = new PaintGame();
	private Dimension location = Toolkit.getDefaultToolkit().getScreenSize();
	private Point head = new Point(0, 0);

	private boolean over = false, pause = false, moved = true;
	private final int WIDTH = COLUMN * SCALE + 10 * SCALE,
			HEIGHT = ROW * SCALE;
	private int tailLength = 4, cherryValue = 100, score = 0;

	Snake() {
		timer = new Timer(SPEED, this);
		frame.addKeyListener(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation((int) location.getWidth() / 2 - frame.getWidth() / 2,
				(int) location.getHeight() / 2 - frame.getHeight() / 2);
		totalScore.setFont(new Font("bestFont", Font.BOLD, SCALE));
		totalScore.setForeground(Color.BLUE);
		totalScore.setHorizontalAlignment(JLabel.CENTER);
		pauseText.setFont(new Font("bestFont", Font.BOLD, SCALE));
		pauseText.setForeground(Color.BLUE);
		totalScore.setHorizontalAlignment(JLabel.CENTER);
		cherryScore.setFont(new Font("bestFont", Font.BOLD, SCALE));
		cherryScore.setForeground(Color.BLUE);
		cherryScore.setHorizontalAlignment(JLabel.CENTER);
		cherryseaten.setFont(new Font("bestFont", Font.BOLD, SCALE));
		cherryseaten.setForeground(Color.BLUE);
		cherryseaten.setHorizontalAlignment(JLabel.CENTER);
		statsDisplay.setBackground(Color.BLACK);
		statsDisplay.setLayout(new GridLayout(3, 1));
		render.add(pauseText);
		statsDisplay.add(totalScore);
		statsDisplay.add(cherryScore);
		statsDisplay.add(cherryseaten);
		split.setDividerSize(1);
		split.setDividerLocation(COLUMN * SCALE + split.getDividerSize());
		split.setLeftComponent(render);
		split.setRightComponent(statsDisplay);
		frame.add(split);
		timer.start();
		frame.setSize(WIDTH + frame.getInsets().left + frame.getInsets().right,
				HEIGHT + frame.getInsets().top + frame.getInsets().bottom);
	}

	private void restart() {
		SnakeParts.clear();
		over = false;
		pause = false;
		direction = DOWN;
		tailLength = 4;
		cherryValue = 100;
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
			render.repaint();
			SnakeParts.add(new Point(head.x, head.y));
			if (SnakeParts.size() > tailLength + 1)
				SnakeParts.remove(0);
			if (direction == LEFT)
				if (head.x - 1 >= 0 && !tailAt(head.x - 1, head.y)) {
					head = new Point(head.x - 1, head.y);
					moved = true;
				} else
					over = true;
			if (direction == RIGHT)
				if (head.x + 1 < split.getLeftComponent().getWidth() / SCALE
						&& !tailAt(head.x + 1, head.y)) {
					head = new Point(head.x + 1, head.y);
					moved = true;
				} else
					over = true;
			if (direction == DOWN)
				if (head.y + 1 < frame.getContentPane().getHeight() / SCALE
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
				cherry = new Point(rand.nextInt(COLUMN), rand.nextInt(ROW));
				for (Point point : SnakeParts) {
					if (cherry.equals(point)) {
						cherry = null;
						break;
					}
				}
			}
			if (cherry != null) {
				cherryValue--;
				if (head.equals(cherry)) {
					tailLength++;
					score = score + cherryValue;
					cherryValue = 100;
					cherry = null;
				}
			}
			scoreText = "Total Score: " + score;
			totalScore.setText(scoreText);
			cherryScore.setText("next Cherry: " + cherryValue);
			cherryseaten.setText("Cherry's eaten: " + (tailLength - 4));
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
					pauseText.setText("PAUSED");
					timer.stop();
				} else {
					pauseText.setText("");
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
		snake.restart();
	}
}
