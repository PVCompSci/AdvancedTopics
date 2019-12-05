package geometrydash.tiles;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Stack;

import geometrydash.gfx.Assets;

public class Tile {

	public static final int TILEWIDTH=64,TILEHEIGHT=64;

	public static Tile[] tiles=new Tile[256];
	public static Tile airTile=new AirTile(0,Assets.air);
	
	public static Tile cubePT=new AirTile(21,Assets.cubePT);
	public static Tile cubePM=new AirTile(22,Assets.cubePM);
	public static Tile cubePB=new AirTile(23,Assets.cubePB);
	public static Tile shipPT=new AirTile(24,Assets.shipPT);
	public static Tile shipPM=new AirTile(25,Assets.shipPM);
	public static Tile shipPB=new AirTile(26,Assets.shipPB);	
	
	public static Tile ground=new SolidTile(1,Assets.ground);
	public static Tile floorTile=new SolidTile(2,Assets.floor1);
	public static Tile borderTile=new SolidTile(5,Assets.barrierMid);
	public static Tile spikeTile1=new SpikeTile(3,true,new Polygon(new int[] {0,TILEWIDTH/2,TILEWIDTH},new int[] {TILEHEIGHT,0,TILEHEIGHT},3),Assets.spike1);
	public static Tile spikeTile2=new SpikeTile(4,true,new Polygon(new int[] {0,TILEWIDTH/2,TILEWIDTH},new int[] {TILEHEIGHT,TILEHEIGHT/2,TILEHEIGHT},3),Assets.spike2);
	public static Tile spikeTile3=new SpikeTile(27,true,new Polygon(new int[]{0,0,TILEWIDTH,TILEWIDTH},new int[] {TILEHEIGHT,3*TILEHEIGHT/4,3*TILEHEIGHT/4,TILEHEIGHT},3),Assets.spike3);
	
	public static Tile barrierMid=new SolidTile(5,Assets.barrierMid);
	public static Tile barrierSL=new SolidTile(6,Assets.barrierSL);
	public static Tile barrierST=new SolidTile(7,Assets.barrierST);
	public static Tile barrierSR=new SolidTile(8,Assets.barrierSR);
	public static Tile barrierSB=new SolidTile(9,Assets.barrierSB);
	public static Tile barrierCTR=new SolidTile(10,Assets.barrierCTR);
	public static Tile barrierCTL=new SolidTile(11,Assets.barrierCTL);
	public static Tile barrierCBR=new SolidTile(12,Assets.barrierCBR);
	public static Tile barrierCBL=new SolidTile(13,Assets.barrierCBL);
	public static Tile barrierEB=new SolidTile(14,Assets.barrierEB);
	public static Tile barrierEL=new SolidTile(15,Assets.barrierEL);
	public static Tile barrierET=new SolidTile(16,Assets.barrierET);
	public static Tile barrierER=new SolidTile(17,Assets.barrierER);
	public static Tile barrierC=new SolidTile(18,Assets.barrierC);
	public static Tile barrierR=new SolidTile(19,Assets.barrierR);
	public static Tile slab=new SolidTile(20,Assets.slab);
		
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
		spikeTile1.resetCollisionBoxes();
		spikeTile2.resetCollisionBoxes();
	}
	
	public int getId() {
		
		return id;
	}
	
	public boolean isSmallTile() {
		return false;
	}
	
}
