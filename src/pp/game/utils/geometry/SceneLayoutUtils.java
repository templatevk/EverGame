package pp.game.utils.geometry;

import java.util.*;

import org.andengine.engine.camera.*;
import org.andengine.entity.scene.background.*;
import org.andengine.entity.shape.*;
import org.andengine.entity.sprite.*;
import org.andengine.entity.text.*;

import pp.game.*;
import pp.game.entities.*;
import pp.game.scene.hud.*;

@SuppressWarnings("serial")
public class SceneLayoutUtils {		
	public static final float DEFAULT_PLAYER_SPEED;
	public static final float VELOCITY_ADJUST_COEF; 
	public static final float BACKGROUND_MAX_X;
	public static final float BACKGROUND_MAX_Y;
	
	public static final boolean IS_HD;
	
	public static final float BACKGROUND_SCALE_COEF 					= 1.5f;
	
	private static final float DEFAULT_WIDTH							= 320;
	private static final float DEFAULT_HEIGHT							= 480;
	private static final float HD_REQUIRED_SIZE						= 1200;
	
	private static final int WEAPON_Z_INDEX 							= 5;
	private static final int BONUS_Z_INDEX							= 5;
	private static final int PLAYER_Z_INDEX	 						= 4;
	private static final int MONSTER_Z_INDEX 							= 4;
	private static final int BULLET_Z_INDEX 							= 4;
	private static final int DEATH_SPRITE_Z_INDEX 					= 3;
	private static final int BLOOD_Z_INDEX 							= 2;
	private static final int BACKGROUND_Z_INDEX 						= 0;
	
	private static final float BLOOD_ALPHA							= 0.5f;	 
	
	private static final float PLAYER_CONTROL_MARGIN_Y 				= 0.00f;
	private static final float PLAYER_CONTROL_MARGIN_X 				= 0.00f;
	private static final float PLAYER_CONTROL_KNOB_DISTANCE_COEF		= 0.50f;
	
	private static final float GAME_HUD_TEXT_SIZE_COEF				= 0.10f;
	private static final float BONUS_SIZE_COEF						= 0.17f;
	private static final float BLOOD_SIZE_COEF						= 0.22f;
	private static final float PLAYER_CONTROL_SIZE_COEF 				= 0.30f;
	private static final float PLAYER_SIZE_COEF 						= 0.22f;
	private static final float MENU_ITEM_HEIGHT_COEF 					= 0.20f;	
	private static final float MENU_ITEM_VMARGIN_COEF 				= 0.03f; 
	
	public static final float WIDTH;
	public static final float HEIGHT;
	public static final float MIN_SIZE;
	
	private static final Random rand = new Random();
	
	private static class CoefsHolder {
		private static HashMap<MonsterType, Float> monsterSizeCoefs = new HashMap<MonsterType, Float>() {{
			put(MonsterType.RUNNER, 0.22f);
			put(MonsterType.SPIDER, 0.22f);
			put(MonsterType.ZOMBIE, 0.22f);
		}};		
		private static HashMap<BonusType, Float> bonusSizeCoefs = new HashMap<BonusType, Float>() {{
			put(BonusType.HP_LARGE, 0.10f);
			put(BonusType.HP_MEDIUM, 0.10f);
			put(BonusType.HP_SMALL, 0.10f);
			put(BonusType.SPEED, 0.10f);
			put(BonusType.BULLETS, 0.10f);
		}};
	}	
	
	
	static {
		Camera camera = Game.getGameActivity().getEngine().getCamera();
		WIDTH = camera.getWidth();
		HEIGHT = camera.getHeight();
		IS_HD = (WIDTH + HEIGHT) >= HD_REQUIRED_SIZE;
		MIN_SIZE = Math.min(WIDTH, HEIGHT);
		BACKGROUND_MAX_X = WIDTH * BACKGROUND_SCALE_COEF;
		BACKGROUND_MAX_Y = HEIGHT * BACKGROUND_SCALE_COEF;
		VELOCITY_ADJUST_COEF = (WIDTH + HEIGHT) / (DEFAULT_WIDTH + DEFAULT_HEIGHT);
		DEFAULT_PLAYER_SPEED = 3f * VELOCITY_ADJUST_COEF;
	}
	
	private SceneLayoutUtils() {
		
	}
	
