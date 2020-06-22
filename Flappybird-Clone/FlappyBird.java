package flappybird;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


class GamePanel extends JPanel implements Runnable {
     
    private boolean gameover = false;
    private boolean gamestart = false;
     
    public GamePanel() {
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(360, 640));
        addKeyListener(new KAdapter());
        setBackground(Color.black);
    }
    
/******************************************************************************/
/******************************************************************************/
/******************************************************************************/
    
    final Image downflap = new ImageIcon("img/downflap.png").getImage();
    final Image upflap = new ImageIcon("img/upflap.png").getImage();
    final Image midflap = new ImageIcon("img/midflap.png").getImage();
    final Image bg_day = new ImageIcon("img/bg_day.png").getImage();
    final Image bg_night = new ImageIcon("img/bg_night.png").getImage();
    final Image base = new ImageIcon("img/base.png").getImage();
    final Image pause_screen = new ImageIcon("img/pause_screen.png").getImage();
    final Image image_gameover = new ImageIcon("img/gameover.png").getImage();
    final Image pipedown = new ImageIcon("img/pipedown.png").getImage();
    final Image pipeup = new ImageIcon("img/pipeup.png").getImage();
    final Image[] nums = { 
        new ImageIcon("0.png").getImage(),
        new ImageIcon("1.png").getImage(),
        new ImageIcon("2.png").getImage(),
        new ImageIcon("3.png").getImage(),
        new ImageIcon("4.png").getImage(),
        new ImageIcon("5.png").getImage(),
        new ImageIcon("6.png").getImage(),
        new ImageIcon("7.png").getImage(),
        new ImageIcon("8.png").getImage(),
        new ImageIcon("9.png").getImage()
    };
    final int GRAVITY = 1;
    final int GAP = 100;
    final int pos_x = 360 / 3;
    
    Image bird = upflap;
    Image background = bg_day;
    boolean flap = false;
    int acceleration, velocity;
    int pos_y = 640 / 2;
    int bg_x = 0, bg_y = 640 - base.getHeight(null);
    int score = 0;
    int bird_timer = 0;
    int[][] pipes = {
        { 400, 350 }, // [0] x, [1] y
        { 700, 460 }, 
        { 1000, 320 },
        { 1300, 500 }
    };
    
    private void resetValues() {
        bird = upflap;
        background = bg_day;
        flap = false;
        acceleration = 0;
        velocity = 0;
        pos_y = 640 / 2;
        bg_x = 0;
        bg_y = 640 - base.getHeight(null);
        score = 0;
        bird_timer = 0;
        pipes = new int[][] {
            { 400, 350 },
            { 700, 460 }, 
            { 1000, 320 },
            { 1300, 500 }
        };
    }
    
    private class KAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE)  {
                if(gameover) {
                    try {
                        Thread.sleep(500);
                    }catch(InterruptedException ex) {}
                    gameover = false; 
                    gamestart = true; 
                    resetValues();
                }
                if(!gamestart) gamestart = true;
                else flap = true;
            }
        }
    } // end KAdapter Class

    private void GameCycle() {
        if(!gamestart) return;
        
        if(!gameover) {
            
            // update pipes
            for (int[] pipe : pipes) 
                pipe[0] -= 5;
            if(pipes[0][0] + pipedown.getWidth(null) < 0) {
                for(int i=1; i<pipes.length; i++) {
                    int[] tmp = pipes[i-1];
                    pipes[i-1] = pipes[i];
                    pipes[i] = tmp;
                }
                pipes[3][0] = pipes[2][0] + 300;
                pipes[3][1] = (new Random()).nextInt()%200 + 280;
            }
        
            // scroll background
            bg_x -= 7;
            if(bg_x < -base.getWidth(null)/2)
                bg_x = 0;

            // do flapping
            if(flap) {
                flap = false;
                if(velocity >= 0) {
                    bird = midflap;
                    acceleration = 0;
                    velocity = -10;
                }
            } else {
                acceleration = GRAVITY;
            }
            velocity += acceleration;
            pos_y += velocity;
            if(pos_y <= 0) pos_y = 0;

            // update flap animation
            if(bird == midflap || bird == downflap) {
                if(bird_timer == 3)
                    bird = downflap;
                if(bird_timer == 6) {
                    bird = upflap;
                    bird_timer = 0;
                }
                bird_timer++;
            }
            
            // check for collisions
            if(pos_y + bird.getHeight(null) > bg_y)
                gameover = true;
            for (int[] pipe : pipes) {

                if( (pos_x + bird.getWidth(null) >= pipe[0]) && // entering gap
                    (pos_x <= pipe[0]+pipedown.getWidth(null)) && // exiting gap
                    ((pos_y+bird.getHeight(null) >= pipe[1]) || // hitting pipe
                                                     (pos_y <= pipe[1]-GAP)) ) {
                        gameover = true;
                }

                if( pos_x == pipe[0] ) {
                    score++;
                    if(score % 20 == 0) 
                        background = (background == bg_day)? bg_night : bg_day;
                }
            
            } 
            
        } // end-if(!gameover)
        
    } // end GameCycle()
     
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        //draw background
        g.drawImage(background, 0, 0, null);
        
        //draw base (scrolling)
        g.drawImage(base, bg_x, bg_y, null);
        g.drawImage(base, bg_x+base.getWidth(null), bg_y, null);
        
        if(gamestart || gameover) {
            
            //draw pipes
            for (int[] pipe : pipes) {
                g.drawImage(pipedown, pipe[0], pipe[1], null);
                g.drawImage(pipeup, pipe[0], pipe[1]-pipeup.getHeight(null)-GAP,
                        null);
            }
            
            //draw bird
            g.drawImage(bird, pos_x, pos_y, null);

            //draw score
            String score_str = String.valueOf(score);
            int w = score_str.length()*24;
            int start = (360-w)/2;
            for( int i = 0; i < score_str.length(); i++ )
                g.drawImage(nums[score_str.charAt(i)-'0'], start+i*24, 640/8,
                                                                          null);
        }
        
        if(!gamestart) {
            g.drawImage(pause_screen, (360 - pause_screen.getWidth(null))/2,
                                  640/5, null);
        }
        if(gameover) {
            g.drawImage(image_gameover, (360-image_gameover.getWidth(null))/2,
                                                                   640/4, null);
        }
        Toolkit.getDefaultToolkit().sync();
        
    } // end paintComponent(...)

/******************************************************************************/
/******************************************************************************/
/******************************************************************************/

    private Thread animator;
    private final int DELAY = 25;
    
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        int frameDropThreshold = 1000;
        beforeTime = System.currentTimeMillis();
        while(true) {
            GameCycle();
            repaint();
            timeDiff = System.currentTimeMillis()- beforeTime;
            sleep = DELAY-timeDiff;
            if(sleep < 0) {
                frameDropThreshold--;
                if(frameDropThreshold <= 0) {
                    System.out.println("[!] Low Frame Rate Detected!");
                    frameDropThreshold = 1000;
                }
            } else {
                try {
                    Thread.sleep(sleep);
                } catch(InterruptedException e) {}
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
 
 
public class FlappyBird extends JFrame {
 
    private GamePanel board;
     
    public FlappyBird() {
        initUI();
    }
     
    private void initUI() {
        setTitle("GAME");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new GamePanel();
        add(board);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FlappyBird app = new FlappyBird();
            app.setVisible(true);
        });
    }
     
}