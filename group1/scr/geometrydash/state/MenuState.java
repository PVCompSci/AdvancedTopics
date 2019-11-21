package geometrydash.state;
import java.awt.Graphics;

import geometrydash.Handler;

public class MenuState extends State{

	public MenuState(Handler handler) {
		super(handler);
	}
	
	public void tick() {
		if(handler.getKeyManager().one) {
			handler.getGame().setGameState();
		}
	}

	public void render(Graphics g) {
		
		g.drawString("Press 1 to Start: ",handler.getGame().getWidth()/2,handler.getGame().getHeight()/2);
	}

}
