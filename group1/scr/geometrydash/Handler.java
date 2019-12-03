package geometrydash;

import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import geometrydash.gfx.GameCamera;
import geometrydash.input.KeyManager;
import geometrydash.input.MouseManager;
import geometrydash.worlds.World;

public class Handler {

	private Game game;
	private World world;
	private Clip clip;
	public Handler(Game game) {
		this.game=game;
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	public MouseManager getMouseManager()
	{
		return game.getMouseManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}
	public synchronized void playSound(String url)
	{
	      new Thread(() ->
	      {
	         try {
	            clip = AudioSystem.getClip();
	            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
	                  new BufferedInputStream(
	                        getClass().getResourceAsStream("" + url)));
	            clip.open(inputStream);
	            clip.start();         
	           	
	         } catch (Exception e) {
	            System.err.println(e.getMessage());
	         }
	      }).start(); 
		   	      
	}
	public Clip getClip()
	{
		return clip;
	}
	public void setWorld(World world) {
		this.world = world;
	}
}
