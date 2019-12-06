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
	private Thread thread1,thread2;

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
	private BackgroundAesthetics backgroundVisuals;
	
	public Game(String title, int width, int height) {
		
		this.width=width;
		this.height=height;
		this.title=title;
		keyManager=new KeyManager();
		mouseManager= new MouseManager();
		
	}

	public void init() {
		
		display=new Display(title,width,height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		

		Assets.init();
		
		gameCamera=new GameCamera(this,0,0);
		handler=new Handler(this);
		
		timerR=true;
		timer=0;

		gameState=new GameState(handler,2);
		menuState=new MenuState(handler,1);
		backgroundVisuals= new BackgroundAesthetics(this,handler); //thread starts
		
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
       // g.clearRect(0, 0, width, height);

		
		//Drawing Area
	
		if(State.getState().getID()==2)//renders background  on single thread, for now multi threading
			backgroundVisuals.render();
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
	public Handler getHandler()
	{
		return handler;
	}
	public BackgroundAesthetics getBackground()
	{
		return backgroundVisuals;
	}
	public Graphics getGraphics()
	{
		return g;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public BufferStrategy getBufferStrategy()
	{
		return bs;
	}
	public boolean getRunning()
	{
		return running;
	}
	public synchronized void start() {
		
		if(running)
			return;
		
		running=true;
		thread1=new Thread(this);
		thread1.start();

	}
	public synchronized void stop() {
		
		if(!running)
			return;
		
		running=false;
		try {
			thread1.join();
			backgroundVisuals.stop();
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
