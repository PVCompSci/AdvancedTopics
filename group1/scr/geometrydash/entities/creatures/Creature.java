package geometrydash.entities.creatures;

import java.awt.Point;

import geometrydash.Game;
import geometrydash.Handler;
import geometrydash.entities.Entity;
import geometrydash.tiles.Tile;

public abstract class Creature extends Entity{
	
	public static final int DEFAULT_HEALTH=10;
	public static final float DEFAULT_SPEED=10f;
	public static final int DEFAULT_WIDTH=64,DEFAULT_HEIGHT=64;
	
	protected int health;
	protected float speed;
	
	protected float dx,dy;

	public Creature(Handler handler,float x,float y,int width, int height) {
		super(handler,x,y,width,height);
		health=DEFAULT_HEALTH;
		speed=DEFAULT_SPEED;
		dx=0;
		dy=0;
	}
	
	public void move() {
		moveX();
		moveY();
	}
	
	public void moveX() {
		if(dx>0) {
			
			int tx=(int)(x+dx+bounds.x+bounds.width)/Tile.TILEWIDTH;
			int y1=(int)(y+bounds.y)/Tile.TILEHEIGHT;
			int y2=(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT;
			
			if(!collisionWithTile(tx,y1)&&!collisionWithTile(tx,y2)) {
				x+=dx;
			}
			else {
				x=tx*Tile.TILEWIDTH-bounds.width-bounds.x-1;
			}
		}
		else if(dx<0) {
			int tx=(int)(x+dx+bounds.x)/Tile.TILEWIDTH;
			int y1=(int)(y+bounds.y)/Tile.TILEHEIGHT;
			int y2=(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT;
			if(!collisionWithTile(tx,y1)&&!collisionWithTile(tx,y2)) {
				x+=dx;
			}
			else {
				x=tx*Tile.TILEWIDTH+Tile.TILEWIDTH-bounds.x+1;
			}
		}
	}
	
	public void moveY() {
		if(dy>0) {
			int ty=(int)(y+dy+bounds.y+bounds.height)/Tile.TILEHEIGHT;
			int x1=(int)(x+bounds.x)/Tile.TILEWIDTH;
			int x2=(int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH;
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				
				y+=dy;
			}
			else {
				y=ty*Tile.TILEHEIGHT-bounds.height-bounds.y-1;
			}
		}
		else if(dy<0) {
			
			int ty=(int)(y+dy+bounds.y)/Tile.TILEHEIGHT;
			int x1=(int)(x+bounds.x)/Tile.TILEWIDTH;
			int x2=((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH);
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				y+=dy;
			}
			else {
				y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-bounds.y+1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x,int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	protected boolean isSmallTile(int x,int y) {
		return handler.getWorld().getTile(x, y).isSmallTile();
	}
	
	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
