package GamePackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Game extends GameClass {

    Sprite[] alphabet = new Sprite[26];
    Sprite[][] matrix = new Sprite[12][12];
    Point selectionStart, selectionEnd;
    Point selectionStartFinal, selectionEndFinal;
    ArrayList<ArrayList<Point>> selections = new ArrayList<>();
    ArrayList<String> wordList = new ArrayList<>();

    @Override
    public void init() {
        Random rnd = new Random();
        for (int i = 0; i < alphabet.length; i++) {
            String imagename = "images/" + i + ".png";
            alphabet[i] = new Sprite();
            alphabet[i].loadImage(imagename);
            alphabet[i].setId(Character.toString((char) (i + 'A')));
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                Sprite randomSprite = alphabet[rnd.nextInt(alphabet.length)];
                matrix[i][j] = new Sprite(j * 50, i * 50, 50, 50);
                matrix[i][j].loadImage(randomSprite.getImage());
                matrix[i][j].setId(randomSprite.getId());
                matrix[i][j].setVisible(true);
            }
        }

        for (int i = 0; i < 10; i++) {
            int row, col, len;
            boolean dir = rnd.nextBoolean();
            StringBuilder sb = new StringBuilder();
            if (dir) {   // horizontal words
                row = rnd.nextInt(12);
                len = rnd.nextInt(7) + 3;
                col = rnd.nextInt(12-len);
                for (int j = 0; j < len; j++) {
                    sb.append(matrix[row][col + j].getId());
                }
            } else {      // vertical words
                col = rnd.nextInt(12);
                len = rnd.nextInt(7) + 3;
                row = rnd.nextInt(12-len);
                for (int j = 0; j < len; j++) {
                    sb.append(matrix[row + j][col].getId());
                }
            }
            wordList.add(sb.toString());
        }

        doGameResume();
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        if (selectionStart != null && selectionEnd != null) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(50, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(selectionStart.x, selectionStart.y, selectionEnd.x, selectionEnd.y);
        }

        g.setColor(Color.BLUE);
        for (ArrayList<Point> e : selections) {
            for (Point p : e) {
                Sprite sp = matrix[p.y][p.x];
                g.fillRect(sp.x, sp.y, sp.width, sp.height);
            }
        }

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                matrix[i][j].draw(g);
            }
        }

        g.setStroke(new BasicStroke());
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        for (int i = 1; i <= wordList.size(); i++) {
            String s = wordList.get(i - 1);
            g.drawString(s, 600 + (200 - fm.stringWidth(s)) / 2, fm.getAscent() * i * 2);
        }

        String elapsedTime = (getGameTime() * GamePanel.DELAY / 1000) + " sec";
        g.drawString(elapsedTime, 600 + (200 - fm.stringWidth(elapsedTime)) / 2, 600 - fm.getAscent());

    }

    @Override
    public void gameCycle() {

        if (wordList.isEmpty()) {
            doGameOver();
        }

        if (selectionStartFinal != null && selectionEndFinal != null) {
            Point start = new Point(selectionStartFinal.x / 50, selectionStartFinal.y / 50);
            Point end = new Point(selectionEndFinal.x / 50, selectionEndFinal.y / 50);
            if (start.x < 12 && start.y < 12 && end.x < 12 && end.y < 12) {
                ArrayList<Point> selection = new ArrayList<>();
                if (start.y == end.y) { // horizontal selection
                    if (start.x > end.x) {
                        Point tmp = new Point(start);
                        start = end;
                        end = tmp;
                    }
                    while (start.x <= end.x) {
                        selection.add(new Point(start));
                        start.x++;
                    }
                } else if (start.x == end.x) {  // vertical selection
                    if (start.y > end.y) {
                        Point tmp = new Point(start);
                        start = end;
                        end = tmp;
                    }
                    while (start.y <= end.y) {
                        selection.add(new Point(start));
                        start.y++;
                    }
                }

                StringBuilder sb = new StringBuilder();
                for (Point p : selection) {
                    sb.append(matrix[p.y][p.x].getId());
                }

                if (wordList.contains(sb.toString())) {
                    selections.add(selection);
                    wordList.remove(sb.toString());
                }
            }
            selectionStartFinal = null;
            selectionEndFinal = null;
        }
    }

    @Override
    public void mouseSelection(Point start, Point end) {
        this.selectionStart = start;
        this.selectionEnd = end;
    }

    @Override
    public void mouseSelectionDone(Point start, Point end) {
        this.selectionStart = null;
        this.selectionEnd = null;
        this.selectionStartFinal = start;
        this.selectionEndFinal = end;
    }

    @Override
    public void close() {
        JOptionPane.showMessageDialog(null, "You have found all words!", "Bravo!", JOptionPane.INFORMATION_MESSAGE);
    }

}
