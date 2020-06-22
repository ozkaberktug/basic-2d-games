package GamePackage;

// IMPORTANT!
// Do NOT change this file! Except setTitle parameter...duh
// Changes can only be done in Game.java and specified part of GameAbstract.java

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class GamePanel extends JPanel implements Runnable {

    private final Game game;
    private Thread animator;
    public static final int DELAY = 30;

    public GamePanel() {
        super();
        game = new Game();
        game.init();
        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(game.getWidth(), game.getHeight()));
        addKeyListener(game.getKeyAdapter());
        addMouseListener(game.getMouseAdapter());
        addMouseMotionListener(game.getMouseAdapter());
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.paint(g);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        int frameDropThreshold = 10;

        beforeTime = System.currentTimeMillis();
        repaint();
        while (!game.isOver()) {
            game.doCycle();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;

            if (DELAY - timeDiff < 0) {
                frameDropThreshold--;
                if (frameDropThreshold <= 0) {
                    JOptionPane.showMessageDialog(this, "Low Frame Rate!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            } else {
                sleep = DELAY - timeDiff;
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }

            beforeTime = System.currentTimeMillis();
        }
        game.close();
        repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

}

public class GameFrame extends JFrame {

    public GameFrame() {
        super();
        initUI();
    }

    private void initUI() {
        setTitle("Find Words");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameFrame app = new GameFrame();
            app.setVisible(true);
        });
    }

}