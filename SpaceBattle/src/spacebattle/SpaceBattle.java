package spacebattle;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class SpaceBattle extends JFrame {
    
    public static final int MOUSE = 1;
    public static final int KEYBOARD = 2;
    public static final String helpText = "<html>Press ENTER to pause.<br>Select control mode (keyboard|mouse) under Game->Controll<br>(Default) Keyboard Buttons: Arrow Keys and Space<br>Mouse: Movement and Left Click</html>";
    
    private int controller = KEYBOARD;
    private Board board;
    
    public SpaceBattle() {
        initUI();
    }
    
    private void initUI() {
        board = new Board(controller);
        add(board);
        pack();
        setSize(500, 400);
        setTitle("Space Battle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e) -> {
            System.exit(0);
        });
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        JMenuItem howtoplay = new JMenuItem("How to play?");
        about.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, "This game written by Berktug Kaan Ozkan, in 30 May 2019.",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });
        howtoplay.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, helpText, "How To Play", JOptionPane.INFORMATION_MESSAGE);
        });
        
        ButtonGroup group = new ButtonGroup();
        JMenu controls = new JMenu("Controls..");
        JRadioButtonMenuItem mouse = new JRadioButtonMenuItem("Mouse");
        mouse.addItemListener((e)->{
            if (e.getStateChange() == ItemEvent.SELECTED)
                controller = MOUSE;
            board.setController(controller);
        });
        group.add(mouse);
        JRadioButtonMenuItem keyboard = new JRadioButtonMenuItem("Keyboard");
        keyboard.addItemListener((e)->{
            if (e.getStateChange() == ItemEvent.SELECTED)
                controller = KEYBOARD;
            board.setController(controller);
        });
        keyboard.setSelected(true);
        group.add(keyboard);
        
        controls.add(mouse); controls.add(keyboard);
        gameMenu.add(controls); gameMenu.add(exit);
        helpMenu.add(about); helpMenu.add(howtoplay);
        menuBar.add(gameMenu); menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        
        JOptionPane.showMessageDialog(this, helpText, "How To Play", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SpaceBattle app = new SpaceBattle();
            app.setVisible(true);
        });
    }

}
