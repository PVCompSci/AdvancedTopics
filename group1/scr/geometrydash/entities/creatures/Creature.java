package geometrydash.entities.creatures;

import geometrydash.Handler;
import geometrydash.entities.Entity;
import geometrydash.tiles.Tile;
import geometrydash.worlds.World;

public abstract class Creature extends Entity{
	
	public static final int DEFAULT_HEALTH=10;
	public static final float DEFAULT_SPEED=11.1f;
	public static final int DEFAULT_WIDTH=64,DEFAULT_HEIGHT=64;
	
	protected int health,rot,respawnCounter;
	protected float speed;
	protected boolean respawn;
	protected int deathCount,portalCount;
	protected boolean portal;
	
	protected float dx,dy;

	public Creature(Handler handler,float x,float y,int width, int height) {
		super(handler,x,y,width,height);
		health=DEFAULT_HEALTH;
		speed=DEFAULT_SPEED;
		dx=speed;
		deathCount=0;
	}
	
	public void move() {
		moveX();
		moveY();
	}
	
	public void moveX() {
		if(dx>0) {
			
			int tx=(int)(x+dx+1+bounds.width)/Tile.TILEWIDTH;
			int y1=(int)(y+1)/Tile.TILEHEIGHT;
			int y2=(int)(y+1+bounds.height)/Tile.TILEHEIGHT;
			
			if(!collisionWithTile(tx,y1)&&!collisionWithTile(tx,y2)) {
				x+=dx;
			}
			else {
				x=tx*Tile.TILEWIDTH-bounds.width-1-1;
				respawn();
			}
		}
		else if(dx<0) {
			int tx=(int)(x+dx+1)/Tile.TILEWIDTH;
			int y1=(int)(y+1)/Tile.TILEHEIGHT;
			int y2=(int)(y+1+bounds.height)/Tile.TILEHEIGHT;
			if(!collisionWithTile(tx,y1)&&!collisionWithTile(tx,y2)) {
				x+=dx;
			}
			else {
				x=tx*Tile.TILEWIDTH+Tile.TILEWIDTH-1+1;
				respawn();
			}
		}
	}
	
	public void moveY() {
		if(dy>0) {
			int ty=(int)(y+dy+1+bounds.height)/Tile.TILEHEIGHT;
			int x1=(int)(x+1)/Tile.TILEWIDTH;
			int x2=(int)(x+1+bounds.width)/Tile.TILEWIDTH;
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				
				y+=dy;
			}
			else {
				y=ty*Tile.TILEHEIGHT-bounds.height-1-1;
			}
		}
		else if(dy<0) {
			
			int ty=(int)(y+dy+1)/Tile.TILEHEIGHT;
			int x1=(int)(x+1)/Tile.TILEWIDTH;
			int x2=((int)(x+1+bounds.width)/Tile.TILEWIDTH);
			
			if(!collisionWithTile(x1,ty)&&!collisionWithTile(x2,ty)) {
				y+=dy;
			}
			else {
				y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-1+1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x,int y) {
		if(handler.getWorld().getTile(x,y).getId()==21)// is portal tile
		{
			portal=true;
			rot=0;
		}

		if(handler.getWorld().getTile(x,y).isSmallTile()) {
			while(handler.getWorld().getTile(x,y).getCollisionBoxes().size()>0) {
				if(handler.getWorld().getTile(x,y).getCollisionBoxes().pop().intersects(bounds)) {
					respawn();
				}
			}
			return false;
		}
		return handler.getWorld().getTile(x, y).isSolid();
	
	}
	
	protected boolean isSmallTile(int x,int y) {
		return handler.getWorld().getTile(x, y).isSmallTile();
	}
	
	
	public void respawn()
	{
		respawn=true;
		respawnCounter=0;
		rot=0;
		deathCount++;
		portal=false;
		
	}
	public boolean isDead()
	{
		return respawn;
	}
	public int getDeathCount()
	{
		return deathCount;
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