	private static float getScaleCoef(Sprite sprite, float sizeCoef) {
		return sizeCoef / ((sprite.getWidth() + sprite.getHeight()) / 2f / MIN_SIZE);
	}
	
	private static float getScaleCoef(float size, float sizeCoef) {
		return sizeCoef / (size / MIN_SIZE);
	}
	
	private static float getScaleCoef(float width, float height, float sizeCoef) {
		return sizeCoef / ((width + height) / 2f / MIN_SIZE);
	}
	
	public static RepeatingSpriteBackground adjustLevelBackground(RepeatingSpriteBackground background) {
		background.getSprite().setZIndex(BACKGROUND_Z_INDEX);		
		return background;
	}
	
	public static Sprite adjustSpriteToScreenSize(Sprite sprite) {
		sprite.setWidth(WIDTH);
		sprite.setHeight(HEIGHT);
		return sprite;
	}
	
	public static Point getRandomBoundPoint(float entityWidth, float entityHeight) {
		float x = rand.nextBoolean() ? - entityWidth : BACKGROUND_MAX_X + entityWidth;
		float y = rand.nextBoolean() ? - entityHeight : BACKGROUND_MAX_Y + entityHeight;
		if (rand.nextBoolean()) {
			y = rand.nextInt((int)(BACKGROUND_MAX_Y + entityHeight));
		} else {
			x = rand.nextInt((int)(BACKGROUND_MAX_X + entityWidth));
		}
		return new Point(x, y);
	}
	
	public static Bullet adjustBullet(Bullet bullet) {
		bullet.getShape().setZIndex(BULLET_Z_INDEX);
		return bullet;
	}
	
	public static Monster adjustMonster(Monster monster) {
		float sizeScaleCoef = CoefsHolder.monsterSizeCoefs.get(monster.getMonsterType());		
		float size = monster.getAliveSprite().getWidth();
		float scaleCoef = getScaleCoef(size, sizeScaleCoef);
		monster.getAliveSprite().setScale(scaleCoef);
		monster.getDeadSprite().setScale(scaleCoef);
		
		Point p = getRandomBoundPoint(monster.getAliveSprite().getWidth(), 
				monster.getAliveSprite().getHeight());
		
		monster.getAliveSprite().setPosition(p.x, p.y);
		monster.getAliveSprite().setZIndex(MONSTER_Z_INDEX);
		monster.getDeadSprite().setZIndex(DEATH_SPRITE_Z_INDEX);
		
		return monster;
	}
	
	public static Player adjustPlayer(Player player) {
		player.getAliveSprite().setScale(getScaleCoef(player.getAliveSprite(), PLAYER_SIZE_COEF));
		player.getAliveSprite().setZIndex(BACKGROUND_Z_INDEX - 1);
		player.getLegsSprite().setScale(getScaleCoef(player.getLegsSprite(), PLAYER_SIZE_COEF));
		player.getLegsSprite().setZIndex(PLAYER_Z_INDEX);
		player.getDeadSprite().setScale(getScaleCoef(player.getDeadSprite(), PLAYER_SIZE_COEF));
		player.getDeadSprite().setZIndex(DEATH_SPRITE_Z_INDEX);
		return player;
	}	
	
	public static Weapon adjustWeapon(Weapon weapon) {
		Sprite sprite = weapon.getSprite();
		sprite.setScale(getScaleCoef(sprite, PLAYER_SIZE_COEF));	
		sprite.setPosition(Player.getInstance().getAliveSprite());
		sprite.setZIndex(WEAPON_Z_INDEX);
		return weapon;
	}	
	
