package pacman;

import java.util.Random;

public class Enemy extends Sprite {
    
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    
    private int SPEED=1;
    private int direction = LEFT;
    private long timer = 0;
    private final long changeDirectionTimer;
    Random rnd;
    
    public Enemy(int a, int b) {
        super(a*20+1,b*20+1,18,18);
        rnd = new Random();
        direction = rnd.nextInt(DOWN) + LEFT;
        changeDirectionTimer = rnd.nextInt(50);
        loadImage("e0.png");
    }
    
    public void update() {
        
        timer++;
        
        if(timer == changeDirectionTimer) {
            changeDirection();
            SPEED = rnd.nextInt(1)+1;
        }
        
        switch(direction) {
        case LEFT:
            if(timer % 60 == 0)
                loadImage("e0.png");
            else if(timer % 60 == 30)
                loadImage("e1.png");
            x -= SPEED;
            break;
        case RIGHT:
            if(timer % 60 == 0)
                loadImage("e0.png");
            else if(timer % 60 == 30)
                loadImage("e1.png");
            x += SPEED;
            break;
        case UP:
            if(timer % 60 == 0)
                loadImage("e0.png");
            else if(timer % 60 == 30)
                loadImage("e1.png");
            y -= SPEED;
            break;
        case DOWN:
            if(timer % 60 == 0)
                loadImage("e0.png");
            else if(timer % 60 == 30)
                loadImage("e1.png");
            y += SPEED;
            break;
        }
        
        if(x >= 420)
            x = -width;
        if(x+width < 0)
            x = 420;
    }
    
    public void changeDirection() {
        direction = rnd.nextInt(DOWN) + LEFT;
    }
    
    public int getSpeed() {
        return SPEED;
    }
    
    public void setTimer(long t) {
        timer = t;
    }
}
