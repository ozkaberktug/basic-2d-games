package spacebattle;

public class Missile extends Sprite {
    
    private final int SPEED = 1;
    
    public Missile(int x, int y) {
        super(x, y);
        loadImage("missile.png");
        getImageDimensions();
    }
    
    public void move() {
        x += SPEED;
        if(x == 500) { x=500; visible=false; }
    }
    
}
