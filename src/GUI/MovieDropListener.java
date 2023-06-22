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
		
		if(!e.getValueIsAdjusting()) {	//�̰� ������ mouse ������, ���� ���� �ѹ��� ȣ��Ǽ� �� �ι� ȣ��
				StartSwing.movieId = StartSwing.movieListForDrop.getSelectedValue().substring(1, 11);
				try {
					selectedMovie = Movie1.findById(StartSwing.movieId);
					String message = selectedMovie.toString(); 
					int choice = JOptionPane.showConfirmDialog(null, message+"�� �����ұ��?","Confirm", JOptionPane.YES_NO_OPTION);
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
					System.out.println("���� ����¿��� ������ ������ϴ�.");
				}catch(Exception e1) {
					System.out.printf(">> ���ſ� �����Ͽ����ϴ�: %s\n", e1.getMessage());
				}

		}


	}

}
