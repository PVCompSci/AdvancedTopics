package geometrydash.worlds;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

import geometrydash.Handler;
import geometrydash.gfx.Assets;
import geometrydash.tiles.Tile;
import geometrydash.utils.Utils;

public class World {

	private Handler handler;
	private int width,height,spawnX,spawnY;
	private int[][]tiles;
	private double backDx,floorDx;
	private float backX,floorX;
	
	public World(Handler handler,String path) { //initiate image icons for gifs
		
		this.handler=handler;
		loadWorld(path);
		backDx=-.1;
		floorDx=-11;
		
	}
	
	public void tick() {
		
		if(!handler.getGame().getGameState().getPlayer().isRespawning()) {
			backX+=backDx;
			floorX+=floorDx;
		}
		handler.getGame().getGameCamera().centerOnEntity(handler.getGame().getGameState().getPlayer());
		
	}
	
	public void render(Graphics g) {
		int xStart=(int)Math.max(0, handler.getGameCamera().getxOffset()/Tile.TILEWIDTH);
		int yStart=(int)Math.max(0, handler.getGameCamera().getyOffset()/Tile.TILEHEIGHT);
		int xEnd=(int)Math.min(width, (handler.getGameCamera().getxOffset()+handler.getWidth())/Tile.TILEWIDTH+1);
		int yEnd=(int)Math.min(height, (handler.getGameCamera().getyOffset()+handler.getHeight())/Tile.TILEHEIGHT+1);
				
		resetTileCollisions();		
		
		
		g.drawImage(Assets.background, (int)backX, 0, handler.getWidth(), handler.getHeight(),null);
		g.drawImage(Assets.background, (int)backX+handler.getWidth(), 0, handler.getWidth(), handler.getHeight(),null);
		if(backX+handler.getWidth()<=0)
			backX=0;
		
		g.drawImage(Assets.floorBackground, (int)floorX, 1472-(int)handler.getGameCamera().getyOffset(), handler.getWidth()+10, 300,null);
		g.drawImage(Assets.floorBackground, (int)floorX+handler.getWidth()+10, 1472-(int)handler.getGameCamera().getyOffset(), handler.getWidth()+10, 300,null);
		if(floorX+handler.getWidth()<=0)
			floorX=0;
		
		
		g.setColor(Color.white);
		float opacity = 0.5f;
		Graphics2D g2= (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawLine(200, 832-(int)handler.getGameCamera().getyOffset(), 1080, 832-(int)handler.getGameCamera().getyOffset());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		for(int y=yStart;y<yEnd;y++) {
			for(int x=xStart;x<xEnd;x++) {
				getTile(x,y).render(g, (int)(x*Tile.TILEWIDTH-handler.getGameCamera().getxOffset()),(int)(y*Tile.TILEHEIGHT-handler.getGameCamera().getyOffset()));;
			}
		}
		
	}
	
	public void resetTileCollisions() {

		getTile(0,0).resetCollisionBoxes();
	}
	
	public Tile getTile(int x,int y) {
		
		if(x<0||y<0||x>=width||y>=height)
			return Tile.airTile;
		
		Tile t=Tile.tiles[tiles[x][y]];
		
		if(t==null)
			return Tile.airTile;
		
		return t;
	}
	
	public int getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}

	private void loadWorld(String path) {
		
		String file=Utils.loadFileAsString(path);
		String[] tokens=file.split("\\s+");
		width=Utils.parseInt(tokens[0]);
		height=Utils.parseInt(tokens[1]);
		spawnX=Utils.parseInt(tokens[2]);
		spawnY=Utils.parseInt(tokens[3]);
		
		System.out.println(width);
		System.out.println(height);
		System.out.println(spawnX);
		System.out.println(spawnY);
		
		tiles=new int[width][height];
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y]=Utils.parseInt(tokens[(x+y*width)+4]);
			}
		}
	}
	
}
