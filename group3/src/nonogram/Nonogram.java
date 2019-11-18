package nonogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Nonogram 
{
	private boolean[][] shaded;
	private int[] shadedNumsPerRow;

	private Queue<Stack> horizontal,vertical;

	public Nonogram() throws FileNotFoundException
	{
		readImage();
	}

	/**
	 * Converts a given Image into a BufferedImage (Stolen from StackOverflow)
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
			return (BufferedImage) img;

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	
	/*
	 * Requests the user to input an image
	 * Assembles the image into stacks and queues for use within the graphics
	 * Creates two queues, one for the horizontal, and one for the vertical
	 * These two queues store stacks within them containing either 1s or 0s
	 */
	public void readImage() throws FileNotFoundException
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".png Files", "png");

		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(new JFrame());

		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			/*
			 * The steps below are those taken to put the image into 
			 * boolean values of either true or false. If true, the pixel
			 * is black/colored. Otherwise, the pixel is white/not colored
			 */
			String name = chooser.getSelectedFile().toString();

			Image image = new ImageIcon(name).getImage();

			BufferedImage img = toBufferedImage(image);

			boolean[][] imageArray = new boolean[img.getWidth()][img.getHeight()];
			
			//The code commented out here was just for the sake of testing to make sure everything is good
			
			//BufferedImage newBuff = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			//Graphics2D g2 = newBuff.createGraphics();

			//nested for loop to go through every single pixel in the image
			for(int a = 0; a < imageArray.length; a++)
			{
				for(int b = 0; b < imageArray[a].length; b++) 
				{
					imageArray[a][b] = (img.getRGB(a, b) == 0 || img.getRGB(a, b) == -1) ? false : true;
					
					//g2.setColor((imageArray[a][b]) ? Color.BLACK : Color.WHITE);
					//g2.drawRect(a, b, 1, 1);
				}
			}
			
			//JFrame frame = new JFrame();
			//frame.setSize(newBuff.getWidth(), newBuff.getHeight());
			//frame.add(new JLabel(new ImageIcon(newBuff)));
			//frame.setVisible(true);
		}
	}
}
