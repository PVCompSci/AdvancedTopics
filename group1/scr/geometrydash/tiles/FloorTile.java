package geometrydash.tiles;

import geometrydash.gfx.Assets;

public class FloorTile extends Tile{

	public FloorTile(int id) {
		
		super(Assets.floor,id);
	}
	
	public boolean isSolid() {
		return true;
	}
}
