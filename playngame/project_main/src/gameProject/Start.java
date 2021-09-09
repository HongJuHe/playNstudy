package gameProject;


import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import MainPage.MainPage;

import java.io.IOException;


public class Start extends JFrame {

	GamePanel panel;
	Font f1;
	
	
	public Start() throws IOException {
		
		
		
		setTitle("Graphic Game Test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 800);
		setResizable(false);

	
		panel = new GamePanel();
		panel.setLayout(null);
		add(panel,BorderLayout.CENTER);
		
		f1 = new Font("TAHOMA", Font.PLAIN, 30);
		
		

		  JButton exitbtn = new JButton("EXIT");
		    exitbtn.setBounds(380, 10, 70, 30);
		    panel.add(exitbtn);
		    exitbtn.setVisible(true);
		    
		    exitbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainPage n = null;
					try {
						n = new MainPage();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					n.setVisible(true);
					dispose();
				}
			});
		Button button = new Button("Click 	To	 Start");
		button.setBounds(200, 500, 100, 80);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main n = null;
				try {
					n = new Main();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				n.setVisible(true);
				dispose();
			}
		});
		
		panel.add(exitbtn);
		panel.add(button);
		
		setVisible(true);
		
	
	}
	
class GamePanel extends JPanel { //����ȭ�� �׷��� Panel
	

		//ȭ�鿡 ������ �̹��� ��ü �������� - �������
		Image imgBack;
		int width, height;//�г� ������ ��������
				
		

		public GamePanel() {

			//GUI ���� ���α׷��� ���Ǹ� ���� ������� ��������(Toolkit) ��ü 
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			//�����ڿ��� ����� ���Ϸ��ϸ� ������ 0��, ���� �г��� �Ⱥپ ����� �𸣱⶧��

			//width = getWidth(); 
			//height = getHeight();

			imgBack = toolkit.getImage("images/bg2.jpg");//��� �̹���
						
			
		}//������		

		//ȭ�鿡 ��������  ������ ���빰 �۾��� �����ϴ� �޼ҵ� : �ڵ� ����(�ݹ� �޼ҵ�)

		@Override
			protected void paintComponent(Graphics g) {
			
			
			
			//ȭ�鿡 ������ �۾� �ڵ�
			if( width == 0 || height == 0) { //ó�� ȣ��ÿ� ������ �Ⱥ��̴� ���� ����
				
				width = getWidth();
				height = getHeight();

				//������¡
				imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);

			}			

			//�̰��� ȭ����ü�� �����Ƿ� �׸� �׸��� �۾��� ������ ���⼭			
			g.drawImage(imgBack, 0, 0, this);//��� �׸���			

			g.setFont(new Font(null, Font.BOLD, 20));//���� ǥ���ϱ�
			g.drawString("Raining Words! " ,50, 100);
			g.drawString("game rules : Type in the falling English " ,50, 150);
			g.drawString("words to earn points.  " ,50, 190);
			
		}//paintComponent method     
		
		
		
	}//GamePanel class



	public static void main(String[] args) throws IOException {
		new Start();
	}

}

