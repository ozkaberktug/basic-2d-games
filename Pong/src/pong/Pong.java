package pong;

import java.awt.EventQueue;
import javax.swing.JFrame;
 
public class Pong extends JFrame {
 
    private GamePanel board;
     
    public Pong() {
        initUI();
    }
     
    private void initUI() {
        setTitle("GAME");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new GamePanel();
        add(board);
        pack();
        setLocationRelativeTo(null);
    }
     
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Pong app = new Pong();
            app.setVisible(true);
        });
    }
     
}
