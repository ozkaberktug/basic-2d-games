package pong;

import java.awt.Rectangle;
import java.util.Random;

public class Ball {
    
    public int x, y, r;
    public Vector2D velocity;
    
    public Ball(int i, int j, int radius) {
        x = i;
        y = j;
        r = radius;
        
        Random rnd = new Random();
        int vx = 5;
        int vy = 5;
        vx = (rnd.nextInt()%2 == 0)? vx : -vx;
        vy = (rnd.nextInt()%2 == 0)? vy : -vy;
        velocity = new Vector2D(vx,vy);
    }
    
    public void update() {
        x += velocity.getX();
        y += velocity.getY();
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, r*2, r*2);
    }
    
}
