package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Expert.Reservation;

public class MenuActionListener implements ActionListener, ListSelectionListener {
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Movie1 selectedMovie; 
			
		if(!e.getValueIsAdjusting()) {	//이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
				StartSwing.movieId = StartSwing.movieList.getSelectedValue().substring(1, 11);
				try {
					selectedMovie = Movie1.findById(StartSwing.movieId);
					ArrayList<Reservation> reservations = Reservation.findByMovieId(StartSwing.movieId);
					Seats1 newSeat = new Seats1(reservations);
					StartSwing.headLabel.setText("선택한 영화: " + selectedMovie.getTitle()+"...좌석을 선택하세요.");
					StartSwing.movieName = selectedMovie.getTitle();
					StartSwing.movieListScroll.setVisible(false);
				}catch(IOException e1) {
					System.out.println("파일 입출력에서 문제가 생겼습니다.");
				}catch(Exception e1) {
					System.out.printf(">> 예매에 실패하였습니다: %s\n", e1.getMessage());
				}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch(cmd) {
			case "예매하기": reserve(); break; //StartSwing.change("r");
			case "로그인": StartSwing.change("login"); break;
			case "로그아웃": StartSwing.change("logout"); break;
			case "login": loginAdmin(); break;
			case "영화목록": StartSwing.change("list"); break;
			case "영화추가": StartSwing.change("add"); break;
			case "영화삭제": StartSwing.change("drop"); break;
			case "종료" : JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.", "System Message", JOptionPane.INFORMATION_MESSAGE);
			 System.exit(0); break;
			case "추가확인": addNewMovie(true); break;
			case "추가취소": addNewMovie(false); break;
			case "확인하기": StartSwing.change("rc"); break;  // reserveCheck(); break;
			case "예약확인": reserveChecking();break;         //예약확인 화면의 확인 버턴 핸들러
			case "취소하기": StartSwing.change("cancel"); break;
			case "예약삭제": cancelReservvation();break;                      //예약확인 화면의 확인 버턴 핸들러
			case "예매목록": StartSwing.change("reservations"); break;
			case "예매삭제": StartSwing.change("reservationsDrop"); break;
		}
	}
	
	private void reserve() {
		StartSwing.change("r");
	}
	
	private void cancelReservvation(){
		Reservation findReservation = null;
		String reNumber = StartSwing.canTextField.getText();
		try {
			findReservation = Reservation.findById(reNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(findReservation != null) {
			String message = findReservation.toString(); 
			int choice = JOptionPane.showConfirmDialog(null, message+"을 삭제할까요?","Confirm", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION) {
				try {
					Reservation.cancel(reNumber);
					JOptionPane.showInternalMessageDialog(null,message + " 예약이 취소되었습니다.","예약 삭제",JOptionPane.INFORMATION_MESSAGE);
					StartSwing.canTextField.setText("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			JOptionPane.showInternalMessageDialog(null,reNumber+" 예약정보가 없습니다.","예약 정보",JOptionPane.INFORMATION_MESSAGE);
			StartSwing.canTextField.setText("");
		}

	}
	
	private void reserveChecking() {
		Reservation findReservation = null;
		String reNumber = StartSwing.rcTextField.getText();
		try {
			findReservation = Reservation.findById(reNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(findReservation != null) {
			String message = findReservation.toString(); 
			JOptionPane.showInternalMessageDialog(null,message,"예약 정보",JOptionPane.INFORMATION_MESSAGE);
			StartSwing.rcTextField.setText("");
		}
		else {
			JOptionPane.showInternalMessageDialog(null,reNumber+" 예약정보가 없습니다.","예약 정보",JOptionPane.INFORMATION_MESSAGE);
			StartSwing.rcTextField.setText("");
		}
	}
	
	protected void addNewMovie(boolean option) {
		String mName, mGanre;
		mName = StartSwing.mMovieNameTextField.getText();
		mGanre = StartSwing.mMovieGenreTextField.getText();		
		Movie1 movie;
		
		if(!option) {
			JOptionPane.showInternalMessageDialog(null,"영화 추가를 취소합니다.","영화추가",JOptionPane.INFORMATION_MESSAGE);
			StartSwing.mMovieNameTextField.setText("");
			StartSwing.mMovieGenreTextField.setText("");
			return;
		}else if(!mName.equals("") && !mGanre.equals("")) {
			if(option) {
				movie = new Movie1(mName, mGanre);
				try {
					movie.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showInternalMessageDialog(null,mName + "를 추가하였습니다.","영화추가",JOptionPane.INFORMATION_MESSAGE);
				StartSwing.mMovieNameTextField.setText("");
				StartSwing.mMovieGenreTextField.setText("");
			}
		}else if(mName.equals("")) {
			JOptionPane.showInternalMessageDialog(null,"영화 제목을 적어주세요.","영화추가",JOptionPane.INFORMATION_MESSAGE);
		}else if(mGanre.equals("")) {
			JOptionPane.showInternalMessageDialog(null,"영화 장르를 적어주세요.","영화추가",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	protected void loginAdmin(){
		String pwd = new String(StartSwing.adminPwd.getPassword());
		if(pwd.equals( "abcd") ) {
			StartSwing.mMenuItem[0].setText("로그아웃");
			StartSwing.mode = true;
			StartSwing.change("login");
		}
		
	}	

}
