package geometrydash.entities.creatures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Stack;

import javax.sound.sampled.LineUnavailableException;

import geometrydash.Handler;
import geometrydash.gfx.Assets;
import geometrydash.tiles.Tile;

public class Player extends Creature{

	private float grav,power,rotSpeed,rotSpeedPortal,powerPortal,maxPortalSpeed;
	private boolean falling,boost,deathSound,levelComplete1;
	private int c,jumpCount,levelCompleteSize,levelCompleteScreenHeight;
	private Rectangle restart,end;
	public Player(Handler handler,float x, float y) {
		
		super(handler,x, y,Creature.DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		bounds.x=1;
		bounds.y=1;
		bounds.width=width-2;
		bounds.height=height-2;
		rot=0;
		rotSpeed=7;
		grav=1.7f;
		power=-21.5f;
		rotSpeedPortal=1f;
		maxPortalSpeed=10f;
		powerPortal=-.4f;
		falling=true;
		dx=speed;
		deathSound=false;
		attemptCount=1;
		c=0;
		jumpCount=0;
		levelCompleteSize=0;
		levelCompleteScreenHeight=-700;
		levelComplete1=true;
		restart=new Rectangle(374, 550, 125, 125);
		end=new Rectangle(785, 550, 125, 125);
	}
	
	public void tick() {
		
		if(!handler.getGame().getGameState().isLevelComplete()) {
			if(!portal)
				getInput();
			else
				getInputPortal();
		
			move();
		
			if(isRespawning()) //determines when to stop the audio
			{
				if(!deathSound)
					handler.getClip().stop();
			}
		}
			
	}
	public void getInput() {
		
		if(falling)
			dy+=grav;
		else
			dy=grav;
		
		if(handler.getKeyManager().space)
			if(!respawn)
				jump();
		

	}
	public void getInputPortal()
	{
		float gravP=-powerPortal;
		if(!boost)
			if(dy+gravP>=maxPortalSpeed)
				dy=maxPortalSpeed;
			else
				dy+=gravP;

		if(handler.getKeyManager().space)
		{
			if(!respawn)
				boost();
		}
		else
			boost=false;
		
		rot=(int) (dy*5);
			
	}
	
	public void move() {
		
		if(!portal)
			moveY();
		else
			movePortalY();
		moveX();
		
		handler.getWorld().resetTileCollisions();
	}
	
	public void moveY() {
	
		if(dy>0) {
			
			int ty=(int)(y+dy+1+bounds.height)/Tile.TILEHEIGHT;
			int x1=(int)(x+1)/Tile.TILEWIDTH;
			int x2=(int)(x+1+bounds.width)/Tile.TILEWIDTH;
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				
				y+=dy;
				falling=true;
			}
			else {
				
				y=ty*Tile.TILEHEIGHT-bounds.height-1-1;
				falling=false;
				rot%=360;
				
				if(rot>=45&&rot<135)
					rot=90;
				else if(rot>=135&&rot<225)
					rot=180;
				else if(rot>=225&&rot<315)
					rot=270;
				else
					rot=0;
			}
		}
		else if(dy<0) {
			
			int ty=(int)(y+dy+1)/Tile.TILEHEIGHT;
			int x1=(int)(x+1)/Tile.TILEWIDTH;
			int x2=((int)(x+1+bounds.width)/Tile.TILEWIDTH);
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				
				y+=dy;
				falling=true;
			}
			else {
				y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-1+1;
				respawn();
			}
		}
	}
	public void movePortalY()
	{
		if(dy>0) {

			int ty=(int)(y+dy+1+bounds.height)/Tile.TILEHEIGHT;
			int x1=(int)(x+1)/Tile.TILEWIDTH;
			int x2=(int)(x+1+bounds.width)/Tile.TILEWIDTH;

			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				y+=dy;
				falling=true;
			}
			else {
				y=ty*Tile.TILEHEIGHT-bounds.height-1-1;
				falling=false;
				rot=0;
				dy=1;
				
			}
		}
		else if(dy<0)
		{
			int ty=(int)(y+dy+1)/Tile.TILEHEIGHT;
			int x1=(int)(x+1)/Tile.TILEWIDTH;
			int x2=((int)(x+1+bounds.width)/Tile.TILEWIDTH);
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				y+=dy;
				falling=true;
			}
			else {
				y=ty*Tile.TILEHEIGHT+bounds.height+2;
				rot=0;
				dy=-1;
			}
			
		}	
	}
	
	public void boost()
	{
		boost=true;
		if(dy+powerPortal<=-maxPortalSpeed)
			dy=-maxPortalSpeed;
		else
			dy+=powerPortal;

		if(rot>=12)
			rot-=rotSpeedPortal+23;	
		else
			rot-=rotSpeedPortal+2;
			
	}
	
	public void jump() {
		if(!falling) {
			dy=power;
			jumpCount++;
		}
	}

	public void render(Graphics g) {
		
		Graphics2D g2=(Graphics2D)g;

		if(!handler.getGame().getGameState().isLevelComplete()) {
			if(!respawn) {
				dx=speed;
				bounds.setLocation((int)(x-handler.getGameCamera().getxOffset()+1), (int)(y-handler.getGameCamera().getyOffset())+1);
		
				if(falling) {
					if(!portal)
						rot+=rotSpeed;
					else
					{
						if(rot<=-15 && !boost)
							rot+=rotSpeedPortal+1;
						else	
							rot+=rotSpeedPortal;
					}	
			}
			
				g2.rotate(Math.toRadians(rot), (double)(x-handler.getGameCamera().getxOffset()+DEFAULT_WIDTH/2),(double)(y-handler.getGameCamera().getyOffset()+DEFAULT_HEIGHT/2));
				if(portal)
					g2.drawImage(Assets.player2,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width+18,height,null);
				else
					g2.drawImage(Assets.player1,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
			}
			else { //if its respawning
				dx=0;
				respawnCounter++;
				if(respawnCounter<=4)
				{
					c++;
					if(c<=1)
					{
						deathSound=true;
						handler.playSound("/audio/GeoDeathSound.wav");
					}
				}
				else if(respawnCounter>=60) {
					attemptCount++;
					respawn=false;
					deathSound=false;
					c=0;
					handler.getGameCamera().resetYOffset();
					x=spawnX;
					y=spawnY;
					handler.playSound("/audio/StereoM.wav");
					handler.getGame().startTimer();
				}
			}
		}
		else {
			
			if(x-handler.getGameCamera().getxOffset()<handler.getWidth()+400) {
				rot+=8;
				if(dx>2&&slowingDown)
					dx-=.3;
				else {
					slowingDown=false;
					dx+=.5;
				}
			
				dy+=.1;
				if(!slowingDown&&y-handler.getGameCamera().getyOffset()+40>handler.getHeight()/2-DEFAULT_HEIGHT/2)
					y-=dy;
				else if(!slowingDown&&y-handler.getGameCamera().getyOffset()-40<handler.getHeight()/2-DEFAULT_HEIGHT/2)
					y+=dy;
				else
					dy=0;
			
				x+=dx;
			
				g2.rotate(Math.toRadians(rot), (double)(x-handler.getGameCamera().getxOffset()+DEFAULT_WIDTH/2),(double)(y-handler.getGameCamera().getyOffset()+DEFAULT_HEIGHT/2));
				if(portal)
					g2.drawImage(Assets.player2,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
				else
					g2.drawImage(Assets.player1,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
			}
			else if(levelCompleteSize<105&&levelComplete1){
				if(levelCompleteSize<=100)
					g.drawImage(Assets.levelComplete, handler.getWidth()/2-levelCompleteSize*5, 200, levelCompleteSize*10,levelCompleteSize,null);
				levelCompleteSize+=5;
				if(levelCompleteSize>100) {
					levelComplete1=false;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(levelCompleteSize>0){
				levelCompleteSize-=2;
				g.drawImage(Assets.levelComplete, handler.getWidth()/2-levelCompleteSize*5, 200, levelCompleteSize*10,levelCompleteSize,null);
			}
			else if(levelCompleteScreenHeight<40) {
				levelCompleteScreenHeight+=5;
				g.drawImage(Assets.levelCompleteScreen,240,levelCompleteScreenHeight,800,666,null);
			}
			else {
				g.drawImage(Assets.levelCompleteScreen,240,40,800,666,null);
				drawNums(g,722,287 ,30,30,attemptCount);
				drawNums(g,655,335 ,30,30,jumpCount);
				drawNums(g,627,385 ,30,30,(int)handler.getGame().getTimer());
				if(handler.getMouseManager().isLeftClicked()&&restart.contains(handler.getMouseManager().getX(),handler.getMouseManager().getY()))
					restart();
				else if(handler.getMouseManager().isLeftClicked()&&end.contains(handler.getMouseManager().getX(),handler.getMouseManager().getY())) {
					System.exit(1000);
				}
			}
		}

	}
	
	public boolean isRespawning() {
		return respawn;
	}
	
	public void restart(){
		rot=0;
		respawn=true;
		portal=false;
		handler.getGame().resetTimer();
		handler.getGameCamera().resetYOffset();
		x=spawnX;
		y=spawnY;
		handler.getGame().getGameState().setLevelComplete(false);
		handler.playSound("/audio/StereoM.wav");
		attemptCount=0;
		jumpCount=0;
		levelComplete1=true;
		levelCompleteScreenHeight=-700;
	}
	
	public void drawNums(Graphics g,int xStart,int yStart,int width, int height,int num) {
		int dist=0;
		Stack<Integer> nums=new Stack<Integer>();

		while(num>0) {
			nums.push(num%10);
			num/=10;
		}
		while(nums.size()>0) {
			g.drawImage(Assets.getEndNum(nums.pop()), xStart+dist, yStart, width,height,null);
			dist+=width;
		}
	}
	
	
}
