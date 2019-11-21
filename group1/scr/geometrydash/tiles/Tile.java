package geometrydash.tiles;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Tile {

	public static final int TILEWIDTH=64,TILEHEIGHT=64;

	public static Tile[] tiles=new Tile[256];
	public static Tile airTile=new AirTile(0);
	public static Tile floorTile=new FloorTile(1);
	public static Tile wallTile=new WallTile(2);
	public static Tile spikeTile=new SpikeTile(3,true,new Polygon(new int[] {0,TILEWIDTH/2,TILEWIDTH},new int[] {TILEHEIGHT,0,TILEHEIGHT},3));
	public static Tile borderTile=new BorderTile(4);
		
	protected BufferedImage texture;
	protected final int id;
	protected Stack<Polygon> collisionBoxes;
	
	public Tile(BufferedImage texture,int id) {
		
		this.texture=texture;
		this.id=id;
		
		tiles[id]=this;
	}
	
	public void tick() {
		
	}
	
	public Stack<Polygon> getCollisionBoxes() {
		return collisionBoxes;
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y,TILEWIDTH,TILEHEIGHT,null);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	
	public void resetCollisionBoxes() {
		spikeTile.resetCollisionBoxes();
	}
	
	public int getId() {
		
		return id;
	}
	
	public boolean isSmallTile() {
		return false;
	}
	
}
