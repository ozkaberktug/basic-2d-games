package pacman;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
 
public class PacMan extends JFrame {
 
    private GameBoard board;
    public JLabel statusBar;
     
    public PacMan() {
        initUI();
    }
     
    private void initUI() {
        initMenuBar();
        initStatusBar();
        board = new GameBoard(statusBar);
        add(board);
        pack();
        setTitle("PACMAN");
        setResizable(false);
        setSize(board.getPreferredSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     
    private void initMenuBar() {
        JMenuBar mbar = new JMenuBar();
        JMenu menu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        JMenuItem howto = new JMenuItem("How to play?");
        menu.add(howto); menu.add(about);
        mbar.add(menu); setJMenuBar(mbar);
          
        about.addActionListener((e) -> { JOptionPane.showMessageDialog(this, "This game written by ozkaberktug, in 6 June 2019."); });
        howto.addActionListener((e) -> { JOptionPane.showMessageDialog(this, "<html><i>Arrow keys</i> to move.<br><i>ESC</i> to quit.<br><i>P</i> to pause/unpause.<br>Eat all dots, escape the ghosts!</html>"); });
    }
    
    private void initStatusBar() {
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        add(statusBar, BorderLayout.SOUTH);
    }
    
     
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PacMan app = new PacMan();
            app.setVisible(true);
        });
    }
     
}