package GUI;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Expert.Reservation;

public class ReservationDropListener implements ListSelectionListener {

	public ReservationDropListener() {
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Reservation selectedReservation; 
		
		if(!e.getValueIsAdjusting()) {	//이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
				StartSwing.resevationId = StartSwing.rservationDrop.getSelectedValue().substring(5, 19).trim();
				System.out.println(	StartSwing.resevationId);
				try {
					selectedReservation = Reservation.findById(StartSwing.resevationId);
					String message = selectedReservation.toString(); 
					int choice = JOptionPane.showConfirmDialog(null, message + "을 삭제할까요?","Confirm", JOptionPane.YES_NO_OPTION);

					if(choice == JOptionPane.YES_OPTION) {
						Reservation.cancel(StartSwing.resevationId);
					}
					StartSwing.change("reservationsDrop");
				}catch(IOException e1) {
					System.out.println("파일 입출력에서 문제가 생겼습니다.");
				}catch(Exception e1) {
					System.out.printf(">> 예매 취소에 실패하였습니다: %s\n", e1.getMessage());
				}

		}
	}

}
