//Name: Kanu Gaba     Date:11/26/13
import java.awt.*;
public class Ball extends Polkadot
{
   private double dx;       // pixels to move each time step() is called.
   private double dy;
   private int Hits;
   private int Hits1;
    // constructors
   public Ball()         //default constructor
   {
      super(200, 200, 50, Color.BLACK);
      dx = Math.random() * 12 - 6;          // to move vertically
      dy = Math.random() * 12 - 6;          // to move sideways
   }
   public Ball(double x, double y, double dia, Color c)
   {
      super(x, y, dia, c);
      dx = Math.random()* 12 - 6;
      dy = Math.random() * 12 - 6;
   }
      
     //modifier methods 
   public void setdx(double x)        
   {
      dx = x;
   }
   public void setdy(double y)
   {
      dy = y;
   }
   public void setHits(int hit)
   {
      Hits = hit;
   }
   public void setHits1(int hit1)
   {
      Hits1 = hit1;
   }
      
      //accessor methods
   public double getdx()             
   {
      return dx;
   }
   public double getdy()
   {
      return dy;
   }
   public int getHits()
   {
      return Hits;
   }
   public int getHits1()
   {
      return Hits1;
   }
      
     //instance methods
   public void move(double rightEdge, double bottomEdge)
   {
      setX(getX()+ dx);                    // x = x + dx
      setY(getY()+ dy);
         
        // check for left & right edge bounces
      if(getX() >= rightEdge - getRadius())     //hits the right edge
      {
         setX(rightEdge - getRadius());
         dx = dx * -1; 
         Hits1++;
         setX(512);
         setY(512);
        
                 
      }
      else if(getX() <=getRadius())     //hits the left edge
      {
         setX(getRadius());
         dx = dx * -1; 
         Hits++;
         setX(512);
         setY(512);
        
         
      }
      else if(getY() <=getRadius())     //hits the top edge
      {
         setY(getRadius());
         dy = dy * -1; 
      }
      else if(getY() >= bottomEdge - getRadius())     //hits the bottom edge
      {
         setY(bottomEdge - getRadius());
         dy = dy * -1;
      }
   }
}