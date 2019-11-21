package geometrydash.tiles;

import java.awt.Polygon;

import geometrydash.gfx.Assets;

public abstract class SmallTile extends Tile{
	
	protected Polygon collisionBox;
	protected boolean isDeadly;
	
	public SmallTile(int id) {
		super(Assets.spike, id);
	}

	public boolean isSolid() {
		return true;
	}
	
	public Polygon getCollisionBox(){
		return collisionBox;
	}

	public boolean isDeadly() {
		return isDeadly;
	}
	
	public boolean isSmallTile() {
		return true;
	}
	

}
