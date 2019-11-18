package geometrydash.tiles;

import geometrydash.gfx.Assets;

public class BorderTile extends Tile{

	public BorderTile(int id) {
		
		super(Assets.border,id);
	}
	
	public boolean isSolid() {
		return true;
	}
}
