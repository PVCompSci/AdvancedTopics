package geometrydash.gfx;

import geometrydash.Game;
import geometrydash.entities.Entity;
import geometrydash.tiles.Tile;

public class GameCamera {

	private Game game;
	private float xOffset,yOffset, cameraDy;
	
	public GameCamera(Game game, float xOffset, float yOffset) {
		this.game=game;
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		cameraDy=0;
	}
	
	public void centerOnEntity(Entity e) {
		
		xOffset=e.getX()-game.getWidth()/2+e.getWidth()/2;
		
		if(e.getY()<400 && e.getY()>200)
		{
			cameraDy-=2;
		}
		else if(e.getY()<200)
			cameraDy+=2;
		else if(e.getY()>400)
			cameraDy=0;
			
		
		yOffset=game.getHeight()/2-Tile.TILEHEIGHT+cameraDy;
		
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
