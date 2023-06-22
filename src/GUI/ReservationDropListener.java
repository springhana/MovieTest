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
		
		if(!e.getValueIsAdjusting()) {	//�̰� ������ mouse ������, ���� ���� �ѹ��� ȣ��Ǽ� �� �ι� ȣ��
				StartSwing.resevationId = StartSwing.rservationDrop.getSelectedValue().substring(5, 19).trim();
				System.out.println(	StartSwing.resevationId);
				try {
					selectedReservation = Reservation.findById(StartSwing.resevationId);
					String message = selectedReservation.toString(); 
					int choice = JOptionPane.showConfirmDialog(null, message + "�� �����ұ��?","Confirm", JOptionPane.YES_NO_OPTION);

					if(choice == JOptionPane.YES_OPTION) {
						Reservation.cancel(StartSwing.resevationId);
					}
					StartSwing.change("reservationsDrop");
				}catch(IOException e1) {
					System.out.println("���� ����¿��� ������ ������ϴ�.");
				}catch(Exception e1) {
					System.out.printf(">> ���� ��ҿ� �����Ͽ����ϴ�: %s\n", e1.getMessage());
				}

		}
	}

}
