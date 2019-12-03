package nonogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class NonogramVisuals extends JFrame implements KeyListener {
	private static boolean checking = false;
	private static JButton[][] buttons;
	private static JButton currentHover;
	private static Stack<JButton> currentClick = new Stack<JButton>();
	private static boolean clicked = false;
	private static JButton button;

	private static Color BLACK = new Color(0, 0, 0);
	private static Color DARK1 = new Color(70, 70, 70);
	private static Color DARK2 = new Color(120, 120, 120);
	private static Color CONTROL = new Color(170, 170, 170);
	private static Color LIGHT2 = new Color(204, 204, 204);
	private static Color LIGHT1 = new Color(230, 230, 230);
	private static Color WHITE = new Color(255, 255, 255);
	private static int wrong;

	public NonogramVisuals() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public static void main(String[] args) throws Exception { 
		printVerboseProcessReport();


		Nonogram nono = new Nonogram();
		NonogramVisuals frame = new NonogramVisuals();    

		//        String[][] values = {
		//        		   { "1", "Steve", "AUS" },
		//        		   { "2", "Virat", "IND" },
		//        		   { "3", "Kane", "NZ" },
		//        		   { "4", "David", "AUS" },
		//        		   { "5", "Ben", "ENG" },
		//        		   { "6", "Tim", "GER"},
		//        		   { "7", "John", "IND"},
		//        		   { "8", "Juan", "USA"},
		//        		   { "9", "Christian", "ENG"},
		//        		   { "10", "Dean", "HELL"}};


		String[][] values = new String[nono.getQueueSize()][nono.getQueueSize()];

		wrong=0;

		boolean imageArray[][] = nono.getImageArray();

		JPanel pMain = new JPanel(new BorderLayout()); 
		JPanel pLayer1 = new JPanel(new BorderLayout());
		JPanel pLayer2 = new JPanel(new GridLayout(values.length, values.length));
		pLayer1.setBackground(CONTROL);
		pLayer2.setOpaque(false);

		button = new JButton("Switch to White (Space Bar)");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggle();
			}
		});

		JPanel xAxis = new JPanel(new GridLayout(1, values.length));
		xAxis.setBackground(CONTROL);
		Queue theXFactor = nono.getVerticalQueue();
		JPanel yAxis = new JPanel(new GridLayout(values.length, 1));
		yAxis.setBackground(CONTROL);
		Queue theYFactor = nono.getHorizontalQueue();

		for(int i = 0; i < values.length; i++) {
			Stack xStack = (Stack) theXFactor.poll();
			int xSize = xStack.size();
			JPanel xTable = new JPanel(new GridLayout(xStack.size(), 1));
			for(int j = 0; j < xSize; j++) {
				JLabel temp = new JLabel(" " + xStack.pop() + " ");
				temp.setHorizontalAlignment(JLabel.CENTER);
				xTable.add(temp);
			}
			xAxis.add(xTable);

			Stack yStack = (Stack) theYFactor.poll();
			int ySize = yStack.size();
			JPanel yTable = new JPanel(new GridLayout(1, yStack.size()));
			for(int k = 0; k < ySize; k++) {
				JLabel temp = new JLabel(" " + yStack.pop() + " ");
				temp.setHorizontalAlignment(JLabel.CENTER);
				yTable.add(temp);
			}
			yAxis.add(yTable);
		}

		buttons = new JButton[values.length][values.length];
		for(int i = 0; i < values.length; i++){
			for(int x = 0; x < values.length; x++){
				buttons[i][x] = new JButton();

				buttons[i][x].putClientProperty("r", i);
				buttons[i][x].putClientProperty("c", x);

				buttons[i][x].setFocusable(false);

				//https://stackoverflow.com/a/7647969
				buttons[i][x].setOpaque(true);
				buttons[i][x].setBorder(new LineBorder(Color.BLACK));
				buttons[i][x].setBackground(CONTROL);

				//https://stackoverflow.com/a/22639054
				buttons[i][x].addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseEntered(java.awt.event.MouseEvent evt) {
						JButton source = (JButton) evt.getSource();
						currentHover = source;
						if(source.getBackground() != BLACK && source.getBackground() != WHITE) {
							if(checking) {
								source.setBackground(LIGHT1);
							} else {
								source.setBackground(DARK1);
							}
						}
						if(clicked && (source.getBackground() != BLACK && source.getBackground() != WHITE)) currentClick.add(source);
					}
					public void mouseExited(java.awt.event.MouseEvent evt) {
						JButton source = (JButton) evt.getSource();
						currentHover = null;
						if(clicked && (source.getBackground() != BLACK && source.getBackground() != WHITE)) {
							if(checking) {
								source.setBackground(LIGHT2);
							} else {
								source.setBackground(DARK2);
							}
						} else if(source.getBackground() != BLACK && source.getBackground() != WHITE) {
							source.setBackground(CONTROL);
						}
					}
					public void mousePressed(java.awt.event.MouseEvent evt) {
						JButton source = (JButton) evt.getSource();
						currentClick.add(source);
						clicked = true;
					}
					public void mouseReleased(java.awt.event.MouseEvent evt) {

						while(!currentClick.isEmpty()) {
							JButton source = (JButton) currentClick.pop();

							if(source.getBackground() != BLACK && source.getBackground() != WHITE) {

								if(checking){

									int r=(int) source.getClientProperty("c");
									int c=(int) source.getClientProperty("r");

									if(imageArray[r][c]) {
										source.setText("x");
										source.setBackground(BLACK);
										wrong++;
									}else {
										source.setBackground(WHITE);
									}

								}else{

									int r=(int) source.getClientProperty("c");
									int c=(int) source.getClientProperty("r");

									if(imageArray[r][c]) {
										source.setBackground(BLACK);
									}else {
										source.setText("x");
										source.setBackground(WHITE);
										wrong++;
									}
								}

								if(wrong>=3) {
									JOptionPane.showMessageDialog(new JFrame(), "You got three wrong","You lose!", JOptionPane.PLAIN_MESSAGE);
									System.exit(0);
								}

							}

							source.setSelected(false);
							source.setEnabled(false);
						}
						clicked = false;

					}
				});
				pLayer2.add(buttons[i][x]);
			}

		}

		pMain.add(button, BorderLayout.SOUTH); 
		pMain.add(yAxis, BorderLayout.WEST); 
		pMain.add(new JPanel(), BorderLayout.EAST); 
		pLayer1.add(xAxis, BorderLayout.NORTH);  
		pLayer1.add(pLayer2, BorderLayout.CENTER); 
		pMain.add(pLayer1, BorderLayout.CENTER);
		frame.add(pMain);

		//https://stackoverflow.com/a/11570414
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void toggle() {
		checking = !checking;
		if(checking) {
			if(currentHover != null && currentHover.getBackground() != BLACK && currentHover.getBackground() != WHITE)
				currentHover.setBackground(LIGHT1);
			button.setText("Switch to Black (Space Bar)");
		} else {
			if(currentHover != null && currentHover.getBackground() != BLACK && currentHover.getBackground() != WHITE)
				currentHover.setBackground(DARK1);
			button.setText("Switch to White (Space Bar)");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE) toggle();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {} 

	//https://www.quickprogrammingtips.com/java/how-to-get-the-list-of-running-processes-in-mac-using-java.html
	private static void printVerboseProcessReport() throws Exception{
		Process process = Runtime.getRuntime().exec("ps -er -o %cpu,%mem,flags,lim,lstart,nice,rss,start,state,tt,wchan,command ");
		BufferedReader r =  new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;

		int count = 0;
		while((line=r.readLine())!=null) {
			if(line.contains("nonogram.NonogramVisuals")) count++;
		}

		if(count > 1) {
			JOptionPane.showMessageDialog(new JFrame(),
					"This program is running more than once. Please close the other program and try again.",
					"Oh no!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}