package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
public class GamePanel extends JPanel implements Runnable {
     
    private boolean gameover = false;
    private boolean gamewin = false;
    private boolean gamepaused = true;
     
    private class KAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_P) gamepaused = !gamepaused;
            if(e.getKeyCode() == KeyEvent.VK_UP) player.keyPressed(e);
            if(e.getKeyCode() == KeyEvent.VK_DOWN) player.keyPressed(e);
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) player.keyReleased(e);
            if(e.getKeyCode() == KeyEvent.VK_DOWN) player.keyReleased(e);
        }
        
    }
    
    
    private Ball ball;
    private final Player player;
    private final Enemy enemy;
    private int scorePlayer, scoreEnemy;
    
    public GamePanel() {
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(new KAdapter());
        setBackground(Color.BLACK);
        ball = new Ball(390,290,10);
        player = new Player(100, 250, 15, 100);
        enemy = new Enemy(684, 250, 15, 100);
        scorePlayer = scoreEnemy = 0;
    }
     
    private void GameCycle() {
        if(gamepaused) return;
        
        ball.update();
        player.update();
        enemy.update(ball);
        
        if(player.y < 0)
            player.y = 0;
        if(player.y + player.height >= 600)
            player.y = 600 - player.height - 1;
        if(enemy.y < 0)
            enemy.y = 0;
        if(enemy.y + enemy.height >= 600)
            enemy.y = 600 - enemy.height - 1;
        
        if(player.intersects(ball.getBounds())) {
            ball.x = 116;
            ball.velocity.setX(-ball.velocity.getX());
        }
        if(enemy.intersects(ball.getBounds())) {
            ball.x = 683 - ball.r*2;
            ball.velocity.setX(-ball.velocity.getX());
        }
        
        if(ball.x < 0) {
            ball = new Ball(390,290,10);
            scoreEnemy++;
        }
        if(ball.x+ball.r*2 >= 800) {
            ball = new Ball(390,290,10);
            scorePlayer++;
        }
        if(ball.y < 0) {
            ball.y = 0;
            ball.velocity.setY(-ball.velocity.getY());
        }
        if(ball.y+ball.r*2 >= 600) {
            ball.y = 600 - ball.r*2 - 1;
            ball.velocity.setY(-ball.velocity.getY());
        }
        
        if(scoreEnemy > 9)
            gameover = true;
        if(scorePlayer > 9)
            gamewin = true;
 
    }
     
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fm;
        
        g.setColor(Color.WHITE);
        
        g.fillOval(ball.x, ball.y, ball.r*2, ball.r*2);
        g.fillRect(player.x, player.y, player.width, player.height);
        g.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);
        
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 72));
        String s = scorePlayer+" "+scoreEnemy;
        
        fm = g.getFontMetrics();
        g.drawString(s, (getWidth()-fm.stringWidth(s))/2, 100);
        
        if(gamepaused) {
            g.setColor(Color.white);
            g.fillRect((getWidth()-200)/2, (getHeight()-100)/2, 200, 100);
            g.setColor(Color.black);
            g.drawRect((getWidth()-180)/2, (getHeight()-80)/2, 180, 80);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            fm = g.getFontMetrics();
            g.drawString("PAUSED", (getWidth()-fm.stringWidth("PAUSED"))/2, (getHeight()-fm.getHeight())/2);
        }
        if(gameover) {
            g.setColor(Color.red);
            g.fillRect((getWidth()-200)/2, (getHeight()-100)/2, 200, 100);
            g.setColor(Color.white);
            g.drawRect((getWidth()-180)/2, (getHeight()-80)/2, 180, 80);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            fm = g.getFontMetrics();
            g.drawString("GAME OVER!", (getWidth()-fm.stringWidth("GAME OVER!"))/2, (getHeight()-fm.getHeight())/2);
        }
        if(gamewin) {
            g.setColor(Color.green);
            g.fillRect((getWidth()-200)/2, (getHeight()-100)/2, 200, 100);
            g.setColor(Color.red);
            g.drawRect((getWidth()-180)/2, (getHeight()-80)/2, 180, 80);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            fm = g.getFontMetrics();
            g.drawString("YOU WIN!", (getWidth()-fm.stringWidth("YOU WIN!"))/2, (getHeight()-fm.getHeight())/2);
        }
         
        Toolkit.getDefaultToolkit().sync();
    }

/******************************************************************************/
/******************************************************************************/
/******************************************************************************/

    private Thread animator;
    private final int DELAY = 10;
     
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        int frameDropThreshold = 1000;
        
        beforeTime = System.currentTimeMillis();
        while(!gameover && !gamewin) {
            GameCycle();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            
            if(DELAY-timeDiff < 0) {
                frameDropThreshold--;
                if(frameDropThreshold <= 0) {
                    System.out.println("[!] Low Frame Rate Detected!");
                    frameDropThreshold = 1000;
                }
                sleep = 0;
            } else {
                sleep = DELAY - timeDiff;
                try {
                    Thread.sleep(sleep);
                } catch(InterruptedException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
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
