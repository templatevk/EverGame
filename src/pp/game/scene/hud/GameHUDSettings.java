package pp.game.scene.hud;

import org.andengine.opengl.font.*;

import pp.game.*;
import android.graphics.*;

public class GameHUDSettings {
	private GameHUDSettings() {
		
	}
	
	public static Font getFont(int color, FontSize size) {
		Font font = null;
		
		switch (size) {
		case MEDIUM:
			break;
		case SMALL:
			font = FontFactory.create(Game.getGameActivity().getFontManager(), 
					Game.getGameActivity().getTextureManager(), 
					GameHUD.SMALL_TEXTURE_WIDTH, GameHUD.SMALL_TEXTURE_HEIGH, 
					Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL),
					GameHUD.SMALL_FONT_SIZE, color);
			break;
		}
		
		font.load();
		return font;
	}
	
	public static Font getFont(int color) {
		Font font = FontFactory.create(Game.getGameActivity().getFontManager(), 
				Game.getGameActivity().getTextureManager(), 
				GameHUD.TEXTURE_WIDTH, GameHUD.TEXTURE_HEIGH, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL),
				GameHUD.FONT_SIZE, color);
		font.load();
		return font;
	}
}
