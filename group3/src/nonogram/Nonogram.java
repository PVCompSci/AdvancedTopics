package nonogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Nonogram 
{
	private Queue<Stack> horizontal, vertical;
	private int[] shadedHorizontal, shadedVertical;
	private boolean imageArray[][];

	public Nonogram() throws FileNotFoundException
	{
		readImage();
	}
	
	/**
	 * @return horizontal, the horizontal queue, that stores stacks of the pixels horizontally
	 */
	public Queue<Stack> getHorizontalQueue()
	{
		return horizontal;
	}
	
	/**
	 * @return vertical, the vertical queue, that stores stacks of the pixels vertically
	 */
	public Queue<Stack> getVerticalQueue()
	{
		return vertical;
	}
	
	/**
	 * @return shadedHorizontal, the array that stores how many pixels are shaded in horizontally
	 */
	public int[] getPixelsShadedHorizontally()
	{
		return shadedHorizontal;
	}
	
	/**
	 * @return shadedVertical, the array that stores how many pixels are shaded in vertically
	 */
	public int[] getPixelsShadedVertically()
	{
		return shadedVertical;
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
	
	/**
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

			int largerDimension = ((img.getWidth() > img.getHeight()) ? img.getWidth() : img.getHeight());
			
			imageArray = new boolean[largerDimension][largerDimension];
			
			for(int a = (largerDimension - img.getWidth()) / 2; a < img.getWidth() + ((largerDimension - img.getWidth()) / 2); a++)
			{
				for(int b = (largerDimension - img.getHeight()) / 2; b < img.getHeight() + ((largerDimension - img.getHeight()) / 2); b++) 
					imageArray[a][b] = ((Math.abs(Math.abs(img.getRGB(a - ((largerDimension - img.getWidth()) / 2), b - (largerDimension - img.getHeight()) / 2)) - 16777215)) > (Math.abs(img.getRGB(a - ((largerDimension - img.getWidth()) / 2), b - (largerDimension - img.getHeight()) / 2)))) ? false : true;
			}
			
			//The code commented out here is simply for the sake of testing to see if the image is processed properly
			/*
			BufferedImage newBuff = new BufferedImage(largerDimension, largerDimension, BufferedImage.TYPE_INT_RGB);
			Graphics2D testG2 = newBuff.createGraphics();
			
			for(int a = 0; a < largerDimension; a++)
			{
				for(int b = 0; b < largerDimension; b++)
				{
					testG2.setColor((imageArray[a][b]) ? Color.BLACK : Color.WHITE);
					testG2.drawRect(a, b, 1, 1);
				}
			}
			
			JFrame frame = new JFrame();
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			frame.add(new JLabel(new ImageIcon(newBuff)));
			frame.setVisible(true);
			*/
			
			formatImage(largerDimension);
		}
	}
	
	/**
	 * Converts the image into a format of stacks and queues, alongside
	 * recording the number of shaded pixels for each row and column of
	 * the nonogram.
	 * 
	 * @param queueSize, the size of the queue. Also the dimensions of the image, since it's square 
	 */
	public void formatImage(int queueSize)
	{
		//initializes the Queues
		horizontal = new ArrayBlockingQueue<Stack>(queueSize);
		vertical = new ArrayBlockingQueue<Stack>(queueSize);
		
		//loop that runs for both the horizontal and vertical, inputting the data into both
		for(int a = 0; a < queueSize; a++)
		{
			//initializes the integer used to store the amount of shaded blocks temporarily
			int shadedAmtHori = 0;
			int shadedAmtVert = 0;
			
			//initializes the temporary stacks that will be later input into their respective queues
			Stack<Integer> tempHori = new Stack<Integer>();
			Stack<Integer> tempVert = new Stack<Integer>();
			
			for(int b = 0; b < queueSize; b++)
			{
				//The top "if" and "else" statements are for rows
				if(imageArray[b][a])
					shadedAmtHori++;
				
				else
				{
					if(shadedAmtHori > 0)
						tempHori.push(shadedAmtHori);
					
					shadedAmtHori = 0;
				}
				
				//The bottom "if" and "else" statements are for columns
				if(imageArray[a][b])
					shadedAmtVert++;
				
				else
				{
					if(shadedAmtVert > 0)
						tempVert.push(shadedAmtVert);
					
					shadedAmtVert = 0;
				}
			}
			
			//pushes the data gathered into their respectie storage units (either queues or arrays)
			horizontal.add(tempHori);
			vertical.add(tempVert);
		}
		
		//The code commented out here is simply for the sake of printing out the nonogram through the console as a test
		/*
		System.out.print("   ");
		
		for(int num : shadedVertical)
			System.out.print(" " + num);
		
		System.out.println();
		
		for(int a = 0; a < vertical.size(); a++)
		{
			StringBuilder stackResult = new StringBuilder();
			
			Stack<Integer> temp = horizontal.poll();
			
			while(temp.size() > 0)
				stackResult.append(temp.pop() + " ");
			
			stackResult = stackResult.reverse();
			
			System.out.println(shadedHorizontal[a] + ((shadedHorizontal[a] > 9) ? " " : "  ") + stackResult.toString());
		}
		*/
	}
}
