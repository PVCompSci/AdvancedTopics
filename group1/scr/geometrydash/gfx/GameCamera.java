package geometrydash.gfx;

import geometrydash.Game;
import geometrydash.entities.Entity;
import geometrydash.tiles.Tile;

public class GameCamera {

	private Game game;
	private float xOffset,yOffset,defaultYOff,dy=3;
	
	public GameCamera(Game game, float xOffset, float yOffset) {
		this.game=game;
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		defaultYOff=game.getHeight()/2-Tile.TILEHEIGHT;
		yOffset=defaultYOff;
	}
	
	public void centerOnEntity(Entity e) {
		
		xOffset=e.getX()-game.getWidth()/2+e.getWidth()/2;
		
		if(e.getY()<200)
			yOffset-=dy;
		else if(e.getY()>600) {
			yOffset-=dy;
			if(yOffset<=defaultYOff)
				yOffset=defaultYOff;
		}
				
		System.out.println(e.getY());
		System.out.println(yOffset);
	}
	
	public void move(float xAmt,float yAmt) {
		xOffset+=xAmt;
		yOffset+=yAmt;
	}
	
	public float getxOffset() {
		return xOffset;
	}
	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}
	public float getyOffset() {
		return yOffset;
	}
	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}
