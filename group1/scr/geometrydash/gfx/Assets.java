package geometrydash.gfx;
import java.awt.image.BufferedImage;

public class Assets {

	private static final int width=32,height=32;
	public static BufferedImage player1,player2,floor,barrier,spike,air,border;
	
	public static void init() {
		
		SpriteSheet sheet =new SpriteSheet(ImageLoader.loadImage("/textures/SpriteSheetGeometryDash.png"));
		
		player1=sheet.crop(0, 0, width, height);
		player2=sheet.crop(width, 0, width, height);
		floor=sheet.crop(width*2, 0, width, height);
		barrier=sheet.crop(width*3, 0, width, height);
		spike=sheet.crop(width*4, 0, width, height);
		air=sheet.crop(width*5, 0, width, height);
		//air=null;
		border=sheet.crop(width*6, 0, width, height);
	}
}
