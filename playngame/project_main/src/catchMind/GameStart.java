package catchMind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.*;

import MainPage.MainPage;

import javax.sound.sampled.*;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class GameStart extends JFrame {
	
	protected boolean running;
	ByteArrayOutputStream out;
	private int scoreValue = 0;
	JButton exitbtn;
	  
	// 필수 태그
	private static final String TAG = "GameStart :";
	// 클라이언트가 입력한 아이디 값을 클라이언트도 알도록 전역 변수로 설정.
	private String IDString;
	// 제시어를 담아놓은 배열.
	public String[] problem = { "Shark", "home", "test", "JAVA", "chair", "desk", "mouse", "light", "red", "water", "clock" };
	// 턴이 변화할 때 마다 제시어를 순차적으로 선택하는 변수.
	public int selectProblem = 0;

	private ImageIcon icGameStart;

	private ImageIcon iconBlackPen;
	private ImageIcon iconRedPen;
	private ImageIcon iconOrangePen;
	private ImageIcon iconYellowPen;
	private ImageIcon iconGreenPen;
	private ImageIcon iconBluePen;
	private ImageIcon iconIndigoPen;
	private ImageIcon iconPurplePen;

	// 통신
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	// 이미지 패널
	private MyPanel plMain; // 초기 메인 화면 이미지

	// plMain에 포함됨
	private JButton btnStart; // 아이디 입력 전 게임시작 버튼

	// btnStart을 누르면 plId이 나타남
	private JPanel plId; // 아이디 입력 패널
	private JPanel plSub; // 아이디 입력 패널

	// plId에 포함됨
	private JLabel laId; // '아이디를 입력하세요'라벨
	private TextField tfIdInput; // 아이디 입력
	private JButton btnId; // 아이디 입력 버튼

	// btnStart을 누르면 plDrawRoom로 바뀜
	private JPanel plDrawRoom; // 그리기방 패널(화면 전체)

	// plDrawRoom에 포함됨

	// plTop과 plMplId
	private JPanel plTopMpId;

	// 위쪽 방 이름 , 제시어, 넘기기 버튼
	private JPanel plTop;

	// plTop에 포함됨
	private JLabel laQuizTitle; // '제시어 : ' 라벨
	private JLabel laQuiz; // 제시어 변수 라벨
	private JButton btnSkip; // 넘기기 버튼
	
	private JLabel userScore;
	
	
	private JButton voiceStart; // 넘기기 버튼
	private JButton voiceEnd; // 넘기기 버튼

	// 왼쪽을 포함한 중간 그림판
	private JPanel plMplId; // 그림판

	// plMplId에 포함됨
	private MyPanel1 plDraw; // 그림판 이미지

	// 아래쪽 팔레트
	private JPanel plBottom; // 팔레트

	private MyButton1 btnDelete; // 지우개 이미지

	// plBottom에 포함됨
	private MyPanel2 plPalette; // 크레파스 이미지

	private JButton btnBlackDrawPen;
	private JButton btnRedDrawPen;
	private JButton btnOrangeDrawPen;
	private JButton btnYellowDrawPen;
	private JButton btnGreenDrawPen;
	//private JButton btnBlueDrawPen;
	private JButton btnIndigoDrawPen;
	//private JButton btnPurpleDrawPen;

	//private MyButton btnEraser; // 지우개 이미지

	// 오른쪽 유저목록, 채팅, 준비완료, 나가기 버튼
	private JPanel plEast;

	// plEast에 포함됨
	private JTextArea taUserList; // 유저 목록 라벨

	// plEast에 포함된 채팅 패널
	private JPanel plChat; // 채팅창, 채팅 입력란

	// plChat에 포함됨
	//private JTextArea taChat; // 채팅 로그
	//private JScrollPane scrChat;

	// 준비완료, 나가기 버튼 패널
	private JPanel btnPanel; // 채팅창, 채팅 입력란

	// btnPanel에 포함됨
	private JButton btnReady; // 준비 완료 버튼
	private JButton btnExit; // 나가기 버튼

	// 폰트 크기 설정
	private Font ftSmall; // 16px크기 폰트
	private Font ftMedium; // 24px크기 폰트
	private Font ftLarge; // 36px크기 폰트

	// Brush 좌표값
	int x, y;
	// Brush 색깔
	// Color color;

	// Draw에 필요한 선언
	private BufferedImage imgBuff;
	private JLabel drawLabel;
	private JPanel drawPanel;
	private Brush brush;
	String sendDraw = null;
	String sendColor = null;
	boolean drawPPAP = true;

	// 이미지 매서드
	private ImageIcon ImageSetSize(ImageIcon icon, int width, int heigth) {
		Image xImage = icon.getImage();
		Image yImage = xImage.getScaledInstance(width, heigth, Image.SCALE_SMOOTH);
		ImageIcon xyImage = new ImageIcon(yImage);
		return xyImage;
	}

	// 이미지 삽입용 클래스
	class MyPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("images/bg2.png");
		private Image imgMain = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imgMain, 0, 0, 500, 800, this);
		}
	};

	class MyPanel1 extends JPanel {
		private ImageIcon icon = new ImageIcon("images/bg2.png");
		private Image imgMain = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			System.out.println(getWidth());
			System.out.println(getHeight());
			g.drawImage(imgMain, 0, 0, 500, 800, this);
		}
	};

	class MyPanel2 extends JPanel {
		private ImageIcon icon = new ImageIcon("img/b.png");
		private Image imgMain = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imgMain, 0, 0, getWidth(), getHeight(), null);
		}
	};

	class MyButton extends JButton {
		private ImageIcon icon = new ImageIcon("img/drawEraser.png");
		private Image imgMain = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imgMain, 0, 0, getWidth(), getHeight(), null);
			setBorderPainted(false); // 버튼 테두리 제거
		}
	};

	class MyButton1 extends JButton {
		private ImageIcon icon = new ImageIcon("img/allDelete.png");
		private Image imgMain = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imgMain, 0, 0, getWidth(), getHeight(), null);
			setBorderPainted(false); // 버튼 테두리 제거
		}
	};

	public GameStart() {
		init();
		setting();
		batch();
		listener();
		setVisible(true);
	}

	private void init() {
		// 이미지 패널
	
		plMain = new MyPanel(); // 초기 메인 화면 이미지
		plTopMpId = new MyPanel1(); // plMplId - 그림판 이미지
		plPalette = new MyPanel2(); // plBottom - 크레파스 이미지
		//btnEraser = new MyButton(); // plBottom - 지우개 이미지
		btnDelete = new MyButton1(); // plBottom - 휴지통 이미지

		// 패널
		plId = new JPanel(); // plMain - 초기 아이디 입력 패널
		plSub = new JPanel(); // plMain - 초기 아이디 입력 패널
		plDrawRoom = new JPanel(); // 아이디를 입력하고 버튼을 누르면 나오는 패널, 게임화면 전체

		// plDrawRoom
		plTop = new JPanel();
		plMplId = new JPanel();
		plBottom = new JPanel();
		plEast = new JPanel();
		btnPanel = new JPanel();

		// plEast
		plChat = new JPanel();

		// 이미지
		icGameStart = new ImageIcon("img/gameStart.png"); // 게임시작 버튼 이미지

		iconBlackPen = new ImageIcon("img/drawBlackPen.png");
		iconRedPen = new ImageIcon("img/drawRedPen.png");
		iconOrangePen = new ImageIcon("img/drawOrangePen.png");
		iconYellowPen = new ImageIcon("img/drawYellowPen.png");
		iconGreenPen = new ImageIcon("img/drawGreenPen.png");
		iconBluePen = new ImageIcon("img/drawBluePen.png");
		iconIndigoPen = new ImageIcon("img/drawIndigoPen.png");
		iconPurplePen = new ImageIcon("img/drawPurplePen.png");

		// 버튼
		btnStart = new JButton(icGameStart); // plMain
		btnId = new JButton(icGameStart); // plMain
		btnSkip = new JButton("넘기기"); // plTop
		btnReady = new JButton("준비"); // plEast
		btnExit = new JButton("나가기"); // plEast
		
		voiceStart = new JButton("녹음");
		voiceEnd = new JButton("제출");

		btnBlackDrawPen = new JButton(iconBlackPen);
		btnRedDrawPen = new JButton(iconRedPen);
		btnOrangeDrawPen = new JButton(iconOrangePen);
		btnYellowDrawPen = new JButton(iconYellowPen);
		btnGreenDrawPen = new JButton(iconGreenPen);
		//btnBlueDrawPen = new JButton(iconBluePen);
		btnIndigoDrawPen = new JButton(iconIndigoPen);
		//btnPurpleDrawPen = new JButton(iconPurplePen);

		// 라벨
		laId = new JLabel("아이디"); // plMain
		laQuizTitle = new JLabel("제시어");
		laQuiz = new JLabel("준비 Click!"); // plTop
		userScore = new JLabel("Score : " + scoreValue);

		// 텍스트 입력란
		tfIdInput = new TextField(); // plMain
		//tfChat = new TextField(); // plEast

		// 텍스트 영역
		//taChat = new JTextArea(); // plEast
		taUserList = new JTextArea();
		// 폰트
		ftSmall = new Font("맑은고딕", Font.PLAIN, 16);
		ftMedium = new Font("맑은고딕", Font.PLAIN, 24);
		ftLarge = new Font("맑은고딕", Font.PLAIN, 36);

		// 스크롤 바
		//scrChat = new JScrollPane(taChat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				//JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// 드로우 캔버스
		imgBuff = new BufferedImage(750, 450, BufferedImage.TYPE_INT_ARGB);
		drawLabel = new JLabel(new ImageIcon(imgBuff));
		drawPanel = new JPanel();
		brush = new Brush();
	}

	private void setting() {
		setTitle("캐치마인드");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// plMain
		setContentPane(plMain);
		plMain.setLayout(null);
		btnStart.setBounds(160, 300, 180, 110); // btnStart 위치, 크기 조정 (x, y, width, height)
		btnStart.setBorderPainted(false); // 버튼 테두리 제거

		icGameStart = ImageSetSize(icGameStart, 180, 110); // 게임 시작 버튼 이미지
		// plId
		plId.setLayout(null);
		plId.setVisible(false); // 비활성화
		plId.setBackground(new Color(255, 255, 255));
		plId.setBounds(50, 200, 420, 300); // plId 위치, 크기 조정 (x, y, width, height) 좌표는 plMain 기준
		
		  exitbtn = new JButton("EXIT");
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

		plSub.setLayout(null);
		plSub.setVisible(false); // 비활성화
		plSub.setBorder(new LineBorder(new Color(87, 87, 87), 3, true));
		plSub.setBounds(90, 50, 246, 36); // plId 위치, 크기 조정 (x, y, width, height) 좌표는 plMain 기준

		laId.setBounds(0, 2, 62, 32); // laId 위치, 크기 조정 (x, y, width, height) 좌표는 plId 기준
		laId.setBorder(new LineBorder(new Color(87, 87, 87), 2, true));
		laId.setFont(ftSmall);
		laId.setHorizontalAlignment(JLabel.CENTER); // 글자 가운데 정렬

		tfIdInput.setBounds(63, 3, 180, 30); // tfIdInput 위치, 크기 조정 (x, y, width, height) 좌표는 plId 기준
		tfIdInput.setBackground(new Color(242, 242, 242, 255));
		tfIdInput.setFont(ftMedium);

		btnId.setBounds(120, 150, 180, 110); // btnId 위치, 크기 조정 (x, y, width, height) 좌표는 plId 기준
		btnId.setBorderPainted(false); // 버튼 테두리 제거

		// plDrawRoom
		plDrawRoom.setLayout(null);
		plDrawRoom.setVisible(false); // 비활성화
		plDrawRoom.setBounds(0, 0, 820, 660);// plDrawRoom 위치, 크기 조정 좌표는 plMain 기준

		// plDrawRoom - plTopMpId
		plTopMpId.setLayout(null);
		plTopMpId.setBackground(new Color(255, 255, 255, 255));
		plTopMpId.setBounds(0, 0, 500, 530);

		// plDrawRoom - plTop
		plTop.setLayout(null);
		plTop.setBackground(new Color(255, 255, 255, 0));
		plTop.setBounds(0, 0, 500, 80); // plTop 위치, 크기 조정 좌표는 plDrawRoom 기준

		// plDrawRoom - plMplId
		plMplId.setLayout(null);
		plMplId.setBackground(new Color(255, 255, 255, 255));
		plMplId.setBounds(0, 110, 500, 450); // plMplId 위치, 크기 조정 좌표는 plDrawRoom 기준

		// plDrawRoom - plBottom
		plBottom.setLayout(null);
		plBottom.setBackground(new Color(255, 255, 255, 255));
		plBottom.setBounds(0, 530, 500, 130); // plBottom 위치, 크기 조정 좌표는 plDrawRoom 기준

		iconBlackPen = ImageSetSize(iconBlackPen, 65, 130);
		iconRedPen = ImageSetSize(iconRedPen, 65, 130);
		iconOrangePen = ImageSetSize(iconOrangePen, 65, 130);
		iconYellowPen = ImageSetSize(iconYellowPen, 65, 130);
		iconGreenPen = ImageSetSize(iconGreenPen, 65, 130);
		iconBluePen = ImageSetSize(iconBluePen, 65, 130);
		iconIndigoPen = ImageSetSize(iconIndigoPen, 65, 130);
		iconPurplePen = ImageSetSize(iconPurplePen, 65, 130);

		btnBlackDrawPen.setBackground(new Color(255, 255, 255, 255));
		btnBlackDrawPen.setBounds(0, 0, 65, 130);
		btnBlackDrawPen.setBorderPainted(false); // 버튼 테두리 제거

		btnRedDrawPen.setBackground(new Color(255, 255, 255, 255));
		btnRedDrawPen.setBounds(65, 0, 65, 130);
		btnRedDrawPen.setBorderPainted(false); // 버튼 테두리 제거

		btnOrangeDrawPen.setBackground(new Color(255, 255, 255, 255));
		btnOrangeDrawPen.setBounds(130, 0, 65, 130);
		btnOrangeDrawPen.setBorderPainted(false); // 버튼 테두리 제거

		btnYellowDrawPen.setBackground(new Color(255, 255, 255, 255));
		btnYellowDrawPen.setBounds(195, 0, 65, 130);
		btnYellowDrawPen.setBorderPainted(false); // 버튼 테두리 제거

		btnGreenDrawPen.setBackground(new Color(255, 255, 255, 255));
		btnGreenDrawPen.setBounds(260, 0, 65, 130);
		btnGreenDrawPen.setBorderPainted(false); // 버튼 테두리 제거
/*
		btnBlueDrawPen.setBackground(new Color(242, 242, 242, 255));
		btnBlueDrawPen.setBounds(325, 0, 65, 130);
		btnBlueDrawPen.setBorderPainted(false); // 버튼 테두리 제거
*/
		btnIndigoDrawPen.setBackground(new Color(255, 255, 255, 255));
		btnIndigoDrawPen.setBounds(325, 0, 65, 130);
		btnIndigoDrawPen.setBorderPainted(false); // 버튼 테두리 제거
/*
		btnPurpleDrawPen.setBackground(new Color(242, 242, 242, 255));
		btnPurpleDrawPen.setBounds(455, 0, 65, 130);
		btnPurpleDrawPen.setBorderPainted(false); // 버튼 테두리 제거
*/
		// plDrawRoom - plEast
		//채팅창
		plEast.setLayout(null);
		plEast.setBounds(500, 0, 320, 530); // plEast 위치, 크기 조정 좌표는 plDrawRoom 기준

		// plDrawRoom - plChat
		plChat.setLayout(null);

		// plDrawRoom - btnPanel
		btnPanel.setLayout(null);
		btnPanel.setBackground(new Color(242, 242, 242, 255));
		btnPanel.setBounds(500, 530, 405, 130);

		// plTop

		// plMplId
//		plDraw.setBackground(new Color(242, 242, 242, 255));
//		plDraw.setBounds(0, 0, 750, 420); // plDraw 위치, 크기 조정 좌표는 plMplId 기준

		// plBottom
		plPalette.setLayout(null);
		plPalette.setBackground(new Color(255, 255, 255, 255));
		plPalette.setBounds(0, 0, 520, 130); // plPalette 위치, 크기 조정 좌표는 plBottom 기준

		

		btnDelete.setBackground(new Color(255, 255, 255, 255));
		btnDelete.setBounds(390, 0, 120, 130); // btnEraser 위치, 크기 조정 좌표는 plBottom 기준

		// plEast 사용자 접속자 나타내는 창
		taUserList.setBounds(0, 0, 320, 200); // taUserList 위치, 크기 조정 좌표는 plEast 기준
		taUserList.setFont(ftMedium);
		taUserList.setBackground(new Color(255, 255, 255, 255));
		taUserList.setLineWrap(true);
/*
		plChat.setBackground(Color.WHITE);
		plChat.setBounds(0, 150, 255, 385); // plChat 위치, 크기 조정 좌표는 plEast 기준
*/
		// plEast - plChat
		
		
		// btnPanel
		laQuizTitle.setVisible(true);
		laQuizTitle.setBounds(0, 2, 155, 65); // laQuiz 위치, 크기 조정 좌표는 plTop 기준
		laQuizTitle.setFont(ftMedium);
		laQuizTitle.setBackground(new Color(242, 242, 242, 255));
		laQuizTitle.setHorizontalAlignment(JLabel.CENTER); // 글자 가운데 정렬

		laQuiz.setVisible(true);
		laQuiz.setBounds(0, 67, 155, 65); // laQuiz 위치, 크기 조정 좌표는 plTop 기준
		laQuiz.setFont(ftMedium);
		laQuiz.setBackground(new Color(242, 242, 242, 255));
		laQuiz.setHorizontalAlignment(JLabel.CENTER); // 글자 가운데 정렬
		
		userScore.setVisible(true);
		userScore.setBounds(0, 200, 320, 100); // laQuiz 위치, 크기 조정 좌표는 plTop 기준
		userScore.setFont(ftMedium);
		userScore.setBackground(new Color(242, 242, 242, 255));
		userScore.setHorizontalAlignment(JLabel.CENTER); // 글자 가운데 정렬


		btnReady.setBounds(150, 2, 155, 65); // btnReady 위치, 크기 조정 좌표는 plEast 기준
		btnReady.setFont(ftMedium);
		btnReady.setBackground(new Color(242, 242, 242, 255));
		btnReady.setBorder(new LineBorder(new Color(87, 87, 87), 5, true));

		voiceStart.setVisible(true);
		voiceStart.setBounds(0, 300, 300, 100); // btnSkip 위치, 크기 조정 좌표는 plTop 기준
		voiceStart.setFont(ftMedium);
		voiceStart.setBackground(new Color(242, 242, 242, 255));
		voiceStart.setBorder(new LineBorder(new Color(87, 87, 87), 5, true));
		
		voiceEnd.setVisible(true);
		voiceEnd.setBounds(0, 420, 300, 100); // btnSkip 위치, 크기 조정 좌표는 plTop 기준
		voiceEnd.setFont(ftMedium);
		voiceEnd.setBackground(new Color(242, 242, 242, 255));
		voiceEnd.setBorder(new LineBorder(new Color(87, 87, 87), 5, true));
		
		voiceStart.setEnabled(true);
	    voiceEnd.setEnabled(false);
	    
	    ActionListener captureListener =
			     new ActionListener() {
			     	public void actionPerformed(ActionEvent e) {
			     		System.out.println("hhhhhhhhhhhhhhhhh");
			     		voiceStart.setEnabled(false);
			     		voiceEnd.setEnabled(true);
			     		captureAudio();
			     	}
			      };
			      voiceStart.addActionListener(captureListener);
			    
			    
		ActionListener stopListener =
			      new ActionListener() {
			         public void actionPerformed(ActionEvent e) {
			        	 System.out.println("Eeeeeeeeeeeeeeeeeeeee");
			             voiceStart.setEnabled(true);
			             voiceEnd.setEnabled(false);
			             running = false;
			             try {
							playAudio();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			               
			          }
			        };
			      voiceEnd.addActionListener(stopListener);
	
		
		
		btnSkip.setVisible(false);
		btnSkip.setBounds(150, 2, 155, 65); // btnSkip 위치, 크기 조정 좌표는 plTop 기준
		btnSkip.setFont(ftMedium);
		btnSkip.setBackground(new Color(242, 242, 242, 255));
		btnSkip.setBorder(new LineBorder(new Color(87, 87, 87), 5, true));

		btnExit.setBounds(150, 62, 155, 65); // btnExit 위치, 크기 조정 좌표는 plEast 기준
		btnExit.setFont(ftMedium);
		btnExit.setBackground(new Color(242, 242, 242, 255));
		btnExit.setBorder(new LineBorder(new Color(87, 87, 87), 5, true));

		// 드로우 캔버스
		drawLabel.setBounds(0, 0, 750, 450);
		drawLabel.setBackground(new Color(255, 255, 255, 0));
		brush.setBounds(0, 0, 750, 450);

		setSize(500, 840);
	}

	private void batch() {
		plMain.add(btnStart);
		plMain.add(plId);
		plMain.add(plDrawRoom);
		plMain.add(exitbtn);
		btnStart.setIcon(icGameStart);

		plId.add(plSub);
		plSub.add(laId);
		plSub.add(tfIdInput);
		plId.add(btnId);
		btnId.setIcon(icGameStart);

		plDrawRoom.add(plTopMpId);

		plTopMpId.add(plTop);
		plTopMpId.add(plMplId);

		plDrawRoom.add(plBottom);
		plDrawRoom.add(plEast);
		plDrawRoom.add(btnPanel);

//		plMplId.add(plDraw);

		plBottom.add(plPalette);
		//plBottom.add(btnEraser);
		plBottom.add(btnDelete);

		plPalette.add(btnBlackDrawPen);
		plPalette.add(btnRedDrawPen);
		plPalette.add(btnOrangeDrawPen);
		plPalette.add(btnYellowDrawPen);
		plPalette.add(btnGreenDrawPen);
		//plPalette.add(btnBlueDrawPen);
		plPalette.add(btnIndigoDrawPen);
		//plPalette.add(btnPurpleDrawPen);

		plEast.add(voiceStart);
		plEast.add(voiceEnd);
		
		plEast.add(taUserList);
		plEast.add(userScore);		//plChat.add(scrChat);
		//plChat.add(tfChat);

		btnPanel.add(laQuiz);
		btnPanel.add(laQuizTitle);
		btnPanel.add(btnReady);
		btnPanel.add(btnSkip);
		btnPanel.add(btnExit);

		// 드로우
		plMplId.add(drawLabel);
		plMplId.add(brush);

	}

	private void listener() {
		
		/*
		// Enter 입력시 채팅 메세지가 보내지는 이벤트.
		tfChat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendChat();
			}
		});
		
		*/

		// 이 이벤트로 plId이 활성화 되어서 아이디를 입력할 수 있음.
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btnStart = (JButton) e.getSource();
				plId.setVisible(true); // plId 활성화
				plSub.setVisible(true); // plId 활성화
				btnStart.setVisible(false); // btnStart 비활성화
			}
		});

		// 이 이벤트로 plDrawRoom이 활성화 되어서 그리기방에 입장함.
		btnId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JButton btnId = (JButton)e.getSource();
				connectServer(); // 서버와 연결
				sendInsertId();
			}
		});
		
				        

		// 나가기 버튼 이벤트.
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendExit();
				System.exit(0);
			}
		});

		// 준비 버튼 이벤트.
		btnReady.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendReady();
			}
		});

		// 넘기기 버튼 이벤트.
		btnSkip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendSkip();
			}
		});
		// 마우스를 눌렀을때 그리는 이벤트

		drawLabel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (drawPPAP == true) {
					System.out.println("ppap true 실행 됨");
					sendDraw = "DRAW&" + e.getX() + "," + e.getY();
					brush.xx = e.getX();
					brush.yy = e.getY();
					brush.repaint();
					brush.printAll(imgBuff.getGraphics());
					writer.println(sendDraw);
				} else {
					System.out.println("ppap false 실행 됨");
				}
			}
		});

		// 검은색 펜 이벤트
		btnBlackDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Black";
				brush.setColor(Color.BLACK);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		// 빨간색 펜 이벤트
		btnRedDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Red";
				brush.setColor(Color.RED);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		// 오렌지색 펜 이벤트
		btnOrangeDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Orange";
				brush.setColor(Color.ORANGE);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		// 노란색 펜 이벤트
		btnYellowDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Yellow";
				brush.setColor(Color.YELLOW);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		// 초록색 펜 이벤트
		btnGreenDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Green";
				brush.setColor(Color.GREEN);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		/*
		// 하늘색 펜 이벤트
		btnBlueDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Blue";
				brush.setColor(Color.CYAN);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});*/
		// 파란색 펜 이벤트
		btnIndigoDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Indigo";
				brush.setColor(Color.BLUE);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		/*
		// 핑크색 펜 이벤트
		btnPurpleDrawPen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendColor = "COLOR&" + "Purple";
				brush.setColor(Color.PINK);
				writer.println(sendColor);
				System.out.println("색 변경 : " + sendColor);
			}
		});
		*/
		// 지우개(흰색) 이벤트
		
		// 드로우 캔버스 초기화 이벤트
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("delete 버튼 눌러짐");
				sendColor = "COLOR&" + "Delete";
				writer.println(sendColor);
				brush.setClearC(false);
				cleanDraw();
				System.out.println("드로우 캔버스 초기화");
			}
		});
	}

	
	 private AudioFormat getFormat() {
		    float sampleRate = 8000;
		    int sampleSizeInBits = 16;
		    int channels = 1;
		    boolean signed = true;
		    boolean bigEndian = true;
		    return new AudioFormat(sampleRate,
		      sampleSizeInBits, channels, signed, bigEndian);
		  }

	 
	  private void captureAudio() {
		  
			try {
		      final AudioFormat format = getFormat();
		      DataLine.Info info = new DataLine.Info(
		        TargetDataLine.class, format);
		      final TargetDataLine line = (TargetDataLine)
		        AudioSystem.getLine(info);
		      line.open(format);
		      line.start();
		      Runnable runner = new Runnable() {
		    	 
		        int bufferSize = (int)format.getSampleRate()
		          * format.getFrameSize();
		        byte buffer[] = new byte[bufferSize];
		 
		        public void run() {
		          out = new ByteArrayOutputStream();
		          running = true;
		          try {
		            while (running) {
		              int count =
		                line.read(buffer, 0, buffer.length);
		              if (count > 0) {
		                out.write(buffer, 0, count);
		              }
		            }
		            out.close();
		          } catch (IOException e) {
		            System.err.println("I/O problems: " + e);
		            System.exit(-1);
		          }
		        }
		      };
		      Thread captureThread = new Thread(runner);
		      captureThread.start();
		    } catch (LineUnavailableException e) {
		      System.err.println("Line unavailable: " + e);
		      System.exit(-2);
		    }
		  }
	  
	  private void playAudio() throws IOException {
		  
		  
	        Socket socket = new Socket();
	        //주소 생성
	        SocketAddress address = new InetSocketAddress("172.28.112.1", 3030);
	        //주소에 해당하는 서버랑 연결
	        socket.connect(address);
	        
		    try {
		      byte audio[] = out.toByteArray();
		      
		      System.out.println(audio);
		      
		      try {
		            //보내기
		        	 OutputStream os = socket.getOutputStream();
		             os.write(audio);
		        	 //출력 스트림에 데이터 씀
		             
		             os.flush();

				      scoreValue = scoreValue +10;
				      userScore.setText("Score : " + scoreValue);
						
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		      socket.close();
		      
		      
		      
		      InputStream input =
		        new ByteArrayInputStream(audio);
		      final AudioFormat format = getFormat();
		      final AudioInputStream ais =
		        new AudioInputStream(input, format,
		        audio.length / format.getFrameSize());
		      
		      DataLine.Info info = new DataLine.Info(
		        SourceDataLine.class, format);
		      final SourceDataLine line = (SourceDataLine)
		        AudioSystem.getLine(info);
		      File waveFile = new File("record.wav");
		      AudioSystem.write(ais, AudioFileFormat.Type.WAVE, waveFile);

		      ais.close();
		      
		      line.open(format);
		      line.start();

		      Runnable runner = new Runnable() {
		        int bufferSize = (int) format.getSampleRate()
		          * format.getFrameSize();
		        byte buffer[] = new byte[bufferSize];
		 
		        public void run() {
		          try {
		            int count;
		            while ((count = ais.read(
		                buffer, 0, buffer.length)) != -1) {
		              if (count > 0) {
		                line.write(buffer, 0, count);
		              }
		            }
		            line.drain();
		            line.close();
		          } catch (IOException e) {
		            System.err.println("I/O problems: " + e);
		            System.exit(-3);
		          }
		        }
		      };
		      Thread playThread = new Thread(runner);
		      playThread.start();
		    } catch (LineUnavailableException e) {
		      System.err.println("Line unavailable: " + e);
		      System.exit(-4);
		    } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    
		    
		  }


	
	
	
	// 접속 시 서버 연결 메서드.
	private void connectServer() {
		try {
			socket = new Socket("localhost", 3000);
			ReaderThread rt = new ReaderThread();
			rt.start();
		} catch (Exception e) {
			System.out.println(TAG + "서버 연결 실패");
		}
	}

	// EXIT 프로토콜 메서드.
	private void sendExit() {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("EXIT&" + IDString);
		} catch (Exception e) {
			System.out.println(TAG + "Exit Msg writer fail...");
		}
	}

	// SKIP 프로토콜 메서드.
	private void sendSkip() {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("SKIP&");
		} catch (Exception e) {
			System.out.println(TAG + "Skip Msg writer fail...");
		}
	}

	// READY 프로토콜 메서드.
	private void sendReady() {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("READY&");
		} catch (Exception e) {
			System.out.println(TAG + "Ready Msg send fail...");
		}

	}
	
	/*

	// CHAT 프로토콜 메서드.
	private void sendChat() {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			String chatString = tfChat.getText();
			writer.println("CHAT&" + chatString);
			tfChat.setText("");
		} catch (Exception e) {
			System.out.println(TAG + "채팅 메세지 요청 실패");
		}
	}
	
	*/

	// ID 프로토콜 메서드
	private void sendInsertId() {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			IDString = tfIdInput.getText();
			if ((IDString.equals(""))) { // NULL값 입력시
				IDString = "emptyID";
				writer.println("ID&" + IDString);
				plId.setVisible(false); // plId 비활성화
				plSub.setVisible(false); // plId 활성화
				plDrawRoom.setVisible(true); // plDrawRoom 활성화
				setSize(820, 840);
			} else { // 아이디 값 입력시.
				writer.println("ID&" + IDString);
				tfIdInput.setText("");
				plId.setVisible(false); // plId 비활성화
				plSub.setVisible(false); // plId 활성화
				plDrawRoom.setVisible(true); // plDrawRoom 활성화
				setSize(820, 840);
			}

		} catch (IOException e) {
			System.out.println(TAG + "준비 메세지 요청 실패");
		}
	}

	// 드로우 캔버스 초기화 메서드
	private void cleanDraw() {
		brush.setClearC(false);
		brush.repaint();
		brush.printAll(imgBuff.getGraphics());
	}

	// 서버로 부터 메세지를 받아 TextArea에 뿌려주는 Thread.
	class ReaderThread extends Thread {
		private BufferedReader reader;

		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String readerMsg = null;
				String[] parsReaderMsg;
				
				while ((readerMsg = reader.readLine()) != null) {
					System.out.println("dfdf");
					parsReaderMsg = readerMsg.split("&");
					if (parsReaderMsg[0].equals("DRAW")) {
						String[] drawM = parsReaderMsg[1].split(",");
						x = Integer.parseInt(drawM[0]);
						y = Integer.parseInt(drawM[1]);
						brush.setX(x);
						brush.setY(y);
						brush.repaint();
						brush.printAll(imgBuff.getGraphics());
						System.out.println("브러쉬 값 : " + drawM);
						System.out.println("브러쉬 값 : " + x);
						System.out.println("브러쉬 값 : " + y);
					} else if (parsReaderMsg[0].equals("COLOR")) {
						System.out.println("색 변경 요청 들어옴");
						if (parsReaderMsg[1].equals("Black")) {
							System.out.println("검은색 요청");
							brush.setColor(Color.BLACK);
						} else if (parsReaderMsg[1].equals("Red")) {
							System.out.println("빨간색 요청");
							brush.setColor(Color.RED);
						} else if (parsReaderMsg[1].equals("Orange")) {
							System.out.println("주황색 요청");
							brush.setColor(Color.ORANGE);
						} else if (parsReaderMsg[1].equals("Yellow")) {
							System.out.println("노랑색 요청");
							brush.setColor(Color.YELLOW);
						} else if (parsReaderMsg[1].equals("Green")) {
							System.out.println("초록색 요청");
							brush.setColor(Color.GREEN);
						} else if (parsReaderMsg[1].equals("Blue")) {
							System.out.println("파랑색 요청");
							brush.setColor(Color.CYAN);
						} else if (parsReaderMsg[1].equals("Indigo")) {
							System.out.println("인디고 위졋고 휘졋고 오졋고 요청");
							brush.setColor(Color.BLUE);
						} else if (parsReaderMsg[1].equals("Purple")) {
							System.out.println("퍼플같은 핑크 요청");
							brush.setColor(Color.PINK);
						} else if (parsReaderMsg[1].equals("White")) {
							System.out.println("지우개 요청");
							brush.setColor(Color.WHITE);
						} else if (parsReaderMsg[1].equals("Delete")) {
							System.out.println("화면 리셋 요청");
							brush.setClearC(false);
							brush.repaint();
							brush.printAll(imgBuff.getGraphics());
						}
					} else if (parsReaderMsg[0].equals("START")) {
						btnReady.setVisible(false);

					} else if (parsReaderMsg[0].equals("ID")) {
						taUserList.setText("");
					} else if (parsReaderMsg[0].equals("IDLIST")) {
						taUserList.append(parsReaderMsg[1] + "\n");
					} else if (parsReaderMsg[0].equals("TURN")) {
						laQuiz.setText(problem[selectProblem]);
						laQuiz.setVisible(true);
						btnSkip.setVisible(true);
						drawPPAP = true;
						//tfChat.setEnabled(false);
						plBottom.setVisible(true);
						System.out.println("내 턴 임");
					} else if (parsReaderMsg[0].equals("NOTTURN")) {
						laQuiz.setVisible(false);
						btnSkip.setVisible(false);
						System.out.println("내 턴 아님");
						brush.setDrawPen(false);
						drawPPAP = false;
						//tfChat.setEnabled(true);
						plBottom.setVisible(false);
						System.out.println(drawPPAP);
					} else if (parsReaderMsg[0].equals("ANSWER")) {
						selectProblem++;
						if (selectProblem >= problem.length) {
							selectProblem = 0;
						}
					} else if (parsReaderMsg[0].equals("END")) {
						//taChat.append("[SERVER]: " + parsReaderMsg[1] + "\n");
						btnReady.setVisible(true);
						//tfChat.setEnabled(true);
						plBottom.setVisible(true);
						btnSkip.setVisible(false);
						btnReady.setVisible(true);
						laQuiz.setVisible(false);
						drawPPAP = true;
					} else {
						//taChat.append("\n");
					}
					// 스크롤을 밑으로 고정.
					//scrChat.getVerticalScrollBar().setValue(scrChat.getVerticalScrollBar().getMaximum());
				}
			} catch (IOException e) {
				System.out.println(TAG + "통신 실패");
			}
		}
	}

	// 그리기 위한 펜을 만들어 주는 클래스
	class Brush extends JLabel {
		public int xx, yy;
		public Color color = Color.BLACK;
		public boolean drawPen = true;
		public boolean clearC = true;

		@Override
		public void paint(Graphics g) {
			if (drawPen == true) {
				g.setColor(color);
				g.fillOval(xx - 10, yy - 10, 10, 10);
				System.out.println(drawPPAP);
			} else if (drawPen == false) {
				g.setColor(Color.WHITE);
				g.fillOval(0, 0, 0, 0);
				System.out.println(drawPPAP);
				System.out.println("브러쉬 사용 못 하게 막음");
			}
			if (clearC == true) {
				g.setColor(color);
				g.fillOval(xx - 10, yy - 10, 10, 10);
			} else if (clearC == false) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 1000, 1000);
				clearC = true;
				System.out.println("캔버스 클리어 실행됨");
			}

		}

		public void setX(int x) {
			this.xx = x;
		}

		public void setY(int y) {
			this.yy = y;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public void setDrawPen(boolean drawPen) {
			this.drawPen = drawPen;
		}

		public void setClearC(boolean clearC) {
			this.clearC = clearC;
		}
	}

	public static void main(String[] args) {
		new GameStart();
		
	}
}