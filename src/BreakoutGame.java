import javax.swing.*;
import java.awt.*;

public class BreakoutGame extends JFrame {

    public BreakoutGame() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setTitle("Breakout Game");
        setSize(400, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            BreakoutGame game = new BreakoutGame();
            game.setVisible(true);
        });
    }
}
