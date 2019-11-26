package geometrydash.state;
import java.awt.Graphics;

import geometrydash.Handler;
import geometrydash.entities.creatures.Player;
import geometrydash.tiles.Tile;
import geometrydash.worlds.World;

public class GameState extends State{
	
	private Player player;
	private World world;

	public GameState(Handler handler) {
		super(handler);
		world=new World(handler,"res/worlds/world1");
		handler.setWorld(world);
		player=new Player(handler,handler.getWorld().getSpawnX()*Tile.TILEHEIGHT,handler.getWorld().getSpawnY()*Tile.TILEHEIGHT);			
	}
	
	public void tick() {
		world.tick();
		player.tick();
	}

	public void render(Graphics g) {
		
		world.render(g);
		player.render(g);
	}

}
