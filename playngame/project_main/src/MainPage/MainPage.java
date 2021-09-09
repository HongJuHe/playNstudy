package MainPage;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;

import gameProject.GameList;
import chatbot.Grammer;

public class MainPage extends JFrame {

   GamePanel panel;
   Font f1;
   
   
   public MainPage() throws IOException {
      
      
      
      setTitle("Play N Study");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setBounds(100, 100, 500, 800);
      setResizable(false);

   
      panel = new GamePanel();
      panel.setLayout(null);
      add(panel,BorderLayout.CENTER);
      
      
    
      Button button = new Button("Chat    bot");
      button.setBounds(200, 450, 100, 70);
      
      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Grammer n = null;
            try {
               n = new Grammer();
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            n.setVisible(true);
            dispose();
         }
      });
    
      
      Button button2 = new Button("English   Game");
      button2.setBounds(200, 550, 100, 70);
      
      button2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 GameList n = null;
            try {
               n = new GameList();
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            n.setVisible(true);
            dispose();
         }
      });
      
      panel.add(button);
      panel.add(button2);
      
      setVisible(true);
      
   
   }
   
class GamePanel extends JPanel { //게임화면 그려낼 Panel
   

      //화면에 보여질 이미지 객체 참조변수 - 멤버변수
      Image imgBack;
      Image logo;
      Image name;
      int width, height;//패널 사이즈 가져오기
            
      

      public GamePanel() {

         //GUI 관련 프로그램의 편의를 위해 만들어진 도구상자(Toolkit) 객체 
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         //생성자에서 사이즈를 구하려하면 무조건 0임, 아직 패널이 안붙어서 사이즈를 모르기때문

         //width = getWidth(); 
         //height = getHeight();

         imgBack = toolkit.getImage("images/bg2.png");//배경 이미지
         logo = toolkit.getImage("images/logo.png");
         name = toolkit.getImage("images/name.png");
                  
         
      }//생성자      

      //화면에 보여질때  보여질 내용물 작업을 수행하는 메소드 : 자동 실행(콜백 메소드)

      @Override
         protected void paintComponent(Graphics g) {
         
      
         
         //화면에 보여질 작업 코딩
         if( width == 0 || height == 0) { //처음 호출시엔 느려서 안보이다 이후 보임
            
            width = getWidth();
            height = getHeight();

            //리사이징
            imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            logo = logo.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            name = name.getScaledInstance(300, 90, Image.SCALE_SMOOTH);
            

         }         

         //이곳에 화가객체가 있으므로 그림 그리는 작업은 무조건 여기서         
         g.drawImage(imgBack, 0, 0, this);//배경 그리기      
         
         g.drawImage(logo, 160, 150, this);
         g.drawImage(name, 100, 340, this);

         
         
      }//paintComponent method     
      
      
      
   }//GamePanel class



   public static void main(String[] args) throws IOException {
      new MainPage();
   }

}
