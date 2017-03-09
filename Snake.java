import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Snake implements ActionListener, KeyListener {
	
	public static final int SCALE = 38, ROW = 25, COLUMN = 25, SPEED = 90; //Sets values for the size of the board
	public static final int DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3; //sets values for directions

	public static ArrayList<Point> SnakeParts = new ArrayList<Point>(); // represents the snake's body
	public static Point cherry;
	public static int direction = DOWN; //snake starts going downward

	/*
	 * Setting variables that will be used to initialize the GUI
	 */
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
	private Point head = new Point(0, 0); // starts the snake at point (0,0) on the grid

	/*
	 * Finalizes board size
	 * initializes snake length 4, score to 0, and counter to 100
	 * sets snake moving to true 
	 */
	private boolean over = false, pause = false, moved = true;
	private final int WIDTH = COLUMN * SCALE + 10 * SCALE,
			HEIGHT = ROW * SCALE;
	private int tailLength = 4, cherryValue = 100, score = 0;

	/*
	 * creates the main GUI
	 * Adjusts all the label settings
	 * starts the timer
	 */
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

	/*
	 * returns all variables to their starting values
	 */
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

	/*
	 * returns true if any part of the snake is on the given coordinates
	 * returns false if the given coordinates don't contain any snake parts
	 */
	private boolean tailAt(int x, int y) {
		for (Point point : SnakeParts)
			if (point.x == x && point.y == y)
				return true;
		return false;
	}

	/*
	 * This Block of code makes the snake respond to actions the user makes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!over && !pause) { //will not run if game is paused or over
			render.repaint();
			
			//Adds location of head to the snake body
			//uses the comparison of size of snake to tail length to determine if a cherry has been collected
			//if cherry is collected the last point of the snake is removed so snake length is consistent
			//if cherry was collected the last part of the snake remains so the snake grows by one
			SnakeParts.add(new Point(head.x, head.y));
			if (SnakeParts.size() > tailLength + 1)
				SnakeParts.remove(0);
			
			//Moves snake one position in the direction it was moving
			//if snake hits the wall or its own tail ends the game
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
			
			//Sets a new random location for the cherry
			//if new point was on the snake a new point is selected
			while (cherry == null) {
				cherry = new Point(rand.nextInt(COLUMN), rand.nextInt(ROW));
				for (Point point : SnakeParts) {
					if (cherry.equals(point)) {
						cherry = null;
						break;
					}
				}
			}
			
			//decreases the value of the cherry if it has not been collected
			if (cherry != null) {
				cherryValue--;
				
				//checks if cherry is collected
				//increases the tail length
				//adds the current value of the cherry to your score
				//resets the value of the cherry to 100
				//sets the cherry to null so it can be replaced
				
				if (head.equals(cherry)) {
					tailLength++;
					score = score + cherryValue;
					cherryValue = 100;
					cherry = null;
				}
			}
			
			//Updates the side panel
			scoreText = "Total Score: " + score;
			totalScore.setText(scoreText);
			cherryScore.setText("next Cherry: " + cherryValue);
			cherryseaten.setText("Cherry's eaten: " + (tailLength - 4));
		}
		// System.out.println(head.x + ", " + head.y);
		// if(cherry != null)
		// System.out.println(cherry.x + ", " + cherry.y);
	}

	/*
	 * Changes direction of the snake if keys (A,S,W,D or Up, Down, Left, Right) are clicked
	 * If the space bar is clicked, pauses, unpauses, or restarts depending on the game state
	 */
	
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
