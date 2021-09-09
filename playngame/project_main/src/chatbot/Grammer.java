package chatbot;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;

import gameProject.Start;

public class Grammer extends JFrame {

	GamePanel panel;
	Font f1;
	
	
	public Grammer() throws IOException {
		
		
		
		setTitle("Play N Study");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 800);
		setResizable(false);

	
		panel = new GamePanel();
		panel.setLayout(null);
		add(panel,BorderLayout.CENTER);
		
		
		

		
		Button button = new Button("Situation dialog");
		button.setBounds(180, 300, 140, 70);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Run n = null;
				n = new Run();
				//n = new Run();
				n.setVisible(true);
				dispose();
			}
		});
		
		Button button2 = new Button("Grammer Question");
		button2.setBounds(180, 400, 140, 70);
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grammer_quiz n = null;
				n = new Grammer_quiz();
				n.setVisible(true);
				dispose();
			}
		});
		
		Button button3 = new Button("Chatbot dialog");
		button3.setBounds(180, 500, 140, 70);
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Chatting_Main n = null;
				try {
					n = new Chatting_Main();
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
		panel.add(button3);
		
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
		new Grammer();
	}

}


