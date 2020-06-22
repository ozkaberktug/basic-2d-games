package pong;

import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Rectangle {
    
    public Enemy(int a, int b, int c, int d) {
        super(a,b,c,d);
    }
    
    public void update(Ball ball) {
        int finish = ball.y + ball.r;
        int topDist = finish - y;
        int bottomDist = finish - y - height;
        Random rnd = new Random();
        if( Math.abs(topDist) < Math.abs(bottomDist) )
            y = (topDist < 0)? y-5 : y+5;
        else
            y = (bottomDist < 0)? y-5 : y+5;
    }
    
}
