package GamePackage;

import java.awt.event.KeyAdapter;
import java.awt.Graphics;
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
    MAdapter(GameClass game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        game.mouseClicked(e);
    }
}

abstract public class GameClass {

    abstract public void init();
    abstract public void close();
    abstract public void paint(Graphics g);
    abstract public void gameCycle();
    abstract public void mouseClicked(MouseEvent e);

    GameClass() {
        keyAdapter = new KAdapter(this);
        mouseAdapter = new MAdapter(this);
        gameover = false;
        gamepaused = true;
        inGameTimer = 0;
		
		// only changed these values:
        width = 400;
        height = 400;
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
