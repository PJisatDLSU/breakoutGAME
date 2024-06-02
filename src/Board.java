import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Board extends JPanel implements ActionListener, MouseMotionListener {

    private Timer timer;
    private final int DELAY = 10;
    private final int BRICK_ROWS = 10;
    private final int BRICKS_PER_ROW = 10;
    private final int BRICK_HEIGHT = 10;
    private Brick[][] bricks;
    private Paddle paddle;
    private Ball ball;
    private int score;
    private boolean gameOver;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 620));

        // Add mouse motion listener
        addMouseMotionListener(this);

        // Defer the initialization of components until after the component is added to the JFrame
        EventQueue.invokeLater(this::initComponents);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initComponents() {
        int brickWidth = (getWidth() - 20) / BRICKS_PER_ROW - 10;  // Subtract 10 for spacing, with an additional margin

        bricks = new Brick[BRICK_ROWS][BRICKS_PER_ROW];
        Color[] colors = {Color.CYAN, Color.CYAN, Color.GREEN, Color.GREEN, Color.YELLOW, Color.YELLOW, Color.ORANGE, Color.ORANGE, Color.RED, Color.RED};

        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICKS_PER_ROW; col++) {
                int x = col * (brickWidth + 10) + 10;  // Add 10 for offset
                int y = row * (BRICK_HEIGHT + 10) + 10;
                bricks[row][col] = new Brick(x, y, brickWidth, BRICK_HEIGHT, colors[row]);
            }
        }

        paddle = new Paddle(200 - 30, 570, 60, 10);  // Positioned slightly higher
        ball = new Ball(200, 550, 10, 2, 2);  // Added dx and dy for ball movement
        score = 0;
        gameOver = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ball.move();
            checkCollision();
            repaint();
        }
    }

    private void checkCollision() {
        // Check ball collision with paddle
        if (ball.getRect().intersects(paddle.getRect())) {
            ball.setDy(-ball.getDy());  // Reverse ball direction
        }

        // Check ball collision with bricks
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICKS_PER_ROW; col++) {
                Brick brick = bricks[row][col];
                if (brick != null && ball.getRect().intersects(brick.getRect())) {
                    ball.setDy(-ball.getDy());  // Reverse ball direction
                    bricks[row][col] = null;    // Remove the brick
                    score++;                    // Increase score
                }
            }
        }

        // Check ball collision with walls
        if (ball.getY() >= 620 - ball.getDiameter()) {
            gameOver = true;  // Set game over if ball hits the bottom wall
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw bricks
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICKS_PER_ROW; col++) {
                if (bricks[row][col] != null) {
                    bricks[row][col].draw(g);
                }
            }
        }

        // Draw paddle and ball
        if (paddle != null) {
            paddle.draw(g);
        }
        if (ball != null) {
            ball.draw(g);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        g.drawString("" + score, getWidth() / 2 -10, getHeight() / 2 + 80);

        // Draw game over message
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            int mouseX = e.getX();
            paddle.setX(mouseX - paddle.getWidth() / 2);  // Center the paddle on the mouse
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);  // Handle dragging similarly to moving
    }
}
