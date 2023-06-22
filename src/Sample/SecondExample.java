package Sample;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SecondExample extends JFrame {

	public SecondExample() {
		setTitle("Second Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridLayout(4, 2));

		Container cp = getContentPane();

		cp.add(new JLabel(" 이름"));
		cp.add(new JTextField());
		cp.add(new JLabel(" 학번"));
		cp.add(new JTextField(""));
		cp.add(new JLabel(" 학과"));
		cp.add(new JTextField(""));
//		cp.add(new JButton("OK"));
		JButton jbtn = new JButton("OK");
		ActionListener listener = new MyActionListener2();
		jbtn.addActionListener(listener);
		cp.add(jbtn);
		
		jbtn = new JButton("Cancel");
		jbtn.addActionListener(listener);
		cp.add(jbtn);
		
		setSize(400, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new SecondExample();
	}

}

//class MyActionListener implements ActionListener {
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		JButton btn = (JButton) e.getSource();
//		if(btn.getText().equals("OK")) {
//			btn.setText("ulsan");
//		} else if(btn.getText().equals("ulsan")){
//			btn.setText("OK");
//		} else if(btn.getText().equals("Cancel")) {
//			System.exit(0);
//		}
//
//	}
//}
