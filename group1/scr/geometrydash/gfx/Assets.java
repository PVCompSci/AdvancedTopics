package geometrydash.gfx;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Assets {

	private static final int width=64,height=64;
	public static BufferedImage player1,player2,floor1,ground,barrierMid,barrierSL,barrierST,barrierSR,barrierSB,barrierCTL,barrierCTR,barrierCBR,barrierCBL,barrierEB,barrierEL,barrierET,barrierER,barrierC,barrierR,spike1,spike2,spike3,spike4,air,border,slab,background,floorBackground,menu1,cubePT,cubePM,cubePB,shipPT,shipPM,shipPB;
	
	public static void init() {
		
		SpriteSheet sheet =new SpriteSheet(ImageLoader.loadImage("/textures/SpriteSheet.png"));
		
		player1=sheet.crop(0, height*2, width, height);
		player2=sheet.crop(width*4, height*2, width, height);
		
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
	}
}
