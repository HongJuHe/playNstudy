package chatbot;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
public class SwingDemo {
   public static void main(String[] args) {
      JButton button1 = new JButton("One");
      JButton button2 = new JButton("Two");
      JButton button3 = new JButton("Three");
      JButton button4 = new JButton("Four");
      JButton button5 = new JButton("Five");
      JButton button6 = new JButton("Six");
      Icon icon = new ImageIcon("images/fill.png", "아닛");
      JButton button7 = new JButton(icon);
      button7.setSize(100,50);
     button7.setBorderPainted(false);
      button7.setContentAreaFilled(false);
      JFrame box = new JFrame();
      box.add(button1);
      box.add(button2);
      box.add(button3);
      box.add(button4);
      box.add(button5);
      box.add(button6);
      box.add(button7);
      
     
      box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      box.setLocationByPlatform(true);
      box.setSize(500, 300);
      box.setVisible(true);
   }
}