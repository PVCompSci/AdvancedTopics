package geometrydash.state;
import java.awt.Graphics;

import geometrydash.Handler;

public abstract class State {

	
	private static State currentState=null;
	
	public static void setState(State state) {
		
		currentState=state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	protected Handler handler;
	private int id;
	
	public State(Handler handler, int id) {
		this.handler=handler;
		this.id=id;
	}
	public abstract int getID();
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
