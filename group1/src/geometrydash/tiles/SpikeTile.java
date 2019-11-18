package geometrydash.tiles;

import java.awt.Polygon;

public class SpikeTile extends SmallTile{

	public SpikeTile(int id,boolean isDeadly,Polygon p) {
		super(id);
		this.isDeadly=isDeadly;
		collisionBox=p;
	}
}
