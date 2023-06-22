package GUI;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Expert.Reservation;

public class MovieDropListener implements ListSelectionListener {

	public MovieDropListener() {
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Movie1 selectedMovie; 
		
		if(!e.getValueIsAdjusting()) {	//이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
				StartSwing.movieId = StartSwing.movieListForDrop.getSelectedValue().substring(1, 11);
				try {
					selectedMovie = Movie1.findById(StartSwing.movieId);
					String message = selectedMovie.toString(); 
					int choice = JOptionPane.showConfirmDialog(null, message+"을 삭제할까요?","Confirm", JOptionPane.YES_NO_OPTION);
					if(choice == JOptionPane.YES_OPTION) {
						Movie1.delete(StartSwing.movieId);
						try {
							ArrayList<Reservation> reservations = Reservation.findByMovieId(selectedMovie.getId());
							for(Reservation temp: reservations) {
								Reservation.cancel(String.valueOf(temp.getId()));
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					StartSwing.change("drop");
//					ArrayList<Reservation> reservations = Reservation.findByMovieId(StartSwing.movieId);
//					StartSwing.movieName = selectedMovie.getTitle();
//					StartSwing.movieListScroll.setVisible(false);
				}catch(IOException e1) {
					System.out.println("파일 입출력에서 문제가 생겼습니다.");
				}catch(Exception e1) {
					System.out.printf(">> 예매에 실패하였습니다: %s\n", e1.getMessage());
				}

		}


	}

}
