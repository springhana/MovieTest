package GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginKeyListener implements KeyListener {
	
	MenuActionListener listener;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		listener = new MenuActionListener();
		switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER : listener.loginAdmin(); break; 
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
