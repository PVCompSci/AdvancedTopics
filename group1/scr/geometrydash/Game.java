package geometrydash;
import java.awt.Graphics;
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
	private Thread thread1;

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
	private TickThread update;
	
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
		
		
		//////enables multithreading by calling TickThread constructor
		update= new TickThread(this,handler); //thread starts	
		State.setState(menuState); //starts with menu
		
		
	}
	
    private void tick() { //ticking is now done all on another thread
		
		keyManager.tick();
		if(State.getState()!=null)
			State.getState().tick();	}
	
	private void renderAll() { //
		
		bs=display.getCanvas().getBufferStrategy();
		if(bs==null) {
			
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g=bs.getDrawGraphics();
      //  g.clearRect(0, 0, width, height); //clears whatever was previously painted

		
		//Drawing Area
//	
		if(State.getState().getID()==2) //renders background
			update.backgroundRender();
		if(State.getState()!=null) //renders everything else
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
				//tick();
				renderAll();
				delta--;
			}
			
			if(State.getState().getID()==2&&timerT>=1000000000&&timerR) {
				timer+=1;
				timerT=0; 
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
	public TickThread getUpdateThread()
	{
		return update;
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
			update.stop();
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
	
	public void startTimer() {
		timerR=true;
	}
	
	public double getTimer() {
		return timer;
	}
	

}
