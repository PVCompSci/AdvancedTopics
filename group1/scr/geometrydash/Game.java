package geometrydash;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import geometrydash.display.Display;
import geometrydash.gfx.Assets;
import geometrydash.gfx.GameCamera;
import geometrydash.input.KeyManager;
import geometrydash.input.MouseManager;
import geometrydash.state.GameState;
import geometrydash.state.MenuState;
import geometrydash.state.State;

public class Game implements Runnable{

	private Display display;
	private Thread thread;

	private int width,height;
	private double timer;
	private String title;
	private boolean running=false;
	private boolean timerR;

	private BufferStrategy bs;
	private Graphics g;
			
	public State gameState,menuState;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private GameCamera gameCamera;
	
	private Handler handler;
	
	public Game(String title, int width, int height) {
		
		this.width=width;
		this.height=height;
		this.title=title;
		keyManager=new KeyManager();
		mouseManager= new MouseManager();
	}

	private void init() {
		
		display=new Display(title,width,height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		Assets.init();
		
		gameCamera=new GameCamera(this,0,0);
		handler=new Handler(this);
		
		gameState=new GameState(handler);
		menuState=new MenuState(handler);
	
		timerR=true;
		timer=0;
		
		State.setState(menuState);
		
	}
	
	private void tick() {
		
		keyManager.tick();
		
		if(State.getState()!=null)
			State.getState().tick();
		
	}
	
	private void render() {
		
		bs=display.getCanvas().getBufferStrategy();
		
		if(bs==null) {
			
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g=bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		//Drawing Area
		
		if(State.getState()!=null)
			State.getState().render(g);
		
		//Finished Drawing
		
		bs.show();
		g.dispose();
		
	}
	
	public void run() {
		
		init();
		
		int fps=60;
		double timePerTick=1000000000/fps;
		double delta=0;
		long now;
		long lastTime=System.nanoTime();
		long timerT=0;

		
		while(running) {
			
			now=System.nanoTime();
			delta+=(now-lastTime)/timePerTick;
			timerT+=now-lastTime;
			lastTime=now;
			
			if(delta>=1) {
				tick();
				render();
				delta--;
			}
				
			if(timerT>=100000000&&timerR) {
				timer+=0.1;
				timerT=0; 
				System.out.println(timer);
			}
		}
		
		stop();
	}
	
	public void setGameState() {
		State.setState(gameState);
	}
	
	public void setMenuState() {
		State.setState(menuState);
	}
	
	public GameState getGameState() {
		return (GameState)gameState;
	}
	
	public KeyManager getKeyManager() {
		
		return keyManager;
	}
	public MouseManager getMouseManager()
	{
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	public Display getDisplay()
	{
		return display;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public synchronized void start() {
		
		if(running)
			return;
		
		running=true;
		thread=new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		
		if(!running)
			return;
		
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void resetTimer() {
		timer=0;
		timerR=true;
	}
	
	public void stopTimer() {
		timerR=false;
	}
	
	public double getTimer() {
		return timer;
	}
	

}
