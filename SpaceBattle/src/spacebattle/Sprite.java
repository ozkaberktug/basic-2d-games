package spacebattle;

import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.ImageIcon;

public class Sprite {
    protected int x, y, width, height;
    protected boolean visible;
    protected Image image;
    
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }
    
    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    protected void loadImage(String imageName) {
        URL imgURL = this.getClass().getResource(imageName);
        ImageIcon ii = new ImageIcon(imgURL);
        image = ii.getImage();
    }
    
    public Image getImage() {
        return image;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
