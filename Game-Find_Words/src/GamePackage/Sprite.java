package GamePackage;

// You may not need to include this file for every game project..
// You can change this file if necessary
// This file carries the general aspects of a sprite.
// You need to 'extend' for creating walls, enemies, player etc.
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.ImageIcon;

public class Sprite {

    protected int x, y, width, height;
    protected boolean visible;
    protected Image image;
    protected String id;

    public Sprite() {
        x = y = width = height = 0;
        visible = false;
        image = null;
    }

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        width = height = 0;
        visible = false;
        image = null;
    }

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        visible = false;
        image = null;
    }

    public void draw(Graphics g) {
        if (visible == false) {
            return;
        }
        if (image == null) {
            g.fillRect(x, y, width, height);
        } else {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void loadImage(String imageName) {
        URL imgURL = this.getClass().getResource(imageName);
        ImageIcon ii = new ImageIcon(imgURL);
        image = ii.getImage();
    }

    public void loadImage(Image image) {
        this.image = image;
    }

    public void setSizeByImage() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public final Image getImage() {
        return image;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final boolean isVisible() {
        return visible;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
