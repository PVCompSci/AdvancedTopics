package geometrydash;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import geometrydash.gfx.Assets;
import geometrydash.state.State;

public class BackgroundAesthetics implements Runnable {

	private Handler handler;
	private Thread t;
	private Game game;
	private Graphics g;
	private double backDx,floorDx;
	private float backX,floorX;
	
	public BackgroundAesthetics(Game g, Handler h)
	{
		
		game=g;
		handler=h;	
		backDx=-.1;
		floorDx=-11;
		start();
		
	}
	public void init()
	{
		Assets.init();
		
	}
	public void tick()
	{
		if(!game.getGameState().getPlayer().isRespawning()) {
			backX+=backDx;
			floorX+=floorDx;
		}
		
	}
	public void render()
	{	
		g=game.getBufferStrategy().getDrawGraphics();	
	    g.clearRect(0, 0, game.getHeight(),game.getHeight());
		 
		g.drawImage(Assets.background, (int)backX, 0, handler.getWidth(), handler.getHeight(),null);
		g.drawImage(Assets.background, (int)backX+handler.getWidth(), 0, handler.getWidth(), handler.getHeight(),null);
		if(backX+handler.getWidth()<=0)
			backX=0;
		
		g.drawImage(Assets.floorBackground, (int)floorX, 832-(int)handler.getGameCamera().getyOffset(), handler.getWidth()+10, 300,null);
		g.drawImage(Assets.floorBackground, (int)floorX+handler.getWidth()+10, 832-(int)handler.getGameCamera().getyOffset(), handler.getWidth()+10, 300,null);
		if(floorX+handler.getWidth()<=0)
			floorX=0;
		
		g.setColor(Color.white);
		float opacity = 0.5f;
		Graphics2D g2= (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawLine(200, 832-(int)handler.getGameCamera().getyOffset(), 1080, 832-(int)handler.getGameCamera().getyOffset());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		
	}
	@Override
	public void run() {	
		init();
		int fps=60;
		double timePerTick=1000000000/fps;
		double delta=0;
		long now;
		long lastTime=System.nanoTime();
		
		while(game.getRunning()) {
			
			now=System.nanoTime();
			delta+=(now-lastTime)/timePerTick;
			lastTime=now;
			
			if(delta>=1) {
				if(State.getState().getID()==2)
				{
					tick();
					render();
				}	
				delta--;
			}
		}
		
		
		try {
			stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	public Thread getThread()
	{
		return t;
	}
	public void start()
	{
		t= new Thread(this);
		t.start();

	}
	public void stop() throws InterruptedException 
	{
		t.join();
	}

}
