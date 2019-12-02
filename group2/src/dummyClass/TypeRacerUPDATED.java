package dummyClass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TypeRacerUPDATED extends JFrame {

	private JButton startButton;
	private JButton restartButton;
	private JPanel textArea = null;
	private JPanel bottomArea;
	private JTextField typingArea;
	private JLabel displayTimeLeft = null;
	private int secondsPassed;
	private int characterCount;
	private int numOfCompletedSentences;
	private ArrayList<String> sentences;
	private Sentence s;
	private boolean gameFinished;
	private boolean gameStarted;

	public TypeRacerUPDATED() {
		super("Type Racer");
	}

	public static void main(String[] args) throws IOException {

		TypeRacerUPDATED tg = new TypeRacerUPDATED();

		tg.startGame();

		FileReader reader = new FileReader(
				"/Users⁩/wjoseph21⁩/Documents/11th Grade⁩/Comp Sci⁩/workspace⁩/Programming Exercises⁩/src/typeracer");

		Scanner sc = new Scanner(reader);

		tg.read(sc);

	}

	public void startGame()

	{

		setSize(1500, 1500);

		setLayout(new BorderLayout());

		sentences = new ArrayList<String>();

		gameFinished = false;

		gameStarted = false;

		secondsPassed = 0;

		characterCount = 0;

		numOfCompletedSentences = 0;

		startButton = new JButton("Press to Start");

		startButton.setPreferredSize(new Dimension(100, 100));

		startButton.addActionListener(new ButtonHandler());

		add(startButton, BorderLayout.NORTH);

		textArea = new DrawPanel();

		textArea.setPreferredSize(new Dimension(300, 300));

		textArea.setBackground(Color.BLACK);

		add(textArea, BorderLayout.CENTER);

		bottomArea = new JPanel();

		bottomArea.setLayout(new FlowLayout());

		restartButton = new JButton("Restart Game");

		restartButton.setPreferredSize(new Dimension(1500, 100));

		restartButton.addActionListener(new ButtonHandler());

		restartButton.setVisible(false);

		typingArea = new JTextField();

		typingArea.setPreferredSize(new Dimension(500, 100));

		typingArea.getDocument().addDocumentListener(new ListenText());

		bottomArea.add(typingArea);

		displayTimeLeft = new JLabel("Time left: " + (60 - secondsPassed) + " seconds");

		bottomArea.add(displayTimeLeft);

		add(bottomArea, BorderLayout.SOUTH);

		s = new Sentence();

		s.state = null;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		show();

	}

	class TimeRun extends JPanel {

		Timer timer;

		public TimeRun() {

			timer = new Timer(1000, new ActionListener() {

				@Override

				public void actionPerformed(ActionEvent e) {

					secondsPassed++;

					if ((numOfCompletedSentences < 10) && (secondsPassed < 60)) {

						displayTimeLeft.setText("Time Left: " + (60 - secondsPassed) + " seconds");

					}

					else {

						((Timer) (e.getSource())).stop();

						gameFinished = true;

						add(restartButton, BorderLayout.SOUTH);

						restartButton.setVisible(true);

						restartButton.setEnabled(false);

						for (int i = 0; i < typingArea.getText().length(); i++)

						{

							if (typingArea.getText().substring(i).equals(" ")
									|| !(typingArea.getText().substring(i).equals(" ")))

							{

								characterCount++;

							}

						}

					}

				}

			});

			timer.setInitialDelay(0);

			timer.start();

		}

	}

	class TimeRestart extends JPanel {

		Timer timer;

		int secondsPassed1 = 0;

		public TimeRestart() {

			timer = new Timer(1000, new ActionListener() {

				@Override

				public void actionPerformed(ActionEvent e) {

					secondsPassed1++;

					if (secondsPassed1 == 10)

					{

						((Timer) (e.getSource())).stop();

						restartButton.setEnabled(true);

					}

				}

			});

			timer.setInitialDelay(0);

			timer.start();

		}

	}

	public void read(Scanner sc)

	{

		while (sc.hasNextLine())

		{

			String check = sc.nextLine();

			if (!(check.equals("#")))

			{

				sentences.add(check);

			}

		}

	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;

			Font f = new Font("Times New Roman", Font.BOLD, 50); // changes font

			FontMetrics fm = this.getFontMetrics(f);

			g2.setFont(f);

			g2.setColor(Color.white);

			if (gameStarted == false)

			{

				Font f1 = new Font("Times New Roman", Font.BOLD, 150); // changes font

				FontMetrics fm1 = this.getFontMetrics(f);

				g2.setFont(f1);

				g2.setColor(Color.white);

				g2.drawString("TypeRacer ", 350, 200);

				g2.drawLine(350, 200, 1050, 200);

				Font f2 = new Font("Times New Roman", Font.BOLD, 50); // changes font

				FontMetrics fm2 = this.getFontMetrics(f);

				g2.setFont(f2);

				g2.drawString("How to Play: ", 100, 400);

				Font f3 = new Font("Times New Roman", Font.BOLD, 25);

				FontMetrics fm3 = this.getFontMetrics(f);

				g2.setFont(f3);

				g2.drawString("1) After pressing start, immediately start typing the text on the screen ", 120, 450);

				g2.drawString(
						"2) The words must match exactly, otherwise you will not be able to proceed to the next sentence",
						120, 500);

				g2.drawString(
						"3) The allotted time for typing is 60 seconds. Afterwards, your WPM score will be available",
						120, 550);

				g2.drawString("4) Watch for spacing errors (especially in the beginning)", 120, 600);

			}

			else if (gameFinished == true)

			{

				Font f1 = new Font("Times New Roman", Font.BOLD, 150);

				FontMetrics fm1 = this.getFontMetrics(f);

				g2.setFont(f1);

				g2.drawString("GAME OVER", 120, 200);

				g2.setColor(Color.GREEN);

				int wpm = (int) ((characterCount / 5) / (((double) secondsPassed) / 60));

				System.out.println("displays: " + wpm);

				g2.drawString("Words per minute: " + wpm, 10, 500);

				typingArea.setVisible(false);

				displayTimeLeft.setVisible(false);

				add(new TimeRestart());

			}

			else if (s.state != null)

			{

				g2.drawString(s.state, 200, 300);

			}

		}

	}

	class Sentence implements Runnable {

		String state = null;

		public void run() {

			try {

				Random r;

				while (((numOfCompletedSentences < 7) && (60 - secondsPassed) >= 0))

				{

					if ((state == null))

					{

						r = new Random();

						state = sentences.get(r.nextInt(40));

						typingArea.setText("");

					}

					repaint();

				}

				gameFinished = true;

			} catch (Exception e) {

			}

		}

	}

	class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e)

		{

			if (e.getActionCommand() == "Restart Game")

			{

				TypeRacerUPDATED tg = new TypeRacerUPDATED();

				tg.startGame();

				FileReader reader = null;

				try {

					reader = new FileReader("/Users/gchin20/Desktop/AP Comp Sci/TypeRacer/Sentences");

				} catch (FileNotFoundException e1) {

					// TODO Auto-generated catch block

					e1.printStackTrace();

				}

				Scanner sc = new Scanner(reader);

				tg.read(sc);

			}

			else if (e.getActionCommand() == "Press to Start") {

				startButton.setVisible(false);

				Thread t1 = new Thread(s);

				t1.start();

				add(new TimeRun());

				gameStarted = true;

			}
		}

	}

	class ListenText implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {

		}

		public void removeUpdate(DocumentEvent e) {

		}

		/*
		 * Checks if what the user typed in equals one of the falling texts If so, it
		 * will update the stats of the game and remove the matching text
		 */
		public void insertUpdate(DocumentEvent e) {
			if (typingArea.getText().equals(s.state)) {
				s.state = null;
				numOfCompletedSentences++;

				for (int i = 0; i < typingArea.getText().length(); i++) {
					if (typingArea.getText().substring(i).equals(" ")
							|| !(typingArea.getText().substring(i).equals(" "))) // increments character count if the
																					// character isn't a whitespace

					{
						characterCount++;
					}

				}
			}
		}

	}
}
