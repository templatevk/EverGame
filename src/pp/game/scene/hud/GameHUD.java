package pp.game.scene.hud;

import org.andengine.engine.camera.hud.*;

import pp.game.*;
import pp.game.level.*;
import pp.game.utils.geometry.*;

public class GameHUD extends HUD implements IPreparable, IPausable, IResetable {	
	public static final float AVERAGE_LETTER_WIDTH_COEF = 0f;
	private static final float FONT_TO_TEXTURE_HEIGHT_COEF = 1.2f;
	private static final float HEIGHT_FOR_FONT 	= 320f; 
	private static final float WIDTH_FOR_FONT 	= 480f;
	
	private static final float FONT_ADJUST = (SceneLayoutUtils.WIDTH + SceneLayoutUtils.HEIGHT)
			/ (WIDTH_FOR_FONT + HEIGHT_FOR_FONT);
	public static final int SMALL_FONT_SIZE = (int)(30 * FONT_ADJUST);
	public static final int FONT_SIZE = (int)(100 * FONT_ADJUST);
	
	static final int SMALL_TEXTURE_WIDTH = (int)(256 * (SceneLayoutUtils.WIDTH / WIDTH_FOR_FONT));
	static final int SMALL_TEXTURE_HEIGH = (int)(32 * FONT_TO_TEXTURE_HEIGHT_COEF
			* (SceneLayoutUtils.HEIGHT / HEIGHT_FOR_FONT));
	
	static final int TEXTURE_WIDTH = (int)(1024 * (SceneLayoutUtils.WIDTH / WIDTH_FOR_FONT));
	static final int TEXTURE_HEIGH = (int)(128 * FONT_TO_TEXTURE_HEIGHT_COEF 
			* (SceneLayoutUtils.HEIGHT / HEIGHT_FOR_FONT));
	
	
	private static final GameHUD INSTANCE = new GameHUD();
	
	private GameHUD() {
		IGame game = Game.getGameInstance();
		game.addPreparable(this);
		game.addResetable(this);
		game.addPauseable(this);
		
		attachChild(PlayerHPIndicator.getHpIndicator());
		attachChild(ScorePointsIndicator.getScoreIndicator());
		attachChild(WeaponMagazineIndicator.getMagazineIndicator());
		setVisible(false);
	}
	
	public static GameHUD getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void prepare(ILevel level) {
		setVisible(true);
	}
	
	@Override
	public void pause() {
		setVisible(false);
	}
	
	@Override
	public void resume() {
		setVisible(true);		
	}
	
	@Override
	public void reset() {
		setVisible(false);
	}
	
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}
}
