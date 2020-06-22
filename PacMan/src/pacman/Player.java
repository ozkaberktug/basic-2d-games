package pacman;

public class Player extends Sprite {
    
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    private final int SPEED = 1;

    private int direction = RIGHT;
    private long timer = 0;
    
    public Player() {
        super(10*20,15*20+1, 18, 18);
        loadImage("p0.png");
    }
    
    public void update() {
        
        timer++;
        
        switch(direction) {
        case LEFT:
            if(timer % 60 == 0)
                loadImage("p0.png");
            else if(timer % 60 == 20)
                loadImage("pl1.png");
            else if(timer % 60 == 40)
                loadImage("pl2.png");
            x -= SPEED;
            break;
        case RIGHT:
            if(timer % 60 == 0)
                loadImage("p0.png");
            else if(timer % 60 == 20)
                loadImage("pr1.png");
            else if(timer % 60 == 40)
                loadImage("pr2.png");
            x += SPEED;
            break;
        case UP:
            if(timer % 60 == 0)
                loadImage("p0.png");
            else if(timer % 60 == 20)
                loadImage("pu1.png");
            else if(timer % 60 == 40)
                loadImage("pu2.png");
            y -= SPEED;
            break;
        case DOWN:
            if(timer % 60 == 0)
                loadImage("p0.png");
            else if(timer % 60 == 20)
                loadImage("pd1.png");
            else if(timer % 60 == 40)
                loadImage("pd2.png");
            y += SPEED;
            break;
        }
        
        if(x >= 420)
            x = -width;
        if(x+width < 0)
            x = 420;
        
    }
    
    public void setDirection(int d) {
        direction = d;
        switch(direction) {
        case LEFT:
            loadImage("pl1.png");
            break;
        case RIGHT:
            loadImage("pr1.png");
            break;
        case UP:
            loadImage("pu1.png");
            break;
        case DOWN:
            loadImage("pd1.png");
            break;
        }
    }
    public int getDirection() {
        return direction;
    }
    public void setTimer(long t) {
        timer = t;
    }
    public int getSpeed() {
        return SPEED;
    }
    
}
