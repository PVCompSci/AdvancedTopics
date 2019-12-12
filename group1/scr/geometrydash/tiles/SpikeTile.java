package geometrydash.tiles;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Stack;

import geometrydash.gfx.Assets;

public class SpikeTile extends SmallTile{

	public SpikeTile(int id,boolean isDeadly,Polygon p,BufferedImage img) {
		super(id,img);
		this.isDeadly=isDeadly;
		collisionBox=p;
		collisionBoxes=new Stack<Polygon>();
	}
	
	public void resetCollisionBoxes() {
		collisionBoxes=new Stack<Polygon>();
	}
	
	public void render(Graphics g, int x, int y) {
		collisionBoxes.push(new Polygon(new int[] {collisionBox.xpoints[0]+x,collisionBox.xpoints[1]+x,collisionBox.xpoints[2]+x},new int[] {collisionBox.ypoints[0]+y,collisionBox.ypoints[1]+y,collisionBox.ypoints[2]+y},3));
		g.drawImage(texture, x, y,TILEWIDTH,TILEHEIGHT,null);
		g.fillPolygon(collisionBoxes.peek());
	}
}
