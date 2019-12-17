package nonogram;

import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

public class a
{
	private Clip clip;
	
	public a()
	{
		//Code commented out here would allow you to choose your own music
		/*
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".wav Files", "wav");

		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(new JFrame());
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				System.out.println(chooser.getSelectedFile());
				File musicPath = chooser.getSelectedFile();
				
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		*/
		
		try
		{
			//this allows the file path to not be hardcoded, and still has it work inside the .jar
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(this.getClass().getResource("/nonogram/27 Let the Game Begin.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void playMegalovania()
	{
		clip.close();
		JOptionPane.showMessageDialog(null, "Blame Eric. He wanted this song.");
		
		try
		{
			//this allows the file path to not be hardcoded, and still has it work inside the .jar
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(this.getClass().getResource("/nonogram/Megalovania.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
