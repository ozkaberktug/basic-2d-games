package GamePackage;

import java.awt.event.KeyAdapter;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class KAdapter extends KeyAdapter {
    GameClass game;
    KAdapter(GameClass game) {
        this.game = game;
    }
}

class MAdapter extends MouseAdapter {
    GameClass game;
    MouseEvent dragStart;
    MAdapter(GameClass game) {
        this.game = game;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(dragStart != null) {
            game.mouseSelectionDone(dragStart.getPoint(), e.getPoint());
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        dragStart = null;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if(dragStart == null) {
            dragStart = e;
        }
        else {
            game.mouseSelection(dragStart.getPoint(), e.getPoint());
        }
    }
}

abstract public class GameClass {

    abstract public void init();
    abstract public void close();
    abstract public void paint(Graphics g);
    abstract public void gameCycle();
    abstract public void mouseSelection(Point start, Point end);
    abstract public void mouseSelectionDone(Point start, Point end);

    GameClass() {
        keyAdapter = new KAdapter(this);
        mouseAdapter = new MAdapter(this);
        gameover = false;
        gamepaused = true;
        inGameTimer = 0;
		
		// only changed these values:
        width = 800;
        height = 600;
    }

    protected int width, height;
    private final KeyAdapter keyAdapter;
    private final MouseAdapter mouseAdapter;
    private boolean gameover;
    private boolean gamepaused;
    private long inGameTimer;

    public final void doCycle() {
        if (gamepaused) {
            return;
        }
        inGameTimer++;
        gameCycle();
    }

    public final boolean isOver() {
        return gameover;
    }

    public final boolean isPaused() {
        return gamepaused;
    }

    protected void doGamePause() {
        gamepaused = true;
    }

    protected void doGameResume() {
        gamepaused = false;
    }

    protected void doGameOver() {
        gameover = true;
    }

    protected final long getGameTime() {
        return inGameTimer;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    public final MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

}
