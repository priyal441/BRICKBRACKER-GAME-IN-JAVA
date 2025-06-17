package brickBracker;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

  private boolean play = false;
  private int score = 0;

  private int totalBricks = 21;
  private Timer Timer;
  private int delay = 8;

  private int playerX = 310;
  private int ballpsoX = 120;
  private int ballpsoY = 350;
  private int ballXdir = -1;
  private int ballYdir = -2;
  private MapGenerator map;

  public Gameplay() {
    map = new MapGenerator(3, 7);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    Timer = new Timer(delay, this);
    Timer.start();

  }

  public void paint(Graphics g) {
    // background
    g.setColor(Color.black);
    g.fillRect(1, 1, 692, 592);

    // drawing map
    map.draw((Graphics2D) g);
    // borders
    g.setColor(Color.yellow);
    g.fillRect(0, 0, 3, 592); // left border
    g.fillRect(0, 0, 692, 3); // top border
    g.fillRect(0, 0, 692, 3); // right border
    g.fillRect(691, 0, 3, 592); // bottom borde


    //scores
    g.setColor(Color.white);
    g.setFont(new Font("serif",Font.BOLD,25));
    g.drawString(""+score,590,30);

    // paddle
    g.setColor(Color.green);
    g.fillRect(playerX, 550, 100, 8);

    // BALL 
    g.setColor(Color.yellow);
    g.fillOval(ballpsoX, ballpsoY, 20, 20);
    if(totalBricks<=0){
       play=false;
  ballXdir=0;
  ballYdir=0;
  g.setColor(Color.RED);
  g.setFont(new Font("serif", Font.BOLD, 30));
  g.drawString(" YOU WON",260, 300);

  g.setFont(new Font("serif", Font.BOLD, 20));
  g.drawString(" Press Enter To Restart",230, 350);
    }
if(ballpsoY>570){
  play=false;
  ballXdir=0;
  ballYdir=0;
  g.setColor(Color.RED);
  g.setFont(new Font("serif", Font.BOLD, 30));
  g.drawString(" Game Over, Scores",190, 300);

  g.setFont(new Font("serif", Font.BOLD, 20));
  g.drawString(" Press Enter To Restart",230, 350);
}

    g.dispose();
 
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Timer.start();
    if (play) {
      if (new Rectangle(ballpsoX, ballpsoY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {

        ballYdir = -ballYdir;
      }
     A: for (int i = 0; i < map.map.length; i++) {
        for (int j = 0; j < map.map[0].length; j++) {
          if (map.map[i][j] > 0) {
            int brickX = j * map.brickWidth + 80;
            int brickY = i * map.brickHeight + 50;
            int brickWidth = map.brickWidth;
            int brickHeight = map.brickHeight;
            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            Rectangle ballRect = new Rectangle(ballpsoX, ballpsoY, 20, 20);
            Rectangle brickRect = rect;
            if (ballRect.intersects(brickRect)) {
              map.setBrickValue(0, i, j);
              totalBricks--;
              score += 5;
              if (ballpsoX + 19 <= brickRect.x || ballpsoX + 1 >= brickRect.x + brickRect.width) {
                ballXdir = -ballXdir;

              } else {
                ballYdir = -ballYdir;

              }
              break A;
            }

          }
        }
      }
      ballpsoX += ballXdir;
      ballpsoY += ballYdir;
      if (ballpsoX < 0) {
        ballXdir = -ballXdir; // left border
      }
      if (ballpsoY < 0) {
        ballYdir = -ballYdir; // top border
      }
      if (ballpsoX > 670) {
        ballXdir = -ballXdir; // rigth border
      }
    }

    repaint();

  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (playerX >= 600) {
        playerX = 600;
      } else {
        moveRight();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      if (playerX < 10) {
        playerX = 10;
      } else {
        moveLeft();
      }
    }
if(e.getKeyCode()==KeyEvent.VK_ENTER){
if(!play){
  play=true;
  ballpsoX=120;
  ballpsoY=350;
  ballXdir=-1;
  ballYdir=-2;
  playerX=310;
  score=0;
  totalBricks=21;
  map=new MapGenerator(3, 7);
  repaint();

}
}
  }


  public void moveRight() {
    play = true;
    playerX += 20;
  }

  public void moveLeft() {
    play = true;
    playerX -= 20;
  }
}
