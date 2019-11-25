package nonogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class NonogramVisuals extends JFrame implements KeyListener {
	//Stores if checking or filling box.
	private static boolean checking = false;
	private static JButton[][] buttons;
	private static JButton currentHover;
	private static JButton button;
	
	private static Color BLACK = new Color(0, 0, 0);
	private static Color DARK1 = new Color(70, 70, 70);
	private static Color DARK2 = new Color(120, 120, 120);
	private static Color CONTROL = new Color(170, 170, 170);
	private static Color LIGHT2 = new Color(204, 204, 204);
	private static Color LIGHT1 = new Color(230, 230, 230);
	private static Color WHITE = new Color(255, 255, 255);
	
	//Add KeyListener to Frame!
	public NonogramVisuals() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	 
	//#MAINMETHODISEPICAMIRIGHT?
    public static void main(String[] args) throws FileNotFoundException { 
    	Nonogram nono = new Nonogram();
    	NonogramVisuals frame = new NonogramVisuals();
        
    	//NOT MY (BRIAN'S) PROBLEM TO POPULATE!
        String[][] values = {
        		   { "1", "Steve", "AUS" },
        		   { "2", "Virat", "IND" },
        		   { "3", "Kane", "NZ" },
        		   { "4", "David", "AUS" },
        		   { "5", "Ben", "ENG" }};
  
        //Create JPanels (for formating).
        JPanel pMain = new JPanel(new BorderLayout()); 
        JPanel pLayer1 = new JPanel(new BorderLayout());
        JPanel pLayer2 = new JPanel(new GridLayout(values.length, values.length));
        pMain.setBackground(CONTROL);
        pLayer1.setBackground(CONTROL);
        pLayer2.setBackground(CONTROL);
        
        //Create JPanel elements (text, buttons, and axes).
        button = new JButton("Switch to White (Space Bar)");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		toggle();
        	}
        });
        JPanel xAxis = new JPanel(new GridLayout(1, values.length));
        xAxis.setBackground(CONTROL);
        JPanel yAxis = new JPanel(new GridLayout(values.length, 1));
        yAxis.setBackground(CONTROL);
        for(int i = 0; i < values.length; i++) {
        	xAxis.add(new JLabel(i + 1 + ""));
        	yAxis.add(new JLabel(i + 1 + ""));
        }
        
        //Create Grid elements (buttons).
        buttons = new JButton[values.length][values.length];
        for(int i = 0; i < values.length; i++){
            for(int x = 0; x < values.length; x++){
                buttons[i][x] = new JButton();
                buttons[i][x].setFocusable(false);
                //https://stackoverflow.com/a/7647969
                buttons[i][x].setOpaque(true);
                buttons[i][x].setBorderPainted(false);
                buttons[i][x].setBackground(CONTROL);
                //https://alvinalexander.com/java/jbutton-listener-pressed-actionlistener
                buttons[i][x].addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		JButton source = (JButton) e.getSource();
                		
                		if(checking) {
                			System.out.println("IsNotFilled");
                			source.setBackground(WHITE);
                		} else {
                			System.out.println("IsFilled");
                			source.setBackground(BLACK);
                		}
                		
                		source.setSelected(false);
                		source.setEnabled(false);
                	}
                });
                //https://stackoverflow.com/a/22639054
                buttons[i][x].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                    	JButton source = (JButton) evt.getSource();
                    	currentHover = source;
                    	if(source.getBackground() != BLACK && source.getBackground() != WHITE) {
                    		source.setBackground(Color.RED);
                    		if(checking) {
                    			source.setBackground(LIGHT1);
                    		} else {
                    			source.setBackground(DARK1);
                    		}
                    	}
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                    	JButton source = (JButton) evt.getSource();
                    	currentHover = null;
                    	if(source.getBackground() != BLACK && source.getBackground() != WHITE) {
                    		source.setBackground(CONTROL);
                    	}
                    }
                });
                pLayer2.add(buttons[i][x]);
            }
        }
  
        //Assembles the frame and positions stuff.
        pMain.add(button, BorderLayout.SOUTH); 
        pMain.add(yAxis, BorderLayout.WEST); 
        pLayer1.add(xAxis, BorderLayout.NORTH); 
        pLayer1.add(pLayer2, BorderLayout.CENTER); 
        pMain.add(pLayer1, BorderLayout.CENTER); 
  
        //Adding the main JPanel to the JFrame and setting the frame visible.
        frame.add(pMain);
        //https://stackoverflow.com/a/11570414
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    //Toggles between the black and white tiles.
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
}