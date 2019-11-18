package geometrydash.tiles;

import geometrydash.gfx.Assets;

public class WallTile extends Tile{

	public WallTile(int id) {
		
		super(Assets.barrier,id);
	}
	
	public boolean isSolid() {
		return true;
	}
}
