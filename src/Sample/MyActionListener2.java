package Sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MyActionListener2 implements ActionListener {

	String TextOk = "OK", TextKorea = "korea";
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn.getText().equals(TextOk)) {
			btn.setText(TextKorea);
		} else if(btn.getText().equals(TextKorea)){
			btn.setText(TextOk);
		} else if(btn.getText().equals("Cancel")) {
			System.exit(0);
		}
	}

}
