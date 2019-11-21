package geometrydash.tiles;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Stack;

public class SpikeTile extends SmallTile{

	public SpikeTile(int id,boolean isDeadly,Polygon p) {
		super(id);
		this.isDeadly=isDeadly;
		collisionBox=p;
		collisionBoxes=new Stack<Polygon>();
	}
	
	public void resetCollisionBoxes() {
		collisionBoxes=new Stack<Polygon>();
	}
	
	public void render(Graphics g, int x, int y) {
		collisionBoxes.push(new Polygon(new int[] {0+x,TILEWIDTH/2+x,TILEWIDTH+x},new int[] {TILEHEIGHT+y,0+y,TILEHEIGHT+y},3));
		g.drawImage(texture, x, y,TILEWIDTH,TILEHEIGHT,null);
	}
}
