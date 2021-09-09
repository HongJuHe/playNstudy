package chatbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import MainPage.MainPage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Grammer_quiz extends JFrame
{
	private int q_count = 0;
	private String[] q_title;
	private String[] q_question;
	private String[][] q_example;
	private String[] q_answer;
	private int temp_cnt = 0;
	private int file_cnt = 3;
	
	QuizPanel panel;
	Font f1 = new Font("TAHOMA", Font.PLAIN, 20);
	Font f2 = new Font("TAHOMA", Font.BOLD, 20);
	
	public Grammer_quiz()
	{
		try
		{
			//JSON 파일 읽어오기
			Random r = new Random();
			String file_name = Integer.toString(r.nextInt(file_cnt));
			JsonObject jsonobject = new JsonObject();
			JsonParser jsonParser = new JsonParser();
				 
			JsonElement element = jsonParser.parse(new FileReader("data"+file_name+".json"));
			jsonobject = element.getAsJsonObject();
			
			q_count = Integer.parseInt(jsonobject.get("count").toString());
			//q_title = jsonobject.get("title").toString();
			System.out.println(jsonobject.get("title"));
			JsonArray titleArray = (JsonArray) jsonobject.get("title");

			q_title = new String[titleArray.size()];
			
			for (int i=0; i<titleArray.size(); i++)
			{
				q_title[i] = titleArray.get(i).toString();
				q_title[i] = q_title[i].substring(1,q_title[i].length()-1);
			}
			
			q_question = new String[q_count];
			q_answer = new String[q_count];
			q_example = new String[q_count][4];
			
			for (int i=0; i<q_count; i++)
			{
				JsonObject object = (JsonObject) jsonobject.get("Question"+Integer.toString(i));
				q_question[i] = object.get("question").toString();
				q_question[i] = q_question[i].substring(1,q_question[i].length()-1);
				JsonArray memberArray = (JsonArray) object.get("example");
				q_answer[i] = object.get("answer").toString();
				q_answer[i] = q_answer[i].substring(1,q_answer[i].length()-1);
				
				for (int j=0; j<memberArray.size(); j++)
				{
					q_example[i][j] = memberArray.get(j).toString();
					q_example[i][j] = q_example[i][j].substring(1,q_example[i][j].length()-1);
				}
				
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		setTitle(q_title[0]);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(225, 100, 500, 800);
		setResizable(false);
		
		panel = new QuizPanel();
		panel.setLayout(null);
		add(panel,BorderLayout.CENTER);
		setVisible(true);
		
		//f1 = new Font("TAHOMA", Font.PLAIN, 20);
		
		JButton exitbtn = new JButton("EXIT");
	    exitbtn.setBounds(380, 715, 70, 30);
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
	    
	    JLabel t_label0 = new JLabel();
	    t_label0.setBounds(0, 65, 500, 50);
	    t_label0.setText(q_title[0]);
	    t_label0.setFont(f2);
	    t_label0.setHorizontalAlignment(JLabel.CENTER);
		panel.add(t_label0);
	    
	    JLabel t_label1 = new JLabel();
	    t_label1.setBounds(0, 100, 500, 50);
	    t_label1.setText(q_title[1]);
	    t_label1.setFont(f2);
	    t_label1.setHorizontalAlignment(JLabel.CENTER);
		panel.add(t_label1);
		
		JLabel t_label2 = new JLabel();
	    t_label2.setBounds(0, 135, 500, 50);
	    t_label2.setText(q_title[2]);
	    t_label2.setFont(f2);
	    t_label2.setHorizontalAlignment(JLabel.CENTER);
		panel.add(t_label2);
	    
	    Button btn = new Button("ENTER");
		JTextField tf = new JTextField(15);
		
		JLabel q_label = new JLabel();
		q_label.setBounds(0, 220, 500, 50);
		q_label.setText(q_question[temp_cnt]);
		q_label.setFont(f1);
		q_label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(q_label);
		
		System.out.println(q_example[temp_cnt].length);
		JLabel[] a_label = new JLabel[q_example[temp_cnt].length];
		
		for (int i=0; i<q_example[temp_cnt].length; i++)
		{
			a_label[i] = new JLabel();
			a_label[i].setFont(f1);
			a_label[i].setBounds(0, 300+(i*50), 500, 50);
			a_label[i].setText(q_example[temp_cnt][i]);
			a_label[i].setHorizontalAlignment(JLabel.CENTER);
			panel.add(a_label[i]);
		}
		
		tf.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                String temp_tf = tf.getText().toUpperCase();
	                
	                if(temp_tf.equals(q_answer[temp_cnt])) //대소문자
	                {
	                	temp_cnt += 1;
	                	q_label.setText(q_question[temp_cnt]);
	                	for (int i=0; i<q_example[temp_cnt].length; i++)
	            		{
	            			a_label[i].setText(q_example[temp_cnt][i]);
	            		}
	                }
	            	
	                tf.setText("");
	            }
	        }
		});
		
		btn.setBounds(290, 600, 50, 25);

		tf.setBounds(180,600,100,25);
		tf.setFont(f1);
		
		panel.add(btn);
		panel.add(tf);
		
		setVisible(true);
	}
	
class QuizPanel extends JPanel {
		
		Image imgBack;
		int width, height;
				
		public QuizPanel() {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			imgBack = toolkit.getImage("images/bg2.png");
		}
		
		@Override
		protected void paintComponent(Graphics g) {
		
		f1 = new Font("TAHOMA", Font.PLAIN, 20);
		
		g.setFont(f1);
	
		if( width == 0 || height == 0) 
		{
			width = getWidth();
			height = getHeight();
			imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		}			

		g.drawImage(imgBack, 0, 0, this);
		//g.setFont(new Font(null, Font.PLAIN, 20));
		//g.drawString(q_title[0] ,150, 50);
		
		
	}//paintComponent method    
		
	}//SituPanel class
	
	public static void main(String[] args)
	{
		new Grammer_quiz();
	}
}