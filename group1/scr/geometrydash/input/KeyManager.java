package geometrydash.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up,down,left,right,space,one;
	
	public KeyManager() {
		keys=new boolean[256];
	}
	
	public void tick() {
		up=keys[KeyEvent.VK_W];
		down=keys[KeyEvent.VK_S];
		left=keys[KeyEvent.VK_A];
		right=keys[KeyEvent.VK_D];
		space=keys[KeyEvent.VK_SPACE];
		one=keys[KeyEvent.VK_1];

	}
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		
		keys[e.getKeyCode()]=true;
	}

	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()]=false;
	}

}
