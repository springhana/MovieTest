package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Expert.Reservation;

public class StartSwing extends JFrame {
	
	static JPanel contentPane;
	static JPanel initPanel;
	static JPanel loginPanel;
	static JPanel mListPanel;
	static JPanel mMovieAddPanel;
	
	static JList<String> movieList, movieListForDrop;
	static JList<String> reservationList, rservationDrop;
	static JScrollPane mRListScroll;
	static JPanel mRDropPanel;
	
	static MenuActionListener listener;
	static MovieDropListener listenerForDrop;
	static ReservationDropListener reservationListForDrop;
	static LoginKeyListener keylistener;
	static MovieAddKeyListener movieaddkeylistener;
	
	static JLabel loginHeadLabel, loginGuideLabel;                      //loginPanel용
	static JPanel logPanel;
	static JPasswordField adminPwd;
	
	static JLabel mHeadLabel;                                            //Manager movie List Panel...mListPanel용
	static JScrollPane mMovieListScroll;
	
	static JLabel mMovieAddHeadLabel, mMovieAddMNameGuideLabel,mMovieAddMGanreGuideLabel;                      //manager ... movie add panel용
	static JTextField mMovieNameTextField, mMovieGenreTextField;
	static JPanel mMoviePanel;
	
	
	static JPanel mMovieDropPanel;
	static JLabel mMovieDropHeadLabel;                                            //Manager movie Drop Panel...mListPanel용
	static JScrollPane movieListScroll;
	static String movieId,movieName;
	
	static String resevationId;
	
	static JPanel reserveCheckPanel;
	static JLabel rcHeadLabel, rcGuideLabel;                      //reserveCheckPanel용
	static JPanel rcPanel;
	static JTextField rcTextField;
	
	static JLabel canHeadLabel, canGuideLabel;                      //cancelPanel용
	static JPanel canPanel;
	static JTextField canTextField;
	static JPanel cancelPanel;
	
	static JPanel reservePanel;
	static JLabel headLabel; 
	
	static JPanel seatPanel;
	
	static String[] MAIN_MENU_TEXT = {"예매", "관리자", "종료"};
	static String[] RESV_MENU_TEXT = {"예매하기","확인하기","취소하기"};
	static String[] MAN_MENU_TEXT =  {"로그인","영화목록","영화추가","영화삭제","예매목록","예매삭제"};
	static String SYS_MENU_TEXT = "종료";
	static boolean mode = false;    //관리자 모드 유무  true/관리자모드
	static JMenu mainMenu[] = new JMenu[3];
	static JMenuItem[] uMenuItem = new JMenuItem[3];
	static JMenuItem[] mMenuItem  = new JMenuItem[6];
	static JMenuItem xMenuItem;
	
