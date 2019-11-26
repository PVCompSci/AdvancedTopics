package geometrydash.state;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import geometrydash.Handler;
import geometrydash.gfx.Assets;

public class MenuState extends State {

	private Rectangle playButton;
	public MenuState(Handler handler) {
		super(handler);
		playButton= new Rectangle(550,250,180,180);
		
	
	}
	
	public void tick() {
		if(handler.getMouseManager().isLeftClicked() && playButton.contains(handler.getMouseManager().getX(),handler.getMouseManager().getY()))
			handler.getGame().setGameState();
		
	}

	public void render(Graphics g) {
		
		Graphics2D g2=(Graphics2D) g;
		g2.drawImage(Assets.menu1, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(), null);
		
			
			
	}



}
