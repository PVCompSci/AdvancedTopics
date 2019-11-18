package geometrydash.state;
import java.awt.Graphics;

import geometrydash.Game;
import geometrydash.Handler;
import geometrydash.entities.creatures.Player;
import geometrydash.tiles.Tile;
import geometrydash.worlds.World;

public class GameState extends State{
	
	private Player player;
	private World world;

	public GameState(Handler handler) {
		super(handler);
		world=new World(handler,"/Users/amoskowitz20/git/AdvancedTopics/group1/res/worlds/world1");
		handler.setWorld(world);
		player=new Player(handler,Tile.TILEWIDTH*4,Tile.TILEHEIGHT*12);			
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
