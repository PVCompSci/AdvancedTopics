package geometrydash.state;
import java.awt.Color;
import java.awt.Graphics;
import geometrydash.Handler;
import geometrydash.entities.creatures.Player;
import geometrydash.tiles.Tile;
import geometrydash.worlds.World;

public class GameState extends State{
	
	private Player player;
	private World world;
	private int id;
	private boolean levelComplete;

	public GameState(Handler handler, int id) {
		super(handler,id);
		this.id=id;
		world=new World(handler,"res/worlds/world1");
		handler.setWorld(world);
		levelComplete=false;
		
		player=new Player(handler,handler.getWorld().getSpawnX()*Tile.TILEWIDTH,handler.getWorld().getSpawnY()*Tile.TILEHEIGHT);			

	}
	public Player getPlayer() {
		return player;
	}
	
	public void tick() {
		if(!levelComplete)
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
	
	public void setLevelComplete(boolean bool) {
		levelComplete=bool;
	}
	
	public boolean isLevelComplete() {
		return levelComplete;
	}

}
