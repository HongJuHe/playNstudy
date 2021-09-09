package chatbot;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import MainPage.MainPage;

public class Run extends JFrame
{
	private String s_name = new String();	
	private JsonArray s_step = new JsonArray();
	private JsonArray s_ko = new JsonArray();
	private String s_explain = new String();
	private int step_len = 0;
	private int step_ele_len = 0;
	private int cnt = 0;
	private String[] answer; //현재 사용하는 문구
	private int btn_side_size = 100;
	private int cnt_btn = 0;
	
	SituPanel panel;
	Font f1;
	private JLabel label_ko;
	private String ko_temp;
	
	//private JFrame f = new JFrame("상황 별 회화 시작하기");
	private boolean nextstep = false;
	//private JPanel p = new JPanel();
	//private BorderLayout layout = new BorderLayout();

	public Run()
	{
		
		setTitle("simulation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(225, 100, 500, 800);
		setResizable(false);
		panel = new SituPanel();
		panel.setLayout(null);
		add(panel,BorderLayout.CENTER);
		
		

		try
		{
			//JSON 파일 읽어오기
			Random r = new Random();
			JsonObject jsonobject = new JsonObject();
			JsonParser jsonParser = new JsonParser();
				 
			JsonElement element = jsonParser.parse(new FileReader("situation.json"));
			jsonobject = element.getAsJsonObject();
			JsonArray memberArray = (JsonArray) jsonobject.get("situation");
				
			//랜덤으로 상황 선택
			JsonObject object = (JsonObject) memberArray.get(r.nextInt(memberArray.size()));
			//JsonObject object = (JsonObject) memberArray.get(0);
				        
			s_name = object.get("name").toString();
			s_name = s_name.substring(1, s_name.length()-1);
			
			s_step = (JsonArray)object.get("step");
			step_len = s_step.size();
			
			s_ko = (JsonArray)object.get("korean");
			
			s_explain = object.get("explain").toString();
			s_explain = s_explain.substring(1, s_explain.length()-1);
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
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
	    
	    JLabel label2 = new JLabel();
		label2.setText("<html><span style='font-size:18px'>"+ s_explain+"</html>");
		System.out.printf(s_explain);
	    label2.setBounds(0, 450, 500, 300);
	    //label2.setFont(new Font("돋움", Font.PLAIN, 20));
	    label2.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(label2);
	    

	    label_ko = new JLabel();
        label_ko.setBounds(0, 450, 500, 100);
        //ko_temp = s_ko.get(0).toString();
        //ko_temp = ko_temp.substring(1, ko_temp.length()-1);
	    //label_ko.setText(ko_temp);
	    //label_ko.setFont(new Font("돋움", Font.PLAIN, 20));
	    //label_ko.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(label_ko);
	    
	    String s = s_step.get(cnt_btn).toString();
	    System.out.println("s: "+s);
	    MakeButtons(s.toString());
	    ko_temp = s_ko.get(cnt_btn).toString();
        ko_temp = ko_temp.substring(1, ko_temp.length()-1);
        label_ko.setText("<html><span style='font-size:15px'>해석: " + ko_temp+ "</html>");
        label_ko.setHorizontalAlignment(JLabel.CENTER);
	    //ko_temp = s_ko.get(cnt_btn).toString();
        //ko_temp = ko_temp.substring(1, ko_temp.length()-1);
        //label_ko.setText("<html><span style='font-size:15px'>해석: " + ko_temp+ "</html>");
        //label_ko.setHorizontalAlignment(JLabel.CENTER);
	    
	    /*for (int i=0; i<step_len; i++)
	    {
	        System.out.println("step_len: "+step_len);
	        //MakeButtons 순서대로 호출
	        String s = s_step.get(i).toString();
	        System.out.println("s: "+s);
	        ko_temp = s_ko.get(i).toString();
	        ko_temp = ko_temp.substring(1, ko_temp.length()-1);
	        label_ko.setText("<html><span style='font-size:15px'>해석: " + ko_temp+ "</html>");
	        label_ko.setHorizontalAlignment(JLabel.CENTER);
	        MakeButtons(s);
	        nextstep = false;
	     }*/
		
	    setVisible(true);
	}
	
class SituPanel extends JPanel {
		

		Image imgBack;
		int width, height;
				
		
		public SituPanel() {
			Toolkit toolkit = Toolkit.getDefaultToolkit();

			imgBack = toolkit.getImage("images/bg2.png");

		}
		
		@Override
		protected void paintComponent(Graphics g) {
		
		
		if( width == 0 || height == 0) 
		{
			width = getWidth();
			height = getHeight();
			imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		}			

		g.drawImage(imgBack, 0, 0, this);
		//g.setFont(new Font(null, Font.BOLD, 20));
		//g.drawString(s_name ,200, 100);
		
	}//paintComponent method    
		
	}//SituPanel class
		
	private void makeJlable() //synchronized 
	{
		
		JLabel label2 = new JLabel();
		label2.setText("<html><span style='font-size:18px'>"+ s_explain+"</html>");
		System.out.printf(s_explain);
	    label2.setBounds(0, 500, 500, 300);
	    //label2.setFont(new Font("돋움", Font.PLAIN, 20));
	    label2.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(label2);
	    

	    JLabel label_ko = new JLabel();
        label_ko.setBounds(0, 500, 500, 100);
        String ko_temp = s_ko.get(0).toString();
        ko_temp = ko_temp.substring(1, ko_temp.length()-1);
	    //label_ko.setText(ko_temp);
	    //label_ko.setFont(new Font("돋움", Font.PLAIN, 20));
	    //label_ko.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(label_ko);
	    
	    for (int i=0; i<step_len; i++)
	    {
	        System.out.println("step_len: "+step_len);
	        //MakeButtons 순서대로 호출
	        String s = s_step.get(i).toString();
	        System.out.println("s: "+s);
	        ko_temp = s_ko.get(i).toString();
	        ko_temp = ko_temp.substring(1, ko_temp.length()-1);
	        label_ko.setText("<html><span style='font-size:15px'>해석: " + ko_temp+ "</html>");
	        label_ko.setHorizontalAlignment(JLabel.CENTER);
	        MakeButtons(s);
	        nextstep = false;
	     }
	    setVisible(true);
	}
       

	void MakeButtons(String s)//s_step받기
    {
		System.out.println("start");
    	s = s.substring(1, s.length()-1);
    	String[] s_arr = s.split(" ");
    	answer =s.split(" ");
    	System.out.println("First!: "+answer[0]);
    	step_ele_len = s_arr.length;
    	
    	//섞는 중
    	for (int r=0; r<s_arr.length; r++)
		{
			int a = (int) (Math.random()*s_arr.length);
			String tmp = s_arr[a];
			s_arr[a] = s_arr[r];
			s_arr[r] = tmp;
		}
    	
    	// 버튼 생성
        JButton[] buttons = new JButton[s_arr.length];
    	//Button[] buttons = new Button[s_arr.length];
        
        if (s_arr.length/3 > 3)
        {
        	btn_side_size = 70;
        }
        
    	for (int j=0; j<s_arr.length; j++)
    	{
    		
            buttons[j] = new JButton(s_arr[j]);
    		//buttons[j] = new Button(s_arr[j]);
            //buttons[j] = new JButton(s_arr[j], image);
            buttons[j].setVisible(true);
     
            //프레임에다가 버튼 추가
            panel.add(buttons[j]);
            //p.add(buttons[j]);
            
            
            buttons[j].setBounds((j%3)*120+75, (j/3)*btn_side_size+150, 105, 40);
            buttons[j].addMouseListener(new MyMouseListener());
    	}
    	
    	//순서대로 클릭하면 종료
    	/*while (!nextstep)
    	{
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("waiting");
    	}*/
    	
    }
	
	class MyMouseListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object a = e.getSource();
			if (a instanceof JButton)
			{
				JButton btn = (JButton)a;
				String btn_txt = btn.getText();
				System.out.println(btn_txt);
				//if btn_txt.equals() //버튼 사라지도록하기
				System.out.println("cnt: "+cnt);
				System.out.println("answer:" + answer[cnt]);
				if (btn_txt.equals(answer[cnt]))
				{
					btn.setVisible(false);
					if (cnt == step_ele_len-1)
					{
						System.out.println("finish");
						cnt = 0;
						nextstep = true;
						if (cnt_btn < step_len-1)
						{
							cnt_btn +=1;
							String s = s_step.get(cnt_btn).toString();
						    System.out.println("s: "+s);
						    ko_temp = s_ko.get(cnt_btn).toString();
					        ko_temp = ko_temp.substring(1, ko_temp.length()-1);
					        label_ko.setText("<html><span style='font-size:15px'>해석: " + ko_temp+ "</html>");
					        label_ko.setHorizontalAlignment(JLabel.CENTER);
						    MakeButtons(s.toString());
						}
					}
					else
					{
						cnt++;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args)
	{
		new Run();
	}
	
}