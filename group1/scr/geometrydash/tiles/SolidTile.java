package geometrydash.tiles;

import java.awt.image.BufferedImage;

import geometrydash.gfx.Assets;

public class SolidTile extends Tile{

	public SolidTile(int id,BufferedImage img) {
		
		super(img,id);
	}
	
	public boolean isSolid() {
		return true;
	}
}
