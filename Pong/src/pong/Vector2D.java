package pong;

public class Vector2D {
    
    private int x, y;
    
    public Vector2D(int i, int j) {
        x = i;
        y = j;
    }
    
    public void setX(int i) {
        x = i;
    }
    
    public void setY(int j) {
        y = j;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
}