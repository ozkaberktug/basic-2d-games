package spacebattle;

public class Alien extends Sprite {

    private final int SPEED = -1;
    public static final int POINT = 10;
    
    public Alien(int x, int y) {
        super(x,y);
        loadImage("alien.png");
        getImageDimensions();
    }
    
    public void move() {
        x += SPEED;
        if(x == 0) { x=0; visible=false; }
    }
}
