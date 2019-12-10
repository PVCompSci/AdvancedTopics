package geometrydash;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Stack;

import geometrydash.gfx.Assets;
import geometrydash.state.State;

public class TickThread implements Runnable {

	private Handler handler;
	private Thread t;
	private Game game;
	private Graphics g;
	private double backDx,floorDx;
	private float backX,floorX,attemptX,defaultAttemptX;
	
	public TickThread(Game g, Handler h)
	{
		
		game=g;
		handler=h;	
		backDx=-.1;
		floorDx=-11;
		defaultAttemptX=400;
		attemptX=defaultAttemptX;
		start();
		
	}
	public void init()
	{
		Assets.init();
		
	}
	private void tickAll() //updates everything including key manager and position of player and position of world
	{
		game.getKeyManager().tick();
		State.getState().tick();
		if(State.getState().getID()==2) //when the level is running, background is updated here
		{	
			backgroundtick();
			//backgroundRender();
		}	
	}
	public void backgroundtick()
	{
		if(!game.getGameState().getPlayer().isRespawning()) {
			backX+=backDx;
			floorX+=floorDx;
						
			if(attemptX>-1000)
				attemptX+=floorDx/2;
		}
		
	}
	public void backgroundRender()
	{	
		g=game.getBufferStrategy().getDrawGraphics();	
	    g.clearRect(0, 0, game.getHeight(),game.getHeight());
		 
		g.drawImage(Assets.background, (int)backX, 0, handler.getWidth(), handler.getHeight(),null);
		g.drawImage(Assets.background, (int)backX+handler.getWidth(), 0, handler.getWidth(), handler.getHeight(),null);
		if(backX+handler.getWidth()<=0)
			backX=0;
		
		if(handler.getGame().getGameState().getPlayer().isRespawning()) {
			attemptX=defaultAttemptX;
			floorX=0;
		}
		else if(!handler.getGame().getGameState().getPlayer().isRespawning()&&attemptX>-1000) {
			g.drawImage(Assets.attempts, (int)attemptX, 200,400,70,null);
			int temp=handler.getGame().getGameState().getPlayer().getAttemptCount();
			int dist=0;
			Stack<Integer> nums=new Stack<Integer>();

			while(temp>0) {
				nums.push(temp%10);
				temp/=10;
			}
			while(nums.size()>0) {
				g.drawImage(Assets.getNum(nums.pop()), (int)attemptX+450+dist, 200, 70,70,null);
				dist+=80;
			}
		}
		
		g.drawImage(Assets.floorBackground, (int)floorX, 1472-(int)handler.getGameCamera().getyOffset(), handler.getWidth()+10, 300,null);
		g.drawImage(Assets.floorBackground, (int)floorX+handler.getWidth()+10, 1472-(int)handler.getGameCamera().getyOffset(), handler.getWidth()+10, 300,null);
		if(floorX+handler.getWidth()<=0)
			floorX=0;
		
		g.drawImage(Assets.floorLine, 200, 1470-(int)handler.getGameCamera().getyOffset(), null);
		
		
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
				tickAll();
				delta--;		
			}
		}
		try {
			stop();
		} catch (InterruptedException e) {
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
