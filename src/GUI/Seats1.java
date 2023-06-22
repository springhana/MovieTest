package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Expert.Reservation;

public class Seats1 implements ActionListener {
	
	public static final int MAX_ROW = 5;
	public static final int MAX_COL = 9;
	String selectedSeatNumber;
	
	public Seats1(ArrayList<Reservation> reservations) {
		String[] seatNumber = {" ","S","C","R","E","E","N"," "," ",
								"A-1","A-2","A-3","A-4","A-5","A-6","A-7","A-8","A-9",
								"B-1","B-2","B-3","B-4","B-5","B-6","B-7","B-8","B-9",
								"C-1","C-2","C-3","C-4","C-5","C-6","C-7","C-8","C-9",
								"D-1","D-2","D-3","D-4","D-5","D-6","D-7","D-8","D-9",
								"E-1","E-2","E-3","E-4","E-5","E-6","E-7","E-8","E-9"};
		JButton btn;
		
		StartSwing.seatPanel = new JPanel();
		StartSwing.seatPanel.setLayout(new GridLayout(6,9,10,10));
		
		for(int i=0; i < seatNumber.length; i++) {
			btn = new JButton(seatNumber[i]);
			btn.addActionListener(this);
			if(i < 9) {
				btn.setBackground(new Color(80,80,80));
				btn.setEnabled(false);
			}else {
				for(int j=0; j < reservations.size(); j++)	{
					Reservation r = reservations.get(j);
					String seatName = r.getSeatName();
					if( i == mark(seatName)) {
						System.out.println(i);
						btn.setText("N/A");
						btn.setBackground(new Color(255,0,0));
						btn.setEnabled(false);
					}
				}
			}
			StartSwing.seatPanel.add(btn);
		}
		StartSwing.reservePanel.add(StartSwing.seatPanel, BorderLayout.CENTER);		
		
	}
	
	public int mark(String seatName) {
		char[] temp = seatName.toCharArray();
		int row = temp[0] -'A';
		int col = temp[2] - '1';
		return (row+1)*MAX_COL + col;

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		selectedSeatNumber = e.getActionCommand();
		Reservation r = new Reservation(Long.parseLong(StartSwing.movieId), StartSwing.movieName, selectedSeatNumber);
		try {
			r.save();
		} catch (IOException e1) {
			System.out.println("예약을 저장에 실패하였습니다.");
		}
		String message = StartSwing.movieName+"(" + selectedSeatNumber + ")의 예약번호: " + r.getId();
		JOptionPane.showInternalMessageDialog(null,message,"예약 정보",JOptionPane.INFORMATION_MESSAGE);
		StartSwing.seatPanel.setVisible(false);
		StartSwing.movieListScroll.setVisible(true);
	}

}
