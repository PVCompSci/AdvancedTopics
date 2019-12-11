package geometrydash.entities.creatures;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.sampled.LineUnavailableException;

import geometrydash.Handler;
import geometrydash.gfx.Assets;
import geometrydash.tiles.Tile;

public class Player extends Creature{

	private float grav,power,rotSpeed,rotSpeedPortal,powerPortal,maxPortalSpeed;
	private boolean falling,boost,deathSound;

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
		powerPortal=-.5f;
		falling=true;
		dx=speed;
		deathSound=false;
		attemptCount=1;
	}
	
	public void tick() {
		
		if(!portal)
			getInput();
		else
			getInputPortal();
		
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		if(isRespawning()) //determines when to stop the audio
		{
			if(!deathSound)
				handler.getClip().stop();
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
//		if(rot<=-50)
//			rot=-50;	
//		if(rot>=50)
//			rot=50;
			
	}
	
	public void move() {
		
		moveX();
		if(!portal)
			moveY();
		else
			movePortalY();
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
		}
	}

	public void render(Graphics g) { 		
		if(!respawn) {
			dx=speed;
			bounds.setLocation((int)(x-handler.getGameCamera().getxOffset()+1), (int)(y-handler.getGameCamera().getyOffset())+1);
		
			Graphics2D g2=(Graphics2D)g;
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
				g2.drawImage(Assets.player2,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
			else
				g2.drawImage(Assets.player1,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
		else { //if its respawning
			dx=0;
			respawnCounter++;
			if(respawnCounter<=1)
			{
				deathSound=true;
				handler.playSound("/audio/GeoDeathSound.wav");
			}
			else if(respawnCounter>=60) {
				attemptCount++;

				respawn=false;
				deathSound=false;
				
				handler.getGame().resetTimer();
				handler.getGameCamera().resetYOffset();
				x=spawnX;
				y=spawnY;
				handler.playSound("/audio/StereoM.wav");
				
			}
		}
	}
	
	public boolean isRespawning() {
		return respawn;
	}
	
	
	
}
