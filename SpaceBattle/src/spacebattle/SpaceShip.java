package spacebattle;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SpaceShip extends Sprite {

    private int dx, dy;
    public ArrayList<Missile> missiles;
    
    public SpaceShip() {
        super(0, 200);
        dx=dy=0;
        loadImage("craft.png");
        getImageDimensions();
        missiles = new ArrayList<>();
    }
    
    public ArrayList<Missile> getMissiles() {
        return missiles;
    }
    
    public void move() {
        x += dx;
        y += dy;
        if(x < 0) x = 0;
        if(x+width >= 500) x= 500 - width;
        if(y < 0) y = 0;
        if(y+height*2 >= 400) y= 400 - height*2; // !?
    }
    
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            dx = -2;
            break;
        case KeyEvent.VK_RIGHT:
            dx = 2;
            break;
        case KeyEvent.VK_UP:
            dy = -2;
            break;
        case KeyEvent.VK_DOWN:
            dy = 2;
            break;
        case KeyEvent.VK_SPACE:
            missiles.add(new Missile(x+width, y+height/2));
            break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            dx = 0;
            break;
        case KeyEvent.VK_RIGHT:
            dx = 0;
            break;
        case KeyEvent.VK_UP:
            dy = 0;
            break;
        case KeyEvent.VK_DOWN:
            dy = 0;
            break;
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    
    public void mouseClicked(MouseEvent e) {
        missiles.add(new Missile(x+width, y+height/2));
    }
    
}
