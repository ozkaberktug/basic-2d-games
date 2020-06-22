package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements Runnable {
     
    private Thread animator;
    private final int DELAY = 15;
    private boolean gameover = false;
    private boolean gamepaused = true;
    private boolean gamewin = false;
    
    private final JLabel statusBar;
    private final ArrayList<Rectangle> walls;
    private final ArrayList<Point> points;
    private final ArrayList<Enemy> enemies;
    private final Player p;
    private long score=0;
    
    private class KAdapter extends KeyAdapter {
        
        private boolean pressed = false;
        
        @Override
        public void keyPressed(KeyEvent e) {
            if(pressed) return;
            else pressed = true;
            if(e.getKeyCode() == KeyEvent.VK_P) gamepaused = !gamepaused;
            if(gamepaused) return;
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
            if(e.getKeyCode() == KeyEvent.VK_LEFT) { p.setDirection(Player.LEFT); p.setTimer(20); }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) { p.setDirection(Player.RIGHT); p.setTimer(20); }
            if(e.getKeyCode() == KeyEvent.VK_UP) { p.setDirection(Player.UP); p.setTimer(20); }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) { p.setDirection(Player.DOWN); p.setTimer(20); }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            pressed = false;
        }
        
    }
     
    public GameBoard(JLabel statusBar) {
        this.statusBar = statusBar;
        setFocusable(true);
        setPreferredSize(new Dimension(420, 460));
        addKeyListener(new KAdapter());
        setBackground(Color.black);
        
        walls = new ArrayList<>(500);
        // outer walls
        walls.add(new Rectangle( 1*20 , 0*20 , 19*20 , 1*20 ));
        walls.add(new Rectangle( 1*20 , 1*20 , 1*20 , 6*20 ));
        walls.add(new Rectangle( 2*20 , 6*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 4*20 , 7*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 0*20 , 8*20 , 4*20 , 1*20 ));
        walls.add(new Rectangle( 0*20 , 10*20 , 5*20 , 1*20 ));
        walls.add(new Rectangle( 4*20 , 11*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 1*20 , 12*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 1*20 , 13*20 , 1*20 , 8*20 ));
        walls.add(new Rectangle( 1*20 , 20*20 , 18*20 , 1*20 ));
        walls.add(new Rectangle( 19*20 , 1*20 , 1*20 , 6*20 ));
        walls.add(new Rectangle( 16*20 , 6*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 16*20 , 7*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 17*20 , 8*20 , 4*20 , 1*20 ));
        walls.add(new Rectangle( 16*20 , 10*20 , 5*20 , 1*20 ));
        walls.add(new Rectangle( 16*20 , 11*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 17*20 , 12*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 19*20 , 13*20 , 1*20 , 8*20 ));
        // fillers
        walls.add(new Rectangle( 0*20 , 0*20 , 1*20 , 8*20 ));
        walls.add(new Rectangle( 1*20 , 7*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 0*20 , 11*20 , 1*20 , 10*20 ));
        walls.add(new Rectangle( 1*20 , 11*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 20*20 , 0*20 , 1*20 , 8*20 ));
        walls.add(new Rectangle( 17*20 , 7*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 20*20 , 11*20 , 1*20 , 10*20 ));
        walls.add(new Rectangle( 17*20 , 11*20 , 3*20 , 1*20 ));
        
        // inner walls
        walls.add(new Rectangle( 3*20 , 2*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 6*20 , 2*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 10*20 , 1*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 12*20 , 2*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 16*20 , 2*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 3*20 , 4*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 6*20 , 4*20 , 1*20 , 5*20 ));
        walls.add(new Rectangle( 8*20 , 4*20 , 5*20 , 1*20 ));
        walls.add(new Rectangle( 14*20 , 4*20 , 1*20 , 5*20 ));
        walls.add(new Rectangle( 16*20 , 4*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 10*20 , 5*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 7*20 , 6*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 12*20 , 6*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 6*20 , 10*20 , 1*20 , 3*20 ));
        walls.add(new Rectangle( 14*20 , 10*20 , 1*20 , 3*20 ));
        walls.add(new Rectangle( 8*20 , 12*20 , 5*20 , 1*20 ));
        walls.add(new Rectangle( 10*20 , 13*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 3*20 , 14*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 16*20 , 14*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 4*20 , 15*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 16*20 , 15*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 6*20 , 14*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 12*20 , 14*20 , 3*20 , 1*20 ));
        walls.add(new Rectangle( 2*20 , 16*20 , 1*20 , 1*20 ));
        walls.add(new Rectangle( 18*20 , 16*20 , 1*20 , 1*20 ));
        walls.add(new Rectangle( 6*20 , 16*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 14*20 , 16*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 10*20 , 17*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 10*20 , 17*20 , 1*20 , 2*20 ));
        walls.add(new Rectangle( 3*20 , 18*20 , 6*20 , 1*20 ));
        walls.add(new Rectangle( 12*20 , 18*20 , 6*20 , 1*20 ));
        walls.add(new Rectangle( 8*20 , 16*20 , 5*20 , 1*20 ));
        // ghost tank
        walls.add(new Rectangle( 8*20 , 8*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 11*20 , 8*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 8*20 , 10*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 11*20 , 10*20 , 2*20 , 1*20 ));
        walls.add(new Rectangle( 10*20 , 9*20 , 1*20 , 1*20 ));
        
        
        p = new Player();

        enemies = new ArrayList<>(10);
        enemies.add(new Enemy(9,9));
        enemies.add(new Enemy(10,10));
        enemies.add(new Enemy(11,9));
        enemies.add(new Enemy(10,8));
        enemies.add(new Enemy(8,9));
        enemies.add(new Enemy(12,9));
        
        points = new ArrayList<>(200);
        
        
        for(int w=0; w<420; w += 20) 
            for(int h=0; h<420; h += 20)
                points.add(new Point(w+8, h+8));
        
        for(int i=0; i<points.size(); i++) {
            Point point = points.get(i);
            for(Rectangle r : walls) {
                if(r.contains(point)) {
                    points.remove(point);
                    i = -1;
                    break;
                }
            }
        }

        
        
        
            
        
    }
     
    private void GameCycle() {
        if(gamepaused) return;
        
        p.update();
        for(Enemy enemy : enemies)
            enemy.update();
        
        for(Rectangle r : walls) {
            Rectangle ri = r.intersection(p.getBounds());
            if(!ri.isEmpty()) {
                p.setTimer(15); // stop animation
                if(ri.width == 1)
                    if(ri.x == r.x)
                        p.setX(p.getX()-p.getSpeed());
                    else
                        p.setX(p.getX()+p.getSpeed());
                else if(ri.height == 1)
                    if(ri.y == r.y)
                        p.setY(p.getY()-p.getSpeed());
                    else
                        p.setY(p.getY()+p.getSpeed());
            }
            
            for(Enemy enemy : enemies) {
                ri = r.intersection(enemy.getBounds());
                if(!ri.isEmpty()) {
                    enemy.setTimer(0);
                    if(ri.width == 1)
                        if(ri.x == r.x)
                            enemy.setX(enemy.getX()-enemy.getSpeed());
                        else
                            enemy.setX(enemy.getX()+enemy.getSpeed());
                    else if(ri.height == 1)
                        if(ri.y == r.y)
                            enemy.setY(enemy.getY()-enemy.getSpeed());
                        else
                            enemy.setY(enemy.getY()+enemy.getSpeed());
                    enemy.changeDirection();
                }
                    
            }
        }
        
        for(int i=0; i<points.size(); i++) {
            Point r = points.get(i);
            if(p.getBounds().contains(r)) {
                points.remove(i);
                score++;
            }
        }
        statusBar.setText("Score: "+score);
        
        for(Enemy enemy : enemies)
            if(p.getBounds().intersects(enemy.getBounds()))
                gameover = true;
        
        if(points.isEmpty()) gamewin = true;
        
    }
     
    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        FontMetrics fm;
        
        g.setColor(Color.cyan);
        for(Rectangle wall : walls)
            g.fillRect(wall.x, wall.y, wall.width, wall.height);
        g.setColor(Color.blue);
        for(Point r : points)
            g.fillRect(r.x, r.y, 4, 4);
        for(Enemy enemy : enemies)
            g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getHeight(), enemy.getWidth(), this);
        g.drawImage(p.getImage(), p.getX(), p.getY(), p.getHeight(), p.getWidth(), this);
        
        /*g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        fm = g.getFontMetrics();
        g.drawString("Score: "+score, 0, 465-fm.getMaxAdvance());*/
        
        if(gamepaused) {
            g.setColor(Color.white);
            g.fillRect((getWidth()-200)/2, (getHeight()-100)/2, 200, 100);
            g.setColor(Color.black);
            g.drawRect((getWidth()-180)/2, (getHeight()-80)/2, 180, 80);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));
            fm = g.getFontMetrics();
            g.drawString("PAUSED", (getWidth()-fm.stringWidth("PAUSED"))/2, (getHeight()-fm.getHeight())/2);
        }
        if(gameover) {
            g.setColor(Color.red);
            g.fillRect((getWidth()-200)/2, (getHeight()-100)/2, 200, 100);
            g.setColor(Color.white);
            g.drawRect((getWidth()-180)/2, (getHeight()-80)/2, 180, 80);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));
            fm = g.getFontMetrics();
            g.drawString("GAME OVER!", (getWidth()-fm.stringWidth("GAME OVER!"))/2, (getHeight()-fm.getHeight())/2);
        }
        if(gamewin) {
            g.setColor(Color.green);
            g.fillRect((getWidth()-200)/2, (getHeight()-100)/2, 200, 100);
            g.setColor(Color.white);
            g.drawRect((getWidth()-180)/2, (getHeight()-80)/2, 180, 80);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));
            fm = g.getFontMetrics();
            g.drawString("YOU WIN!", (getWidth()-fm.stringWidth("YOU WIN!"))/2, (getHeight()-fm.getHeight())/2);
        }
         
        Toolkit.getDefaultToolkit().sync();
    }
     
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while(gameover == false && gamewin == false) {
            GameCycle();
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
     
    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
     
}
