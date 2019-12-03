package nonogram;

import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

public class a
{
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
			//I don't know if this will work perfectly if exported. I think we'll need to test it somehow
			File musicPath = new File(new File("").getAbsolutePath() + "/src/nonogram/Megalovania.wav");
			System.out.println(musicPath);
			
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
}