	public static PlayerControl adjustPlayerControl(PlayerControl control) {		
		float scaleCoef = getScaleCoef(control.getBaseWidth(),
				control.getBaseHeight(), PLAYER_CONTROL_SIZE_COEF);
		control.getControlBase().setScale(scaleCoef);
		control.getControlKnob().setScale(scaleCoef);		
		
		float xBase = 0;
		float yBase = 0;
		float xKnob = 0;
		float yKnob = 0;
		switch (control.getType()) {
		case MOVE_CONTROL:
			xBase = control.getKnobWidth() * scaleCoef * PLAYER_CONTROL_KNOB_DISTANCE_COEF 
					+ WIDTH * PLAYER_CONTROL_MARGIN_X
					- (control.getBaseWidth() - control.getBaseWidth() * scaleCoef) / 2f;
			break;
		case SHOOT_CONTROL:
			xBase = WIDTH - (control.getKnobWidth() * PLAYER_CONTROL_KNOB_DISTANCE_COEF 
					+ control.getBaseWidth()) * scaleCoef
					- (control.getBaseWidth() - control.getBaseWidth() * scaleCoef) / 2f
					- WIDTH * PLAYER_CONTROL_MARGIN_X;
			break;
		}

		yBase = HEIGHT - (control.getBaseHeight() + control.getKnobHeight() 
				* PLAYER_CONTROL_KNOB_DISTANCE_COEF) * scaleCoef 
				- (control.getBaseHeight() - control.getBaseHeight() * scaleCoef) / 2f
				- HEIGHT * PLAYER_CONTROL_MARGIN_Y;		
		xKnob = xBase + (control.getBaseWidth() - control.getKnobWidth()) / 2f;
		yKnob = yBase + (control.getBaseHeight() - control.getKnobHeight()) / 2f;
		
		control.getControlBase().setPosition(xBase, yBase);
		control.getControlKnob().setPosition(xKnob, yKnob);
		control.refreshControlKnobPosition();
		
		return control;
	}
	
	public static RectangularShape adjustToCenteredListItem(RectangularShape item, 
			int menuItemVPos, int menuItemsCount) {
		float menuItemVPosF = menuItemVPos;
		float menuItemsCountF = menuItemsCount;
		float menuItemsVMargin = HEIGHT * MENU_ITEM_VMARGIN_COEF;
		float scaleCoef = getScaleCoef(item.getHeight(), MENU_ITEM_HEIGHT_COEF);
		item.setScale(scaleCoef);
		
		float heightBase = (HEIGHT - (item.getHeight() * scaleCoef * menuItemsCountF
				+ menuItemsVMargin * (menuItemsCount == 1 ? 0 : menuItemsCount - 1))) / 2f;
		float y = heightBase + (item.getHeight() * scaleCoef + menuItemsVMargin) 
				* menuItemVPosF;
		float x = (WIDTH - item.getWidth()) / 2f;
		item.setX(x);
		item.setY(y);
		return item;
	}
	
	public static RectangularShape adjustToLeftCenteredListItem(RectangularShape item, 
			int menuItemVPos, int menuItemsCount) {
		float menuItemVPosF = menuItemVPos;
		float menuItemsCountF = menuItemsCount;
		float menuItemsVMargin = HEIGHT * MENU_ITEM_VMARGIN_COEF;
		float scaleCoef = getScaleCoef(item.getHeight(), MENU_ITEM_HEIGHT_COEF);
		item.setScale(scaleCoef);
		
		float heightBase = (HEIGHT - (item.getHeight() * scaleCoef * menuItemsCountF
				+ menuItemsVMargin * (menuItemsCount == 1 ? 0 : menuItemsCount - 1))) / 2f;
		float y = heightBase + (item.getHeight() * scaleCoef + menuItemsVMargin) 
				* menuItemVPosF;
		float x = WIDTH / 3f;
		item.setX(x);
		item.setY(y);
		return item;
	}
	
	public static Bonus adjustBonus(Bonus bonus) {
		Float sizeScaleCoef = CoefsHolder.bonusSizeCoefs.get(bonus.getBonusType());
		bonus.getShape().setScale(getScaleCoef(bonus.getShape(), 
				sizeScaleCoef == null ? BONUS_SIZE_COEF : sizeScaleCoef));
		bonus.getShape().setZIndex(BONUS_Z_INDEX);
		return bonus;
	}
	
	public static Sprite adjustBloodSprite(Sprite sprite) {
		sprite.setScale(getScaleCoef(sprite, BLOOD_SIZE_COEF));
		sprite.setZIndex(BLOOD_Z_INDEX);
		sprite.setAlpha(BLOOD_ALPHA);
		return sprite;
	}
	
	public static boolean isInScene(IAreaShape shape) {
		if (shape.getX() < 0 || shape.getX() > SceneLayoutUtils.BACKGROUND_MAX_X
				|| shape.getY() < 0 || shape.getY() > SceneLayoutUtils.BACKGROUND_MAX_Y) {
			return false;
		}
		return true;
	}
	
	public static Text adjustGameHUDText(Text text) {
		text.setScale(getScaleCoef(text.getHeight(), GAME_HUD_TEXT_SIZE_COEF));
		return text;
	}
}
