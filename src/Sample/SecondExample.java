package Sample;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SecondExample extends JFrame {

	public SecondExample() {
		setTitle("Second Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(4,2,5,5));
		
		Container cp = getContentPane();
		
		cp.add(new JLabel(" 이름"));
		cp.add(new JTextField());
		cp.add(new JLabel(" 학번"));
		cp.add(new JTextField(""));
		cp.add(new JLabel(" 학과"));
		cp.add(new JTextField(""));
		cp.add(new JButton("OK"));
		cp.add(new JButton("Cancel"));
		
		setSize(400,300);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SecondExample();
	}

}
