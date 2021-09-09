package gameProject;


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

import catchMind.GameStart;

public class GameList extends JFrame {

	GamePanel panel;
	Font f1;
	
	
	public GameList() throws IOException {
		
		
		
		setTitle("Play N Study");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 800);
		setResizable(false);

	
		panel = new GamePanel();
		panel.setLayout(null);
		add(panel,BorderLayout.CENTER);
		
		
		
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
		
		Button button = new Button("Chatch   Mind");
		button.setBounds(180, 330, 140, 70);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameStart n = null;
				n = new GameStart();
				//n = new Run();
				n.setVisible(true);
				dispose();
			}
		});
		
		Button button2 = new Button("TAJA");
		button2.setBounds(180, 470, 140, 70);
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start n = null;
				try {
					n = new Start();
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
		panel.add(button2);
		
		setVisible(true);
		
	
	}
	
class GamePanel extends JPanel { //����ȭ�� �׷��� Panel
	

		//ȭ�鿡 ������ �̹��� ��ü �������� - �������
		Image imgBack;
		Image name;
		int width, height;//�г� ������ ��������
				
		

		public GamePanel() {

			//GUI ���� ���α׷��� ���Ǹ� ���� ������� ��������(Toolkit) ��ü 
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			//�����ڿ��� ����� ���Ϸ��ϸ� ������ 0��, ���� �г��� �Ⱥپ ����� �𸣱⶧��

			//width = getWidth(); 
			//height = getHeight();

			imgBack = toolkit.getImage("images/bg2.png");//��� �̹���
			name = toolkit.getImage("images/name.png");
			
						
			
		}//������		

		//ȭ�鿡 ��������  ������ ���빰 �۾��� �����ϴ� �޼ҵ� : �ڵ� ����(�ݹ� �޼ҵ�)

		@Override
			protected void paintComponent(Graphics g) {
			
			f1 = new Font("TAHOMA", Font.PLAIN, 100);
			
			g.setFont(f1);
		
			
			//ȭ�鿡 ������ �۾� �ڵ�
			if( width == 0 || height == 0) { //ó�� ȣ��ÿ� ������ �Ⱥ��̴� ���� ����
				
				width = getWidth();
				height = getHeight();

				//������¡
				imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				name = name.getScaledInstance(300, 90, Image.SCALE_SMOOTH);

			}			

			//�̰��� ȭ����ü�� �����Ƿ� �׸� �׸��� �۾��� ������ ���⼭			
			g.drawImage(imgBack, 0, 0, this);//��� �׸���			
			g.drawImage(name, 100, 200, this);

		
			
			
		}//paintComponent method     
		
		
		
	}//GamePanel class



	public static void main(String[] args) throws IOException {
		new GameList();
	}

}


