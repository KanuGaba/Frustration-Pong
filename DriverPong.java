   //Torbert, e-mail: smtorbert@fcps.edu
	//version 6.17.2003

import javax.swing.JFrame;
public class DriverPong
{
   public static void main(String[] args)
   { 
      JFrame frame = new JFrame("Frustration Pong");
      frame.setSize(1000, 1000);
      frame.setLocation(150, 10);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new BumperPanel());
      frame.setVisible(true);
   }
}