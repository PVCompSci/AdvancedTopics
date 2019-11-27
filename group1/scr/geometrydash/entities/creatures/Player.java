package geometrydash.entities.creatures;

import java.awt.Graphics;
import java.awt.Graphics2D;

import geometrydash.Handler;
import geometrydash.gfx.Assets;
import geometrydash.tiles.Tile;

public class Player extends Creature{

	private float grav,power,rotSpeed;
	private boolean falling;

	public Player(Handler handler,float x, float y) {
		
		super(handler,x, y,Creature.DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		bounds.x=1;
		bounds.y=1;
		bounds.width=width-2;
		bounds.height=height-2;
		rot=0;
		rotSpeed=7;
		grav=1.7f;
		power=-22f;
		falling=true;
		dx=speed;
		deathCount=0;
	}
	
	public void tick() {
		
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
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
	
	public void move() {
		
		moveX();
		moveY();
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
			rot+=rotSpeed;
			}
			
			g2.rotate(Math.toRadians(rot), (double)(x-handler.getGameCamera().getxOffset()+DEFAULT_WIDTH/2),(double)(y-handler.getGameCamera().getyOffset()+DEFAULT_HEIGHT/2));
			g2.drawImage(Assets.player1,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
		else {
			dx=0;
			respawnCounter++;
			if(respawnCounter>=60) {
				respawn=false;
				x=spawnX;
				y=spawnY;
			}
		}
	}
	
	public boolean isRespawning() {
		return respawn;
	}
	
}
