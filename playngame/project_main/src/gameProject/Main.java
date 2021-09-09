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
import java.awt.event.KeyAdapter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JTextField;

import MainPage.MainPage;

import java.io.FileReader;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

 

public class Main extends JFrame {

	GamePanel panel;
	GameThread gThread;
	Font f1;
	String ch;
	int count = 0;
	int ancnt = 0;
	String[] textData = new String[30];

	int[] tx = new int[30];
	int[] ty= new int[30];
	
	int [] answer = new int[30];

	int score; //����		

	
	
	
	
	public Main() throws IOException {
		
		
		FileReader wordReader = new FileReader("data/word.txt");
		BufferedReader bufReader = new BufferedReader(wordReader);
		
		for(int i = 0; i<30; i++) {
			answer[i] = -1;
		}

		Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
        
        
           
            @Override
            public void run() {
            	
            	
            	try {
					ch = bufReader.readLine();
					System.out.println(ch);
					textData[count] = ch;
                	
                	count++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            			
               
            }
        };
        timer.schedule(timerTask,1000,2000);
	
		setTitle("Graphic Game Test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 800);
		setResizable(false);

	
		panel = new GamePanel();
		panel.setLayout(null);
		add(panel,BorderLayout.CENTER);
		
		f1 = new Font("TAHOMA", Font.PLAIN, 20);
		
		JButton exitbtn = new JButton("EXIT");
	    exitbtn.setBounds(380, 10, 70, 30);
	    
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
		
		Button btn = new Button("ENTER");
		JTextField tf = new JTextField(15);
		tf.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                System.out.println(tf.getText());
	            	for(int i = 0; i<count; i++) {
	            		if(tf.getText().equals(textData[i])) {
	            		
	            			score += 10;
		            		answer[ancnt] = i+1;
		            		ancnt++;
	            			
	              			ty[i] = 1000;
	            			
	            		}
	            	}
	            	//System.out.println(tf.getText());
	                tf.setText("");
	            }
	        }
		});
		
		btn.setBounds(290, 700, 50, 25);

		tf.setBounds(180,700,100,25);
		tf.setFont(f1);
		
		panel.add(btn);
		panel.add(tf);
		panel.add(exitbtn);
		
		setVisible(true);
		
		//���� �����Ű�� ������ ��ü ���� �� ��ŸƮ
		gThread = new GameThread();
		gThread.start(); //run() �޼ҵ� �ڵ�����!!
		
		//�����ӿ� Ű���� �Է¿� �����ϴ� keyListner ���

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
			Random rnd = new Random();

			for(int i = 0; i < tx.length; i++) {
				tx[i] = rnd.nextInt(370);
				//System.out.println(tx[i]);
			}	
			
			
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
			g.drawString("Score : " + score,10, 30);
			
			for(int i = 0; i <count; i++) {
				g.drawString(textData[i], tx[i], ty[i]);
			}
			
			
		}//paintComponent method     
		
		

		void move() { //�÷��̾� �����̱�(��ǥ ����)

			repaint();
			for(int i =0; i<count; i++) {
				ty[i] += 10;
				
			}
			
			
			try {
                  Thread.sleep(200);
            } catch(InterruptedException e) {}
			
			
		}

		

		void makeEnemy() { //���� ���� �޼ҵ�

			if(width == 0 || height == 0) return;

			Random rnd = new Random();//50���� �ѹ��÷� �����
			int n = rnd.nextInt(15);

		}

		

		
	}//GamePanel class

	///////////////////////////////////////////////////////

	//���� �ð����� ����ȭ���� ���Ž�Ű�� �۾� �����ϴ� ���� ������ Ŭ����

	class GameThread extends Thread {

		@Override

		public void run() {

			while(true) {
				//���� ��ü ������ ��� �޼ҵ� ȣ��
				panel.makeEnemy();
				//GamePanel�� �÷��̾� ��ǥ ���� 

				//panel.x += -1;// ��ü�� ����� ������ 				
				//panel.y += -5;/// �� ��ü�� ������ �ϵ��� �ϴ°��� OOP�� �⺻ ��Ƽ��

				panel.move();				
				panel.repaint();//GamePanel�� ȭ�� ����

				try { //�ʹ� ���� ���Ƽ� õõ�� ������
					sleep(20);
				} catch (InterruptedException e) {}
			}
		}
	}


	public static void main(String[] args) throws IOException {
		new Main();
	}

}