	static String[] reservationsList;
	static JPanel RPanel;
	
	
	public static void change(String panelName) {
		if(panelName.equals("login")) {
			contentPane.removeAll();
			createMenu();
			if(!mode) {
				createLoginPanel();
			}else {
				createInitPanel("영화 예매 시스템 관리자 모드입니다.");
				contentPane.add(loginPanel,BorderLayout.CENTER);
			}
			contentPane.revalidate();
			contentPane.repaint();
		}else if (panelName.equals("logout")) {
			StartSwing.mMenuItem[0].setText("로그인");
			mode = false;
	    	contentPane.removeAll();    	
			createMenu();
			if(!mode) {
//				createLoginPanel();
//				contentPane.add(loginPanel,BorderLayout.CENTER);
				createInitPanel("영화예매 시스템에 오신 것을 환영합니다.");
			}
			else {
				createInitPanel("영화예매 시스템 관리자 모드입니다.");
			}
			contentPane.revalidate();
			contentPane.repaint();
		}else if (panelName.equals("list")) {
	    	contentPane.removeAll();    
    		createMenu();
    		createMListPanel();
    		contentPane.add(mListPanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();   		
	    }else if (panelName.equals("add")) {
	    	contentPane.removeAll();    
    		createMenu();
    		createMovieAddPanel();
    		contentPane.add(mMovieAddPanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();   		
	    }else if (panelName.equals("drop")) {
	    	contentPane.removeAll();    
    		createMenu();
    		createMovieDropPanel();
    		contentPane.add(mMovieDropPanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();   		
	    }else if(panelName.equals("rc")) {
	    	contentPane.removeAll();
	    	createMenu();
	    	createReserveCheckPanel();	
	    	contentPane.add(reserveCheckPanel,BorderLayout.CENTER);
	    	contentPane.revalidate();
	    	contentPane.repaint();
	    }else if(panelName.equals("cancel")) {
	    	contentPane.removeAll();
    		createMenu();
    		createCancelReservePanel();
    		contentPane.add(cancelPanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();   
	    }else if(panelName.equals("r")) {
	    	contentPane.removeAll();
    		createMenu();
    		createReservePanel();
    		contentPane.add(reservePanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();   	
	    }else if(panelName.equals("reservations")) {
	      	contentPane.removeAll();    
    		createMenu();
    		createMReservationListPanel();
    		contentPane.add(RPanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();  
	    }else if(panelName.equals("reservationsDrop")) {
	    	contentPane.removeAll();    
    		createMenu();
    		createReservationDropPanel();
    		contentPane.add(mRDropPanel,BorderLayout.CENTER);
    		contentPane.revalidate();
    		contentPane.repaint();   
	    }
	}
	
	static void createReservationDropPanel() {
		mMovieDropHeadLabel = new JLabel("삭제할 예매를 선택하세요.");
		mRDropPanel = new JPanel();
		mRDropPanel.setSize(500, 350);
		mRDropPanel.setLayout(new BorderLayout());
		
		mRDropPanel.add(mMovieDropHeadLabel,BorderLayout.NORTH);
		try {
			ArrayList<Reservation> reservations = Reservation.findAll();
			reservationsList = new String[reservations.size()];
			for(int i=0; i < reservations.size(); i++) {
				reservationsList[i] = reservations.get(i).toString();
			}
		}catch(IOException e) {
			System.out.println("예약 목록 접속에 실패했습니다.");
		}
	

		rservationDrop = new JList<String>(reservationsList);
		rservationDrop.setFixedCellWidth(300);
		rservationDrop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		

		rservationDrop.addListSelectionListener(reservationListForDrop);
		rservationDrop.setVisible(true);
		mRListScroll = new JScrollPane(rservationDrop);
		mRDropPanel.add(mRListScroll, BorderLayout.CENTER);		
		
		contentPane.add(mRDropPanel,BorderLayout.CENTER);
	}
	
	static void createMReservationListPanel() {
		mHeadLabel = new JLabel("등록된 예매 목록");
		RPanel = new JPanel();
		RPanel.setSize(500, 350);
		RPanel.setLayout(new BorderLayout());
		
		RPanel.add(mHeadLabel,BorderLayout.NORTH);	
		try {
			ArrayList<Reservation> reservations = Reservation.findAll();
			reservationsList = new String[reservations.size()];
			for(int i=0; i < reservations.size(); i++) {
				reservationsList[i] = reservations.get(i).toString();
			}
		}catch(IOException e){
			System.out.println("예약 목록 접속에 실패했습니다.");
		}
		
		reservationList = new JList<String>(reservationsList);
		reservationList.setFixedCellWidth(300);
		reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		reservationList.addListSelectionListener(listener);
		reservationList.setVisible(true);
		mRListScroll = new JScrollPane(reservationList);
		RPanel.add(mRListScroll, BorderLayout.CENTER);		
		
		contentPane.add(RPanel,BorderLayout.CENTER);
		}

	
	static void createReservePanel() {
		headLabel = new JLabel("예매할 영화를 선택하세요.");
		reservePanel = new JPanel();
		reservePanel.setSize(500, 350);
		reservePanel.setLayout(new BorderLayout());
		
		reservePanel.add(headLabel,BorderLayout.NORTH);	
		ArrayList<Movie1> movies = Movie1.findAll();
		String[] moviesList = new String[movies.size()];
		for(int i=0; i < movies.size(); i++) {
			moviesList[i] = movies.get(i).toString();
		}

		movieList = new JList<String>(moviesList);
		movieList.setFixedCellWidth(300);
		movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movieList.addListSelectionListener(listener);
		movieList.setVisible(true);
		movieListScroll = new JScrollPane(movieList);
		reservePanel.add(movieListScroll, BorderLayout.CENTER);		
		
		contentPane.add(reservePanel,BorderLayout.CENTER);
	}
	
	static void createCancelReservePanel() {
		cancelPanel = new JPanel();
		cancelPanel.setSize(500, 350);
		cancelPanel.setLayout(new BorderLayout());
		
		canHeadLabel = new JLabel("삭제할 예약번호를 입력하세요");
		
		canPanel = new JPanel();	
		canPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
		canGuideLabel = new JLabel("예약번호");
		canTextField = new JTextField(20);
		canPanel.add(canGuideLabel);
		canPanel.add(canTextField);
		JButton btn = new JButton("예약삭제");
		btn.addActionListener(listener);
		canPanel.add(btn);
		
		cancelPanel.add(canHeadLabel,BorderLayout.NORTH);	
		cancelPanel.add(canPanel, BorderLayout.CENTER);	
		cancelPanel.setVisible(true);
		contentPane.add(cancelPanel,BorderLayout.CENTER);
	}
	
	static void createReserveCheckPanel() {
		reserveCheckPanel = new JPanel();
		reserveCheckPanel.setSize(500, 350);
		reserveCheckPanel.setLayout(new BorderLayout());
		
		rcHeadLabel = new JLabel("확인할 예약번호를 입력하세요");
		
		rcPanel = new JPanel();	
		rcPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
		rcGuideLabel = new JLabel("예약번호");
		rcTextField = new JTextField(20);
		rcPanel.add(rcGuideLabel);
		rcPanel.add(rcTextField);
		JButton btn = new JButton("예약확인");
		btn.addActionListener(listener);
		rcPanel.add(btn);
		
		reserveCheckPanel.add(rcHeadLabel,BorderLayout.NORTH);	
		reserveCheckPanel.add(rcPanel, BorderLayout.CENTER);	
		reserveCheckPanel.setVisible(true);
		contentPane.add(reserveCheckPanel,BorderLayout.CENTER);
	}
	
	static void createMovieDropPanel() {
		mMovieDropHeadLabel = new JLabel("삭제할 영화를 선택하세요.");
		mMovieDropPanel = new JPanel();
		mMovieDropPanel.setSize(500, 350);
		mMovieDropPanel.setLayout(new BorderLayout());
		
		mMovieDropPanel.add(mMovieDropHeadLabel,BorderLayout.NORTH);	
		ArrayList<Movie1> movies = Movie1.findAll();
		String[] moviesList = new String[movies.size()];
		for(int i=0; i < movies.size(); i++) {
			moviesList[i] = movies.get(i).toString();
		}

		movieListForDrop = new JList<String>(moviesList);
		movieListForDrop.setFixedCellWidth(300);
		movieListForDrop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		movieListForDrop.addListSelectionListener(listenerForDrop);
		movieListForDrop.setVisible(true);
		movieListScroll = new JScrollPane(movieListForDrop);
		mMovieDropPanel.add(movieListScroll, BorderLayout.CENTER);		
		
		contentPane.add(mMovieDropPanel,BorderLayout.CENTER);
	}
	
	static void createMovieAddPanel() {
		mMovieAddPanel = new JPanel();
		mMovieAddPanel.setSize(650, 350);
		mMovieAddPanel.setLayout(new BorderLayout());
		
		mMovieAddHeadLabel = new JLabel("추가할 정화 정보를 입력하세요");
		
		mMoviePanel = new JPanel();	
		mMoviePanel.setLayout(null);                   //new GridLayout(3,2));
		mMovieAddMNameGuideLabel = new JLabel("영화제목");
		mMovieNameTextField = new JTextField(20);	
		mMovieAddMGanreGuideLabel = new JLabel("영화장르");	
		mMovieGenreTextField = new JTextField(20);
		JButton btn1 = new JButton("추가확인");
		JButton btn2 = new JButton("추가취소");
		btn1.addActionListener(listener);
		btn2.addActionListener(listener);
		
		movieaddkeylistener = new MovieAddKeyListener();
		mMovieNameTextField.addKeyListener(movieaddkeylistener);
		mMovieGenreTextField.addKeyListener(movieaddkeylistener);
		
		mMovieAddMNameGuideLabel.setBounds(10, 20, 50, 30); // setBounds(x,y,WIDTH,HEIGHT);
		mMovieNameTextField.setBounds(80, 20, 250, 30);
		mMovieAddMGanreGuideLabel.setBounds(10, 70, 50, 30);
		mMovieGenreTextField.setBounds(80, 70, 250, 30);
		btn1.setBounds(10, 120, 100, 30);
		btn2.setBounds(200, 120, 100, 30);
		
		mMoviePanel.add(mMovieAddMNameGuideLabel);
		mMoviePanel.add(mMovieNameTextField);
		mMoviePanel.add(mMovieAddMGanreGuideLabel);		
		mMoviePanel.add(mMovieGenreTextField);
		mMoviePanel.add(btn1);
		mMoviePanel.add(btn2);
		
		mMovieAddPanel.add(mMovieAddHeadLabel,BorderLayout.NORTH);	
		mMovieAddPanel.add(mMoviePanel, BorderLayout.CENTER);	
		mMovieAddPanel.setVisible(true);
		contentPane.add(mMovieAddPanel,BorderLayout.CENTER);
	}
	
	static void createMListPanel() {
		mHeadLabel = new JLabel("등록된 영화 목록");
		mListPanel = new JPanel();
		mListPanel.setSize(500, 350);
		mListPanel.setLayout(new BorderLayout());
		
		mListPanel.add(mHeadLabel,BorderLayout.NORTH);	
		ArrayList<Movie1> movies = Movie1.findAll();
		String[] moviesList = new String[movies.size()];
		for(int i=0; i < movies.size(); i++) {
			moviesList[i] = movies.get(i).toString();
		}

		movieList = new JList<String>(moviesList);
		movieList.setFixedCellWidth(300);
		movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		movieList.addListSelectionListener(listener);
		movieList.setVisible(true);
		mMovieListScroll = new JScrollPane(movieList);
		mListPanel.add(mMovieListScroll, BorderLayout.CENTER);		
		
		contentPane.add(mListPanel,BorderLayout.CENTER);
	}
	
	static void createLoginPanel() { 
		loginPanel = new JPanel();
		loginPanel.setSize(500, 350);
		loginPanel.setLayout(new BorderLayout());
		
		loginHeadLabel = new JLabel("관리자 모드 전환");
		
		logPanel = new JPanel();	
		logPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
		loginGuideLabel = new JLabel("비밀번호");
		adminPwd = new JPasswordField(20);
		logPanel.add(loginGuideLabel);
		logPanel.add(adminPwd);
		JButton btn = new JButton("login");
		btn.addActionListener(listener);
		logPanel.add(btn);
		
		keylistener = new LoginKeyListener();
		adminPwd.addKeyListener(keylistener);
		
		loginPanel.add(loginHeadLabel,BorderLayout.NORTH);	
		loginPanel.add(logPanel, BorderLayout.CENTER);	
		loginPanel.setVisible(true);
		contentPane.add(loginPanel,BorderLayout.CENTER);
	}
	
	static void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar,BorderLayout.NORTH);  //c);
		
		for(int i =0; i < MAIN_MENU_TEXT.length; i++) {
			mainMenu[i] = new JMenu(MAIN_MENU_TEXT[i]);
			menuBar.add(mainMenu[i]);	
			if( i== 0) {
				for(int j =0; j < RESV_MENU_TEXT.length; j++) {
						uMenuItem[j] = new JMenuItem(RESV_MENU_TEXT[j]);
						if(mode)
							uMenuItem[j].setEnabled(false);
						uMenuItem[j].addActionListener(listener);
						mainMenu[i].add(uMenuItem[j]);
				}
			}
			else if(i == 1) {				
				for(int j =0; j < MAN_MENU_TEXT.length; j++) {
					mMenuItem[j] = new JMenuItem(MAN_MENU_TEXT[j]);
					mMenuItem[j].addActionListener(listener);
					if(j!=0 & !mode ) {
						mMenuItem[j].setEnabled(false);
					}
					if(mode) {
						mMenuItem[j].setEnabled(true);
						mMenuItem[0].setText("로그아웃");
						
					}
					mainMenu[i].add(mMenuItem[j]);
				}
			}
			else {
				xMenuItem = new JMenuItem(SYS_MENU_TEXT);
				xMenuItem.addActionListener(listener);
				mainMenu[i].add(xMenuItem);
			}

		}
	}
	
	static void createInitPanel(String message) {
		JLabel startLabel = new JLabel(message);

		initPanel = new JPanel();
		contentPane.add(initPanel,BorderLayout.CENTER); 
		
		initPanel.setSize(650, 350);
//		initPanel.setLayout(new BorderLayout());
		initPanel.setLayout(null);
		startLabel.setLocation(215,0);
		startLabel.setSize(500,350);
		initPanel.add(startLabel);
	}
	
	public StartSwing() {
		listener = new MenuActionListener();
		listenerForDrop = new MovieDropListener();
		reservationListForDrop = new ReservationDropListener();
		
		setTitle("영화 예매 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		createMenu();
		createInitPanel("영화예매 시스템에 오신 것을 환영합니다.");
		setSize(650,400);
		setResizable(false);
		contentPane.setVisible(true);
	}
	
	public static void main(String[] args) {
		//new startSwing();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartSwing frame = new StartSwing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
