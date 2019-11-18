package geometrydash.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import geometrydash.Game;
import geometrydash.Handler;
import geometrydash.gfx.Assets;
import geometrydash.tiles.Tile;

public class Player extends Creature{

	private float grav,power,rotSpeed,rot;
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
		power=-21f;
		falling=true;
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
		dx=0;
		
		if(handler.getKeyManager().up)
			dy=-speed;
		if(handler.getKeyManager().down)
			dy=speed;
		if(handler.getKeyManager().left)
			dx=-speed;
		if(handler.getKeyManager().right)
			dx=speed;
		if(handler.getKeyManager().space)
			jump();
	}
	
	public void move() {
		
		moveX();
		moveY();
	}
	
	public void moveY() {
	
		if(dy>0) {
			
			int ty=(int)(y+dy+bounds.y+bounds.height)/Tile.TILEHEIGHT;
			int x1=(int)(x+bounds.x)/Tile.TILEWIDTH;
			int x2=(int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH;
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				
				y+=dy;
				falling=true;
			}
			else {
				
				y=ty*Tile.TILEHEIGHT-bounds.height-bounds.y-1;
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
			
			int ty=(int)(y+dy+bounds.y)/Tile.TILEHEIGHT;
			int x1=(int)(x+bounds.x)/Tile.TILEWIDTH;
			int x2=((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH);
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				
				y+=dy;
				falling=true;
			}
			else
				y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-bounds.y+1;
		}
	}
	
	public void jump() {
		if(!falling) {
			dy=power;
		}
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2=(Graphics2D)g;
		if(falling) {
			rot+=rotSpeed;
		}

		g2.rotate(Math.toRadians(rot), (double)(x-handler.getGameCamera().getxOffset()+DEFAULT_WIDTH/2),(double)(y-handler.getGameCamera().getyOffset()+DEFAULT_HEIGHT/2));
		g.drawImage(Assets.player1,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
	}

}
