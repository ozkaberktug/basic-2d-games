package GamePackage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class Game extends GameClass {

    Sprite background;
    Sprite[][] matrix = new Sprite[3][3];
    Point clickedPoint;
    String turn = "x";
    String game_state = "continue";
    
    @Override
    public void init() {
        background = new Sprite();
        background.setVisible(true);
        background.loadImage("images/bg.png");
        background.setSizeByImage();
        
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                matrix[i][j] = new Sprite(20 + 126 * j,20 + 126 * i, 106, 106);
                matrix[i][j].setVisible(true);
                matrix[i][j].setId("");
            }
        }
        
        doGameResume();
    }

    @Override
    public void close() {
        String msg = game_state + "\nPlay time: " + (getGameTime() * GamePanel.DELAY / 1000.0) + " seconds";
        JOptionPane.showMessageDialog(null, msg, game_state, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void paint(Graphics g) {
        background.draw(g);
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(!matrix[i][j].getId().equals(""))
                    matrix[i][j].draw(g);
    }

    @Override
    public void gameCycle() {
        
        // process the input
        if(clickedPoint != null) {
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    if(matrix[i][j].getBounds().contains(clickedPoint) && matrix[i][j].getId().equals("")) {
                        if(turn.equals("x")) {
                            matrix[i][j].loadImage("images/x.png");
                            matrix[i][j].setId(turn);
                            turn = "o";
                        }
                        else {
                            matrix[i][j].loadImage("images/o.png");
                            matrix[i][j].setId(turn);
                            turn = "x";
                        }
                    }
                }
            }
            clickedPoint = null;
        }
        
        // check for game state
        if ((matrix[0][0].getId().equals("x") && matrix[0][1].getId().equals("x") && matrix[0][2].getId().equals("x")) || (matrix[0][0].getId().equals("x") && matrix[1][1].getId().equals("x") && matrix[2][2].getId().equals("x")) || (matrix[0][0].getId().equals("x") && matrix[1][0].getId().equals("x") && matrix[2][0].getId().equals("x")) || (matrix[0][2].getId().equals("x") && matrix[1][2].getId().equals("x") && matrix[2][2].getId().equals("x")) || (matrix[2][0].getId().equals("x") && matrix[1][1].getId().equals("x") && matrix[0][2].getId().equals("x")) || (matrix[2][2].getId().equals("x") && matrix[2][1].getId().equals("x") && matrix[2][0].getId().equals("x")) || (matrix[0][1].getId().equals("x") && matrix[1][1].getId().equals("x") && matrix[2][1].getId().equals("x")) || (matrix[1][0].getId().equals("x") && matrix[1][1].getId().equals("x") && matrix[1][2].getId().equals("x")))
            game_state = "X Wins!";
        else if ((matrix[0][0].getId().equals("o") && matrix[0][1].getId().equals("o") && matrix[0][2].getId().equals("o")) || (matrix[0][0].getId().equals("o") && matrix[1][1].getId().equals("o") && matrix[2][2].getId().equals("o")) || (matrix[0][0].getId().equals("o") && matrix[1][0].getId().equals("o") && matrix[2][0].getId().equals("o")) || (matrix[0][2].getId().equals("o") && matrix[1][2].getId().equals("o") && matrix[2][2].getId().equals("o")) || (matrix[2][0].getId().equals("o") && matrix[1][1].getId().equals("o") && matrix[0][2].getId().equals("o")) || (matrix[2][2].getId().equals("o") && matrix[2][1].getId().equals("o") && matrix[2][0].getId().equals("o")) || (matrix[0][1].getId().equals("o") && matrix[1][1].getId().equals("o") && matrix[2][1].getId().equals("o")) || (matrix[1][0].getId().equals("o") && matrix[1][1].getId().equals("o") && matrix[1][2].getId().equals("o")))
            game_state = "O Wins!";
        
        boolean allCellsMarked = true;
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(matrix[i][j].getId().equals(""))
                    allCellsMarked = false;
        if(allCellsMarked) game_state = "It\'s a Tie!";
        
        if(!game_state.equals("continue")) {
            doGameOver();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickedPoint = e.getPoint();
    }

}
