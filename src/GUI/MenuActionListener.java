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
			
		if(!e.getValueIsAdjusting()) {	//�̰� ������ mouse ������, ���� ���� �ѹ��� ȣ��Ǽ� �� �ι� ȣ��
				StartSwing.movieId = StartSwing.movieList.getSelectedValue().substring(1, 11);
				try {
					selectedMovie = Movie1.findById(StartSwing.movieId);
					ArrayList<Reservation> reservations = Reservation.findByMovieId(StartSwing.movieId);
					Seats1 newSeat = new Seats1(reservations);
					StartSwing.headLabel.setText("������ ��ȭ: " + selectedMovie.getTitle()+"...�¼��� �����ϼ���.");
					StartSwing.movieName = selectedMovie.getTitle();
					StartSwing.movieListScroll.setVisible(false);
				}catch(IOException e1) {
					System.out.println("���� ����¿��� ������ ������ϴ�.");
				}catch(Exception e1) {
					System.out.printf(">> ���ſ� �����Ͽ����ϴ�: %s\n", e1.getMessage());
				}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch(cmd) {
			case "�����ϱ�": reserve(); break; //StartSwing.change("r");
			case "�α���": StartSwing.change("login"); break;
			case "�α׾ƿ�": StartSwing.change("logout"); break;
			case "login": loginAdmin(); break;
			case "��ȭ���": StartSwing.change("list"); break;
			case "��ȭ�߰�": StartSwing.change("add"); break;
			case "��ȭ����": StartSwing.change("drop"); break;
			case "����" : JOptionPane.showMessageDialog(null, "���α׷��� �����մϴ�.", "System Message", JOptionPane.INFORMATION_MESSAGE);
			 System.exit(0); break;
			case "�߰�Ȯ��": addNewMovie(true); break;
			case "�߰����": addNewMovie(false); break;
			case "Ȯ���ϱ�": StartSwing.change("rc"); break;  // reserveCheck(); break;
			case "����Ȯ��": reserveChecking();break;         //����Ȯ�� ȭ���� Ȯ�� ���� �ڵ鷯
			case "����ϱ�": StartSwing.change("cancel"); break;
			case "�������": cancelReservvation();break;                      //����Ȯ�� ȭ���� Ȯ�� ���� �ڵ鷯
			case "���Ÿ��": StartSwing.change("reservations"); break;
			case "���Ż���": StartSwing.change("reservationsDrop"); break;
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
			int choice = JOptionPane.showConfirmDialog(null, message+"�� �����ұ��?","Confirm", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION) {
				try {
					Reservation.cancel(reNumber);
					JOptionPane.showInternalMessageDialog(null,message + " ������ ��ҵǾ����ϴ�.","���� ����",JOptionPane.INFORMATION_MESSAGE);
					StartSwing.canTextField.setText("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			JOptionPane.showInternalMessageDialog(null,reNumber+" ���������� �����ϴ�.","���� ����",JOptionPane.INFORMATION_MESSAGE);
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
			JOptionPane.showInternalMessageDialog(null,message,"���� ����",JOptionPane.INFORMATION_MESSAGE);
			StartSwing.rcTextField.setText("");
		}
		else {
			JOptionPane.showInternalMessageDialog(null,reNumber+" ���������� �����ϴ�.","���� ����",JOptionPane.INFORMATION_MESSAGE);
			StartSwing.rcTextField.setText("");
		}
	}
	
	protected void addNewMovie(boolean option) {
		String mName, mGanre;
		mName = StartSwing.mMovieNameTextField.getText();
		mGanre = StartSwing.mMovieGenreTextField.getText();		
		Movie1 movie;
		
		if(!option) {
			JOptionPane.showInternalMessageDialog(null,"��ȭ �߰��� ����մϴ�.","��ȭ�߰�",JOptionPane.INFORMATION_MESSAGE);
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
				JOptionPane.showInternalMessageDialog(null,mName + "�� �߰��Ͽ����ϴ�.","��ȭ�߰�",JOptionPane.INFORMATION_MESSAGE);
				StartSwing.mMovieNameTextField.setText("");
				StartSwing.mMovieGenreTextField.setText("");
			}
		}else if(mName.equals("")) {
			JOptionPane.showInternalMessageDialog(null,"��ȭ ������ �����ּ���.","��ȭ�߰�",JOptionPane.INFORMATION_MESSAGE);
		}else if(mGanre.equals("")) {
			JOptionPane.showInternalMessageDialog(null,"��ȭ �帣�� �����ּ���.","��ȭ�߰�",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	protected void loginAdmin(){
		String pwd = new String(StartSwing.adminPwd.getPassword());
		if(pwd.equals( "abcd") ) {
			StartSwing.mMenuItem[0].setText("�α׾ƿ�");
			StartSwing.mode = true;
			StartSwing.change("login");
		}
		
	}	

}
