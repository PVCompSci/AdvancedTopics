package TypeRacerPackage;

import javafx.application.Application;

import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.io.*;
import javafx.scene.text.Font;
import javafx.scene.paint.*;

public class TypeMain extends Application {
	int mode = 0;
	File filename = new File(this.getClass().getResource("test.txt").getFile());

	static Stage primaryStage;
	TextListener getwords = null;

	TextListener getWords = new TextListener(filename);
	int characters = getWords.getCharacterCount();
	
	public static void main(String[] args) 
	{
		launch(args); //starts game
	}

	@Override
	public void start(Stage ppp) throws Exception {
		primaryStage = ppp;
		BorderPane menuLay = new BorderPane(); // Main Menu Scene
		Scene menu = new Scene(menuLay, 600, 550);
		menuLay.setStyle("-fx-background-color: #E8E8E8");
		// Top of the menu
		Label mainTop = new Label("TypeRacer");
		mainTop.setFont(new Font("Times New Roman", 24));
		mainTop.setTextFill(Color.BLUE);
		mainTop.setPadding(new Insets(15, 12, 15, 12));
		StackPane title = new StackPane();
		title.getChildren().add(mainTop);
		menuLay.setTop(title);

		//start button
		Button startGameBut = new Button("Start Game");
		startGameBut.setOnAction(e -> {
			primaryStage.hide(); //when the start game button is clicked, the main screen is hidden and the main pops up
			TypeRacer game = new TypeRacer(mode, filename, characters);
		});
		StackPane startButPane = new StackPane();
		startButPane.setPadding(new Insets(0, 120, 0, 0));
		startButPane.getChildren().add(startGameBut);

		//Format
		VBox settingsBox = new VBox(5);
		Label setL1 = new Label("Enter Text File Location");
		TextField enterFileLocation = new TextField();
		enterFileLocation.setPromptText("ex. C:/Users/exampleFile/example/words.txt"); //example file input
		enterFileLocation.setOnAction(e -> {
			String a = new String(enterFileLocation.getText());
			File b = new File(a);
			File c = new File(a + ".txt");
			System.out.println(a + "\n" + b.exists() + "\n" + c.exists());
			if (b.exists() == false && c.exists() == false) 
			{
				startGameBut.setDisable(true);
				startGameBut.setTextFill(Color.RED);
				startGameBut.setText("Invalid File Location");
			} 
			else 
			{
				startGameBut.setDisable(false);
				startGameBut.setTextFill(Color.BLACK);
				startGameBut.setText("Start Game");
				if (b.exists() == true) {
					filename = b;
				} else {
					filename = c;
				}

			}
		});

		//Format options
		final ToggleGroup modeButList = new ToggleGroup();
		RadioButton mode0 = new RadioButton("Word by Word");
		mode0.setToggleGroup(modeButList);
		mode0.setOnAction(e -> mode = 0);
		RadioButton mode1 = new RadioButton("Line by Line");
		mode1.setToggleGroup(modeButList);
		mode1.setOnAction(e -> mode = 1);
		RadioButton mode2 = new RadioButton("Whole File  ");
		mode2.setToggleGroup(modeButList);
		mode2.setOnAction(e -> mode = 2);
		mode0.setSelected(true);
		VBox butListMode = new VBox();
		butListMode.getChildren().addAll(mode0, mode1, mode2);
		settingsBox.getChildren().addAll(setL1, enterFileLocation, new Label("Display Settings"), butListMode);

		//Leaderboards
		VBox leaderboard = new VBox(2);
		// Left Pane
		VBox navigation = new VBox(2);
		navigation.setPadding(new Insets(10, 10, 10, 10));
		menuLay.setLeft(navigation);
		
		
		// Main Page Button
		Button mainPageBut = new Button("Main Page");
		mainPageBut.setPrefSize(100, 20);
		mainPageBut.setOnAction(e -> {
			menuLay.setCenter(startButPane);
		});
		navigation.getChildren().add(mainPageBut);

		Button displayBut = new Button("Format");
		displayBut.setPrefSize(100, 20);
		displayBut.setOnAction(e -> {
			menuLay.setCenter(settingsBox);
		});
		navigation.getChildren().add(displayBut);

		Button leadBtn = new Button("Leaderboards");
		leadBtn.setPrefSize(100, 20);
		leadBtn.setOnAction(e -> {
			// updateHistory();
			menuLay.setCenter(leaderboard);
		});
		navigation.getChildren().add(leadBtn);

		menuLay.setCenter(startButPane);
		primaryStage.setScene(menu);
		primaryStage.show();
	}
	
	//jdskl;fjkds
}
