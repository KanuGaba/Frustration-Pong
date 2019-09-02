	// Phil Ero 15JUL08
	
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
   
public class BumperPanel extends JPanel
{
   private static final int FRAME = 1024;
   private static final Color BACKGROUND = new Color(204, 204, 204);
   private static final Color BALL_COLOR = Color.BLACK;
   private static final Color PRIZE_COLOR = Color.RED;
   private static final Color BUMPER_COLOR = Color.BLUE;
   private static final double BALL_DIAM = 20;
   private static final double PRIZE_DIAM = 25;
   private static final int BUMPER_X_WIDTH = 75;
   private static final int BUMPER_Y_WIDTH = 125;
   
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Ball ball;
   private Bumper bumper;
   private Bumper bumper1;
   private Bumper bumper2;
   private Bumper bumper3;
   private Bumper bumper4;
   private Timer timer;    
   private boolean GameState = true;
      
   public BumperPanel()
   {
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
         
         // create ball and jump
      ball = new Ball(512, 512, 20, Color.BLACK);
            
         // create bumper
      bumper1 = new Bumper(70, 206, 30, 100, Color.BLUE.darker());
      bumper = new Bumper(FRAME - 100, 206, 30, 100, Color.GREEN.darker());
      bumper2 = new Bumper(70, 718, 30, 100, Color.BLUE.brighter());
      bumper3 = new Bumper(FRAME - 100, 718, 30, 100, Color.GREEN);
      bumper4 = new Bumper(522, 0, 21, 145, Color.RED);
      	
         // ensure ball is outside the bumper
      while(bumper.inBumper(ball))
         ball.jump(FRAME, FRAME);
      while(bumper1.inBumper(ball))
         ball.jump(FRAME, FRAME);
      
      ball.setHits(0);
      ball.setHits1(0);
      timer = new Timer(5, new Listener());
      timer.start();
      addKeyListener(new Key());
      setFocusable(true);
      addMouseListener(new Mouse());
   }
      
   public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
      for(int y = 0; y <= 1024; y += 40)
         g.fillRect(510, y, 4, 30);
      g.fillRect(0, 480, 1024, 2);
   }
          
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
            // clear buffer and move ball
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0,0,FRAME,FRAME); 
         ball.move(FRAME, FRAME);
            
            // check for collisions
         BumperCollision.collide(bumper, ball);
         BumperCollision.collide(bumper1, ball);
         BumperCollision.collide(bumper2, ball);
         BumperCollision.collide(bumper3, ball);
         BumperCollision.collide(bumper4, ball);
            
            // draw ball, bumper & prize
         if(GameState == true)
         {   
            ball.draw(myBuffer);         
            bumper.draw(myBuffer);
            bumper1.draw(myBuffer);
            bumper2.draw(myBuffer);
            bumper3.draw(myBuffer);
            bumper4.draw(myBuffer);
            bumper4.move();
         }
         
            // update hits on buffer
         myBuffer.setColor(Color.black);
         myBuffer.setFont(new Font("Monospaced", Font.BOLD, 50));
         myBuffer.drawString("Count: " + ball.getHits(), FRAME - 450, 50);
            
         myBuffer.setColor(Color.black);
         myBuffer.setFont(new Font("Monospaced", Font.BOLD, 50));
         myBuffer.drawString("Count: " + ball.getHits1(), 235, 50);
         
         if(ball.getHits1() == 11 || ball.getHits() == 11)
         {
            if(ball.getHits1() == 11)
            {
               myBuffer.setColor(Color.BLACK);
               myBuffer.fillRect(0,0,FRAME,FRAME); 
               myBuffer.setFont(new Font("SansSerif", Font.BOLD, 100));
               myBuffer.setColor(Color.ORANGE);
               myBuffer.drawString("Blue Wins!", 300, 510);
               GameState = false;
               myBuffer.setFont(new Font("SansSerif", Font.ITALIC, 75));
               myBuffer.setColor(Color.WHITE);
               myBuffer.drawString("Left Click to Play Again", 125, 750);
               ball.setdx(0);
               ball.setdy(0);
                     
            }  
            else if(ball.getHits() == 11)
            {
               myBuffer.setColor(Color.BLACK);
               myBuffer.fillRect(0,0,FRAME,FRAME); 
               myBuffer.setFont(new Font("SansSerif", Font.BOLD, 100));
               myBuffer.setColor(Color.ORANGE);
               myBuffer.drawString("Green Wins!", 225, 510);
               GameState = false;
               myBuffer.setFont(new Font("SansSerif", Font.ITALIC, 75));
               myBuffer.setColor(Color.WHITE);
               myBuffer.drawString("Left Click to Play Again", 125, 750);
               ball.setdx(0);
               ball.setdy(0);
               
                     
            }  
               
         }
               
         
         repaint();
      }
   } 
   		
   private double distance(double x1, double y1, double x2, double y2)
   {
      return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
   }
   private class Key extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {
            bumper.setY( bumper.getY()-40 );
            bumper3.setY( bumper3.getY()-40 ); 
         }   
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {
            bumper3.setY( bumper3.getY()+40 );
         }
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
            bumper1.setY( bumper1.getY()-40 );
            bumper2.setY( bumper2.getY()-40 );
         }
         if(e.getKeyCode() == KeyEvent.VK_S)
         {
            bumper2.setY( bumper2.getY()+40 );
         }
        // if(e.getKeyCode() == KeyEvent.VK_I)
         //{
         //   bumper.setY( bumper.getY()-40 );
         //   bumper3.setY( bumper3.getY()-40 ); 
        // }   
      
         if(bumper.getY() <= 0)     //hits the top edge
         {
            bumper.setY(512 - bumper.getYWidth());
         }
         else if(bumper.getY() >= FRAME - bumper.getYWidth())     //hits the bottom edge (half, not needed)
         {
            bumper.setY(FRAME - bumper.getYWidth());
         }
         
         if(bumper1.getY() <= 0)     //hits the top edge
         {
            bumper1.setY(512 - bumper.getYWidth());
         }
         else if(bumper1.getY() >= FRAME - bumper1.getYWidth())     //hits the bottom edge (half, not needed)
         {
            bumper1.setY(FRAME - bumper1.getYWidth());
         }
         
         if(bumper2.getY() <= 512)     //hits the top edge (half)
         {
            bumper2.setY(512);
         }
         else if(bumper2.getY() >= FRAME - bumper2.getYWidth())     //hits the bottom edge
         {
            bumper2.setY(FRAME - bumper2.getYWidth());
         }
         
         if(bumper3.getY() <= 512)     //hits the top edge (half)
         {
            bumper3.setY(512);
         }
         else if(bumper3.getY() >= FRAME - bumper3.getYWidth())     //hits the bottom edge
         {
            bumper3.setY(FRAME - bumper3.getYWidth());
         }
         
         if(bumper4.getY() <= 0)     //hits the top edge
         {
            bumper4.setY(FRAME - bumper4.getYWidth());
         }
         else if(bumper4.getY() >= FRAME - bumper4.getYWidth())     //hits the bottom edge
         {
            bumper4.setY(0);
         }
         
        
      
      }
   }
   private class Mouse extends MouseAdapter
   {
      public void mousePressed( MouseEvent e )
      {
        if(GameState == false)
        {
         GameState = true;
         ball.setHits(0);
         ball.setHits1(0);
         ball.setdx(Math.random() * 12 - 6);
         ball.setdy(Math.random() * 12 - 6);
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0,0,FRAME,FRAME); 
           }
         
         
      }
   }
}