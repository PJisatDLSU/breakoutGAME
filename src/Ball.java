import java.awt.*;

public class Ball {
    private int x, y, diameter, dx, dy;

    public Ball(int x, int y, int diameter, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;

        if (x <= 0 || x >= 400 - diameter) {
            dx = -dx;  // Bounce off left and right walls
        }

        if (y <= 0) {
            dy = -dy;  // Bounce off top wall
        }

        // Game over check moved to Board class
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }
}
