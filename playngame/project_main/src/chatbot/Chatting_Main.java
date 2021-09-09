package chatbot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.client.Url;
import io.socket.emitter.Emitter;
import io.socket.emitter.Emitter.Listener;
import io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import MainPage.MainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class Chatting_Main extends JFrame{

	
	ChatPanel panel;
	Font f1 = new Font("TAHOMA", Font.PLAIN, 20);
	Font f2 = new Font("TAHOMA", Font.PLAIN, 30);
	
	Socket socket;
	JSONObject jsonObjectBody = new JSONObject();
	JSONObject jsonObjectRequest = new JSONObject();
	
	String recvtxt = "";
	WebDriver driver;
	JTextArea ta;

    public Chatting_Main() throws IOException
    {
    	setSize(500, 800);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	setTitle("chatbot");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(225, 100, 500, 800);
		setResizable(false);
		
		panel = new ChatPanel();
		
		panel.setLayout(null);
		//panel.setLayout(new FlowLayout());
		add(panel,BorderLayout.CENTER);
		setVisible(true);
		
		JButton btn = new JButton("Enter");
	    btn.setBounds(380, 715, 70, 30);
	    panel.add(btn);
	    btn.setVisible(true);
	    
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
	    
	    ta = new JTextArea(100, 200);
	    ta.setBounds(5, 50, 485, 650);
	    //ta.setEditable(false);
	    ta.setBackground(new Color(255, 255, 255, 255));
	    ta.setFont(f2);
	    //ta.setLineWrap(true);
	    //ta.setWrapStyleWord(true);
	    //JScrollPane scroll = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    //scroll.setBounds(0, 25, 455, 430);
	    //scroll.setBounds(5, 50, 485, 200); 
	    //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    //panel.add(scroll);
	    //scroll.setVisible(true);
	    
	    JTextField tf = new JTextField(15);
		tf.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                System.out.println(tf.getText());
	                ta.append("You: "+tf.getText() + "\n");
	                ta.setCaretPosition(ta.getDocument().getLength());
	                chatmsg(tf.getText());
	                
	                tf.setText("");
	            }
	        }
		});
		
	
		tf.setBounds(20, 705, 350, 50);
		tf.setFont(f1);
		
		panel.add(btn);
		panel.add(tf);
		panel.add(ta);
		
		setVisible(true);
	    
	    //System.setProperty("webdriver.chrome.driver", "C:\\Users\\347\\Downloads\\chromedriver_win32\\chromedriver.exe");
	    //ChromeOptions chromeOptions = new ChromeOptions();
	    //chromeOptions.addArguments("--headless", "--window-size=500,800");
	    //chromeOptions.setHeadless(true);
	    //DesiredCapabilities cap = DesiredCapabilities.chrome();

	    //chromeOptions.addArguments("--disable-gpu");
        //chromeOptions.addArguments("--disable-extensions");
        //chromeOptions.addArguments("--no-sandbox");
        //chromeOptions.addArguments("--disable-dev-shm-usage");
        //chromeOptions.addArguments("--headless");
        //chromeOptions.addArguments("--window-size=500,800");

        /*final HashMap<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);*/
	    
        
        //cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	    //driver = new ChromeDriver(chromeOptions);
	    //cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
	    
	    //JavascriptExecutor js = (JavascriptExecutor)driver;
	    //driver.get("C:\\Users\\347\\PycharmProjects\\rasa_comunication\\index.html");

	    //driver.manage().window().setSize(new Dimension(1024,768));
	    //js.executeScript("window.resizeTo(500, 800)");
	    //driver.manage().window().setSize(dimension);
	    //String text = driver.findElement(By.className("rw-new-message")).getText();
	    //Dimension dem = new Dimension((int)500, (int)800);
	    //driver.manage().window().setSize(dem);
	    //js.executeScript("let e = document.createElement('script');");
	    //js.executeScript("e.src='https://cdn.jsdelivr.net/npm/rasa-webchat/lib/index.js'), (e.async=!0), (e.onload=() =>{ window.WebChat.default({customData:{language:'en'}, socketUrl:'http://localhost:5005'},null);})");
	    //driver.close();
	    //((JavascriptExecutor) driver).executeScript("let e = document.createElement('script');");
	    //System.out.println("wi:" + driver.getWindowHandle());
	    
	    //WebElement chkBx = driver.findElement(By.id("end"));
	    /*while(!chkBx.isSelected())
	    {
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println("select checkbox");//타이머 조정
	    }*/
	    //System.out.println("close/");//메인 프레임 띄우기
	    
	    /*btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JsonObject prejsonObject = new JsonObject();
				prejsonObject.addProperty("sender", "rasa");
				prejsonObject.addProperty("message", "hello");
				JSONObject jsonObject = null;
				System.out.println(prejsonObject);
				try {
					jsonObject = new JSONObject(prejsonObject.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				socket.emit("post", jsonObject);
			}
		});*/
	    
	    
	    /*URL url = new URL("http://localhost:5005/conversations/default/");
	    URLConnection con = url.openConnection();
	    HttpURLConnection http = (HttpURLConnection)con;
	    
	    byte[] out = "{\"message\":\"hi\"}" .getBytes(StandardCharsets.UTF_8);
	    int length = out.length;

	    http.setFixedLengthStreamingMode(length);
	    
	    http.setRequestMethod("POST"); // PUT is another valid option
	    http.setDoOutput(true);
	    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    http.connect();
	    try(OutputStream os = http.getOutputStream()) {
	        os.write(out);
	    }*/
	    
	    
	    /*
	    URL url = null;
		try {
			url = new URL("http://localhost:5005");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String query = "";

        //make connection
        URLConnection urlc = url.openConnection();

        //use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        //send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
        ps.print(query);
        ps.close();

        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
            .getInputStream()));
        String l = null;
        while ((l=br.readLine())!=null) {
            System.out.println(l);
        }
        br.close();*/
    }
		
		/*Button button = new Button("AAA");
		panel.add(button);
		button.setBounds(350, 700, 70, 30);
		button.setVisible(true);
		
		button.addActionListener(event -> {
			UI.getCurrent().getPage().executeJs("ns.toggle($0)");
		});*/
		
		

    /*public boolean isClickable(WebElement webe){
    	try
    	{ 
    		WebDriverWait wait = new WebDriverWait(driver, 1);
    		wait.until(ExpectedConditions.elementToBeClickable(webe));
    		System.out.println("1");
    		return true;
    	} catch (Exception e)
    	{
    		System.out.println("2");
    		return false;
    	}
    }*/
    
    public void chatmsg(String client_msg)
    {
    	try {
	    	IO.Options opts = new IO.Options();
	    	opts.transports = new String[] {"websocket"};
	    	opts.path = "/socket.io";
	    	//opts.path = new String("/webhooks/rest/webhook/");
	    	Socket socket = IO.socket("http://localhost:5005/", opts);
	    	//Socket socket = IO.socket("http://localhost:5005");
	    	
	        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	            @Override
	            public void call(Object... args) {
	                System.out.println("Connected to server!");
	            }
	        });
	        

	        socket.connect();
	        
	        try {
	            jsonObjectBody.put("session_id", "HongJuHee");
	            jsonObjectBody.put("message", client_msg);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        
	        try {
	            jsonObjectRequest.put("user_uttered", jsonObjectBody);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        
	        
	        socket.emit("user_uttered", jsonObjectBody, new Ack() {
				@Override
	            public void call(Object... args) {
	        		System.out.println("User sent a message");
	        	}
	        });
	        
	        
	        socket.on("bot_uttered", new Emitter.Listener() {
	            @Override
	            public void call(Object... args) {
	                System.out.println("recv: "+ java.util.Arrays.toString(args));
	                recvtxt = java.util.Arrays.toString(args);
	                recvtxt = recvtxt.substring(10, recvtxt.length()-3);
	                System.out.println(recvtxt);
	                
	                //msg[0].setText(recvtxt);
	                ta.append("Bot: "+recvtxt + "\n");
	                ta.setCaretPosition(ta.getDocument().getLength());
	                //scroll.append("Bot: "+recvtxt + "\n");
	            }
	        });
	    	
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    	
    }
    
    class ChatPanel extends JPanel
    {
    	Image imgBack;
		int width, height;
				
		public ChatPanel() {
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
    }
    public static void main(String[] args) throws IOException
	{
		new Chatting_Main();
	}
}