package TypeRacerPackage;
import javafx.application.Application;


import javafx.event.*;
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
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.scene.paint.*;



public class TypeRacer {
	int mode;
	File location;
	TextListener words = null;
	String currentString = null;
	int on;
	FlowPane contenta = new FlowPane(new Label("ERROR"));
	Stage gameStage;
	ScrollPane contentb = new ScrollPane();
	VBox content = new VBox(2);
	Scene gameScene;
	
	private int secondsPassed, characterCount;
	
	TypeRacer(int chosenOpt,File filename)
	{
		mode =chosenOpt;
		location = filename;
		words = new TextListener(filename);
		getText();
		on = 0;
		gameStage = new Stage();
		contenta.setAlignment(Pos.CENTER);
		characterCount=0;
		secondsPassed=0;
		
		
		if (mode==2)
		{
			contenta.setAlignment(Pos.CENTER_LEFT);
		}
		content.setAlignment(Pos.TOP_CENTER);
		content.getChildren().add(new Label("Typing Game"));
		gameScene = new Scene(content, 600, 300);
		gameStage.setScene(gameScene);
		if ( mode == 0 || mode == 1 )
		{
			content.getChildren().add(contenta);
		}
		if ( mode == 2 )
		{
			contentb.setHbarPolicy(ScrollBarPolicy.NEVER);
			contentb.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			content.getChildren().add(contentb);
			contentb.setContent(contenta);
		}
		gameScene.setOnKeyTyped(
			new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
					
                    String code = e.getCharacter().toString();
                    checkLet(code);
                    if(currentString==null)
                    {
                    	
                    }

                }
            }); 
		gameStage.show();
		
		t.scheduleAtFixedRate(task, 1000, 1000);		
	}
	
	Timer t = new Timer();
	TimerTask task = new TimerTask() {
		public void run()
		{
			secondsPassed++;
			System.out.println("Seconds passed: "+secondsPassed);
		}
	};
	
	public void checkLet(String let)
	{
		char w = let.charAt(0);
		if (let.charAt(0)!=(currentString.charAt(on)))
		{
			drawWord(false);
		}
		else
		{

			on++;
			drawWord(true);
			if(on==currentString.length())
			{
				getText();
			}
		}
	}
	public void drawWord(boolean correct)
	{
		int p = 0;
		String word = currentString;
		contenta.getChildren().clear();
	    for(String s : word.split(""))
	    {
	        Label l = new Label(s);
	        if (p<on)
	        {
	        l.setTextFill(Color.GREEN);
	        }
	        if (p==on)
	        {
	        	if (correct)
	        	{
	        		l.setTextFill(Color.BLUE);
	        		
	        	}
	        	else
	        	{
	        		l.setTextFill(Color.RED);
	        	}
	        }
	        if (p>on)
	        {
	        	l.setTextFill(Color.DARKGRAY);
	        }
	        l.setPrefWidth(20 );
	        l.setAlignment(Pos.CENTER );
	        l.setFont( new Font( "Arial",  24 ) );
	        contenta.getChildren().add( l );
			p++;
	    }
	    
	}
	public void getText() 
	{
		on=0;
		System.out.println("test");
		switch(mode)
		{
			case 0: 
				currentString=words.firstOpt();
			
				break;
			case 1: 
				currentString=words.secondOpt();
				
				break;
			
			case 2: 
				currentString=words.thirdOpt();

				break;
			
			default: 
				System.out.println("Error");
				break;
		}
		if (currentString==null)
		{
			TypeMain.primaryStage.show();
			gameStage.close();
			t.cancel();
			
			
			TypeMain t = new TypeMain();
			
			//get character count from file being read from
			int wpm=(int)((characterCount/5)/(((double)secondsPassed)/60));
			
		}
		else
		{
			drawWord(true);
		}
	}
}