package spacebattle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable {

    private final int DELAY = 10;
    private final int MAXALIENS = 30;
    
    private int score=0;
    private int controller;
    private boolean paused = false;
    private boolean gameover = false;
    private Thread animator;
    private final SpaceShip ship;
    private final ArrayList<Alien> aliens;
    
    private class KAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if(controller == SpaceBattle.MOUSE) return;
            ship.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(gameover) System.exit(0);
            if(e.getKeyCode() == KeyEvent.VK_ENTER) paused = !paused;
            if(controller == SpaceBattle.MOUSE) return;
            ship.keyPressed(e);
        }
    }
    
    private class MAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(controller == SpaceBattle.KEYBOARD || paused) return;
            ship.mouseClicked(e);
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            if(controller == SpaceBattle.KEYBOARD || paused) return;
            ship.mouseMoved(e);
        }
    }
    
    public Board(int controller) {
        setFocusable(true);
        addKeyListener(new KAdapter());
        addMouseMotionListener(new MAdapter());
        addMouseListener(new MAdapter());
        setBackground(Color.black);
        setPreferredSize(new Dimension(500,400));
        this.controller = controller;
        this.paused = true;
        ship = new SpaceShip();
        aliens = new ArrayList<>();
    }
    
    private void gameCycle() {
        if(paused) return;
        
        placeAliens();
        checkCollision();
        ship.move();
        ArrayList<Missile> missiles = ship.getMissiles();
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if (missile.isVisible()) 
                missile.move();
             else 
                missiles.remove(i);
        }
        for(Missile missile : missiles)
                missile.move();
        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);
            if (alien.isVisible()) 
                alien.move();
            else 
                aliens.remove(i);
        }
        for(Alien alien : aliens)
            alien.move();
    }
    
    public void setController(int controller) {
        this.controller = controller;
    }
    
    private void checkCollision() {
        Rectangle recShip = ship.getBounds();
        for(int i=0; i<aliens.size(); i++) {
            Rectangle recAlien = aliens.get(i).getBounds();
            if(recShip.intersects(recAlien)) {gameover = true; return;}
            
            for(Missile missile: ship.getMissiles()) {
                Rectangle recMissile = missile.getBounds();
                if(recMissile.intersects(recAlien)) { 
                    aliens.get(i).setVisible(false); 
                    score += Alien.POINT;
                }
            }
        }
        
    }
    
    private void placeAliens() {
        Random rnd = new Random();
        for(int i = aliens.size(); i<MAXALIENS; i++)
            aliens.add(new Alien(rnd.nextInt(500)+500,rnd.nextInt(400)));
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font f = new Font("TimesRoman", Font.BOLD, 15);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();
        
        g.setColor(Color.white);
        g.drawString("Score: "+score, 5, fm.getHeight());
        
        
        g.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
        
        for(Missile missile : ship.getMissiles())
            g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
        for(Alien alien : aliens)
            g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
        
        if(paused) {
            g.setColor(Color.white);
            g.fillRect(150, 150, 200, 100);
            g.setColor(Color.black);
            g.drawRect(160, 160, 180, 80);
            g.drawString("PAUSED", 250-fm.stringWidth("PAUSED")/2, 200);
        }
        
        if(gameover) {
            g.setColor(Color.white);
            g.fillRect(150, 150, 200, 100);
            g.setColor(Color.black);
            g.drawRect(160, 160, 180, 80);
            g.drawString("GAME OVER", 250-fm.stringWidth("GAME OVER")/2, 200);
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while(!gameover) {
            gameCycle();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = (DELAY-timeDiff < 0)? 2:DELAY - timeDiff;
            try {
                Thread.sleep(sleep);
            } catch(InterruptedException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            beforeTime = System.currentTimeMillis();
        }
    }
        
}
