package pong;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends Rectangle {
    
    int dy;
    
    public Player(int a, int b, int c, int d) {
        super(a,b,c,d);
    }
    
    public void update() {
        y += dy;
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) dy = -7;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) dy = 7;
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) dy = 0;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) dy = 0;
    }
    
}
