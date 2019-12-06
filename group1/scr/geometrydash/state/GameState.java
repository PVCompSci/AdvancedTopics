package geometrydash.state;
import java.awt.Color;
import java.awt.Graphics;

import geometrydash.BackgroundAesthetics;
import geometrydash.Handler;
import geometrydash.entities.creatures.Player;
import geometrydash.tiles.Tile;
import geometrydash.worlds.World;

public class GameState extends State{
	
	private Player player;
	private World world;
	private int id;

	public GameState(Handler handler, int id) {
		super(handler,id);
		this.id=id;
		world=new World(handler,"res/worlds/world1");
		handler.setWorld(world);
		player=new Player(handler,handler.getWorld().getSpawnX()*Tile.TILEHEIGHT,handler.getWorld().getSpawnY()*Tile.TILEHEIGHT);
		
	}
	public Player getPlayer() {
		return player;
	}
	
	public void tick() {
		world.tick();
		player.tick();
	}

	public void render(Graphics g) {
		
		world.render(g);
		player.render(g);
		
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
