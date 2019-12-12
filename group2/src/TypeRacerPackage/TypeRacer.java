package TypeRacerPackage;

import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.paint.*;

public class TypeRacer {
	File location;
	TextListener words = null;
	String currentString = null;
	int on;
	FlowPane contentA = new FlowPane(new Label("ERROR"));
	Stage gameStage;
	ScrollPane contentB = new ScrollPane();
	VBox content = new VBox(2);
	Scene gameScene;

	private int secondsPassed, characterCount, mode;


	TypeRacer(int chosenOpt, File filename, int count) {
		mode = chosenOpt; //format of words
		location = filename; //file
		words = new TextListener(filename); //reads file
		getText(); //gets lines of text from file
		on = 0;
		gameStage = new Stage();
		contentA.setAlignment(Pos.CENTER);
		characterCount = count; //total character count in the file
		secondsPassed = 0;

		if (mode == 2) 
		{
			contentA.setAlignment(Pos.CENTER_LEFT);
		}
		content.setAlignment(Pos.TOP_CENTER);
		content.getChildren().add(new Label("Typing Game"));
		gameScene = new Scene(content, 600, 300);
		gameStage.setScene(gameScene);
		if (mode == 0 || mode == 1) 
		{
			content.getChildren().add(contentA);
		}
		if (mode == 2) 
		{
			contentB.setHbarPolicy(ScrollBarPolicy.NEVER);
			contentB.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			content.getChildren().add(contentB);
			contentB.setContent(contentA);
		}
		gameScene.setOnKeyTyped(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {

				String code = e.getCharacter().toString();
				checkLet(code);
			}
		});
		gameStage.show();

		t.scheduleAtFixedRate(task, 1000, 1000); //starts timer (goes by 1 sec at a time)
	}

	//timer
	Timer t = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {
			secondsPassed++;
			System.out.println("Seconds passed: " + secondsPassed);
		}
	};

	public void checkLet(String let) {
		char w = let.charAt(0);
		
		//checks first char
		if (let.charAt(0) != (currentString.charAt(on))) 
		{
			drawWord(false);
		}
		//checks the rest of the text
		else 
		{
			on++;
			drawWord(true);
			if (on == currentString.length()) //checks if the char is the last char in the file
			{
				getText();
			}
		}
	}

	public void drawWord(boolean correct) {
		int p = 0;
		String word = currentString;
		contentA.getChildren().clear();
		for (String s : word.split("")) {
			Label l = new Label(s);
			if (p < on) {
				l.setTextFill(Color.GREEN);
				l.setUnderline(true);
			}
			if (p == on) 
			{
				if (correct) 
				{
					l.setTextFill(Color.BLUE); //char that the player is on
				} 
				else 
				{
					l.setTextFill(Color.RED); //char that the player got incorrect
					l.setUnderline(true);
				}
			}
			if (p > on) {
				l.setTextFill(Color.DARKGRAY); //chars that the player didn't get to yet
			}
			l.setAlignment(Pos.CENTER);
			l.setFont(new Font("Times New Roman", 24)); //font and size
			contentA.getChildren().add(l);
			p++;
		}

	}

	public void getText() {
		on = 0;
		System.out.println("test");
		switch (mode) {
			case 0:
				currentString = words.firstOpt(); //word by word
				break;
			case 1:
				currentString = words.secondOpt(); //one line of scrolling text
				break;
			case 2:
				currentString = words.thirdOpt(); //entire file
				break;

			default:
				System.out.println("Error");
				break;
		}
		
		//if there is no more text, the game screen closes and wpm is calculated
		if (currentString == null) 
		{
			t.cancel(); //stops timer
			int wpm = (int) ((characterCount / 5) / (((double) secondsPassed) / 60)); //equation for WPM
			System.out.println("WPM: " + wpm);
			TypeMain.primaryStage.show(); //goes back to main screen
			gameStage.close(); //closes game screen
		}
		else 
		{
			drawWord(true);
		}
	}
	

	
}
