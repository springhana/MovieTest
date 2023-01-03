package Sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MyFrame extends JFrame {

	public MyFrame() {
		Container conPane = getContentPane();
		
		setTitle("Firts GUI Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		conPane.setBackground(Color.orange);
		conPane.setLayout(new BorderLayout());

		JButton btn1 = new JButton("OK");
		JButton btn2 = new JButton("Cancel");
		JButton btn3 = new JButton("Ignore");
		conPane.add(btn1,BorderLayout.NORTH);
		conPane.add(btn2,BorderLayout.CENTER);
		conPane.add(btn3,BorderLayout.SOUTH);
		
		setSize(400,300);
		setVisible(true);
	}
	public static void main(String[] args) {
		MyFrame mf = new MyFrame();
	}

}
