package geometrydash.gfx;

import geometrydash.Game;
import geometrydash.entities.Entity;
import geometrydash.tiles.Tile;

public class GameCamera {

	private Game game;
	private float xOffset,yOffset,defaultYOff,dy=5;
	
	public GameCamera(Game game, float xOffset, float yOffset) {
		this.game=game;
		this.xOffset=xOffset;
		this.yOffset=yOffset;

		defaultYOff=game.getHeight()+200;
		this.yOffset=defaultYOff;
	}
	public void centerOnEntity(Entity e) {
		
		xOffset=e.getX()-game.getWidth()/2+e.getWidth()*4;
		
		if(!game.getGameState().getPlayer().isPortal()) {
		
			if(!game.getGameState().getPlayer().isRespawning()) {
				if(e.getY()-yOffset<200)
					yOffset-=dy;
				else if(e.getY()-yOffset>400) {
					yOffset+=dy;
					if(yOffset>=defaultYOff)
						yOffset=defaultYOff;
				}
			}
		}
		else {
			yOffset=697;
		}
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
	public void resetYOffset() {
		yOffset=defaultYOff;
	}
}
