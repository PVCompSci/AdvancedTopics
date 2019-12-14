package geometrydash.gfx;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Assets {

	private static final int width=64,height=64;
	public static BufferedImage player1,player2,floor1,ground,barrierMid,barrierSL,barrierST,barrierSR,barrierSB,barrierCTL,barrierCTR,barrierCBR,barrierCBL,barrierEB,barrierEL,barrierET,barrierER,barrierC,barrierR,spike1,spike2,spike3,spike4,air,border,slab,background,floorBackground,floorLine,menu1,levelCompleteScreen,levelComplete,cubePT,cubePM,cubePB,shipPT,shipPM,shipPB,attempts,num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,endNum1,endNum2,endNum3,endNum4,endNum5,endNum6,endNum7,endNum8,endNum9,endNum0;
	
	public static void init() {
		
		SpriteSheet sheet =new SpriteSheet(ImageLoader.loadImage("/textures/SpriteSheet.png"));
		
		player1=sheet.crop(0, height*2, width, height);
		//player2=sheet.crop(width*4, height*2, width, height);
		
		air=null;
		ground=null;
		
		floor1=sheet.crop(width*7, height, width, height);
		
		barrierMid=sheet.crop(0, 0, width, height);
		barrierSL=sheet.crop(width, 0, width, height);
		barrierST=sheet.crop(width*2, 0, width, height);
		barrierSR=sheet.crop(width*3, 0, width, height);
		barrierSB=sheet.crop(width*4, 0, width, height);
		barrierCBL=sheet.crop(width*5, 0, width, height);
		barrierCTL=sheet.crop(width*6, 0, width, height);
		barrierCTR=sheet.crop(width*7, 0, width, height);
		barrierCBR=sheet.crop(0, height, width, height);
		barrierEB=sheet.crop(width, height, width, height);
		barrierEL=sheet.crop(width*2, height, width, height);
		barrierET=sheet.crop(width*3, height, width, height);
		barrierER=sheet.crop(width*4, height, width, height);
		barrierC=sheet.crop(width*5, height, width, height);
		barrierR=sheet.crop(width*6, height, width, height);

		spike1=sheet.crop(width, height*2, width, height);
		spike2=sheet.crop(width*2, height*2, width, height);
		spike3=sheet.crop(width*3, height*3, width, height);
		spike4=sheet.crop(width*4, height*3, width, height);
		border=sheet.crop(width*7, height, width, height);
		slab=sheet.crop(width*3, height*2, width, height);
		
		shipPT=sheet.crop(width*5, height*2, width, height);
		shipPM=sheet.crop(width*6, height*2, width, height);
		shipPB=sheet.crop(width*7, height*2, width, height);
		cubePT=sheet.crop(0, height*3, width, height);
		cubePM=sheet.crop(width, height*3, width, height);
		cubePB=sheet.crop(width*2, height*3, width, height);
		
		background= ImageLoader.loadImage("/textures/Background.png");
		floorBackground= ImageLoader.loadImage("/textures/Floor.png");
		menu1=ImageLoader.loadImage("/textures/Menu1.png");
		floorLine=ImageLoader.loadImage("/textures/FloorLine.png");
		attempts=ImageLoader.loadImage("/textures/Attempt.png");
		player2=ImageLoader.loadImage("/textures/playerShip.png");
		levelCompleteScreen= ImageLoader.loadImage("/textures/LevelCompleteScreen.png");
		levelComplete= ImageLoader.loadImage("/textures/LevelCompleteWords.png");

		
		num1=ImageLoader.loadImage("/textures/num1.png");
		num2=ImageLoader.loadImage("/textures/num2.png");
		num3=ImageLoader.loadImage("/textures/num3.png");
		num4=ImageLoader.loadImage("/textures/num4.png");
		num5=ImageLoader.loadImage("/textures/num5.png");
		num6=ImageLoader.loadImage("/textures/num6.png");
		num7=ImageLoader.loadImage("/textures/num7.png");
		num8=ImageLoader.loadImage("/textures/num8.png");
		num9=ImageLoader.loadImage("/textures/num9.png");
		num0=ImageLoader.loadImage("/textures/num0.png");
		
		endNum1=ImageLoader.loadImage("/textures/endNum1.png");
		endNum2=ImageLoader.loadImage("/textures/endNum2.png");
		endNum3=ImageLoader.loadImage("/textures/endNum3.png");
		endNum4=ImageLoader.loadImage("/textures/endNum4.png");
		endNum5=ImageLoader.loadImage("/textures/endNum5.png");
		endNum6=ImageLoader.loadImage("/textures/endNum6.png");
		endNum7=ImageLoader.loadImage("/textures/endNum7.png");
		endNum8=ImageLoader.loadImage("/textures/endNum8.png");
		endNum9=ImageLoader.loadImage("/textures/endNum9.png");
		endNum0=ImageLoader.loadImage("/textures/endNum0.png");
	}
	
	public static BufferedImage getNum(int n) {
		if(n==0)
			return num0;
		else if(n==1)
			return num1;
		else if(n==2)
			return num2;
		else if(n==3)
			return num3;
		else if(n==4)
			return num4;
		else if(n==5)
			return num5;
		else if(n==6)
			return num6;
		else if(n==7)
			return num7;
		else if(n==8)
			return num8;
		else if(n==9)
			return num9;
		else
			return num0;
	}
	
	public static BufferedImage getEndNum(int n) {
		if(n==0)
			return endNum0;
		else if(n==1)
			return endNum1;
		else if(n==2)
			return endNum2;
		else if(n==3)
			return endNum3;
		else if(n==4)
			return endNum4;
		else if(n==5)
			return endNum5;
		else if(n==6)
			return endNum6;
		else if(n==7)
			return endNum7;
		else if(n==8)
			return endNum8;
		else if(n==9)
			return endNum9;
		else
			return endNum0;
	}
}
