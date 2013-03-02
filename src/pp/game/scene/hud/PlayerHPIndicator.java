package pp.game.scene.hud;

import org.andengine.entity.modifier.*;
import org.andengine.entity.text.*;
import org.andengine.opengl.font.*;
import org.andengine.util.color.*;

import pp.game.*;
import pp.game.audio.*;
import pp.game.entities.*;
import pp.game.entities.IDieableEntity.HPState;
import pp.game.level.*;
import pp.game.observers.*;
import pp.game.scene.*;
import pp.game.utils.geometry.*;

class PlayerHPIndicator extends Text {	
	private static final int MAX_CHARACTERS_TO_DRAW = 7;
	private static final int X = 10;
	private static final int Y = 10;
	
	private static final float SCALE_MODIFIER_DURATION = 1;
	
	private Color fullHPColor;
	private Color mediumHPColor;
	private Color lowHPColor;
	
	private class PlayerHPIndicatorObserver implements IPreparable, IResetable, IDieableObserver {
		private HPState prevHPState;
		private HPState currentHPState;
		private ILevel level;
		private boolean prepared = false;
		
		public PlayerHPIndicatorObserver() {
			Game.getGameInstance().addPreparable(this);
		}
		
		@SuppressWarnings("incomplete-switch")
		private void updateIndicator(IDieableEntity entity) {
			currentHPState = Player.getInstance().getHPState();
			if (currentHPState != prevHPState) {
				switch (currentHPState) {
				case HIGH:
					setColor(fullHPColor);
					GameScene.getInstance().setCurrentMusic(level.getMusic());
					break;
				case MEDIUM:
					setColor(mediumHPColor);
					GameScene.getInstance().setCurrentMusic(level.getMusic());
					break;
				case LOW:
					setColor(lowHPColor);
					GameScene.getInstance().setCurrentMusic(GameMusicType.LOW_HP);
					break;
				}
			}
			
			setText(String.valueOf((int)entity.getCurrentHP()));
		}
		
		@Override
		public Priority getPriority() {
			return Priority.LOW;
		}
		
		@Override
		public void prepare(ILevel level) {
			this.level = level;
			onChanged(Player.getInstance());
			prevHPState = null;
			updateIndicator(Player.getInstance());
			prepared = true;
		}
		
		@Override
		public void reset() {
			prepared = false;
		}
		
		@Override
		public void onChanged(IDieableEntity observable) {
			if (!prepared) {
				return;
			}
					
			updateIndicator(observable);			
			prevHPState = currentHPState;
		}
	}
	
	private PlayerHPIndicator(IFont font) {
		super(X, Y, font, "", MAX_CHARACTERS_TO_DRAW, Game.getGameActivity()
				.getVertexBufferObjectManager());
		
		fullHPColor = new Color(0, 0.8f, 0);
		mediumHPColor = new Color(0.8f, 0.8f, 0);
		lowHPColor = new Color(0.8f, 0, 0);
		
		registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
						new ScaleModifier(SCALE_MODIFIER_DURATION, 0.8f, 1.2f),
						new ScaleModifier(SCALE_MODIFIER_DURATION, 1.2f, 0.8f))));
	}
	
	static PlayerHPIndicator getHpIndicator() {
		Font font =	GameHUDSettings.getFont(android.graphics.Color.WHITE, FontSize.SMALL);		
		PlayerHPIndicator indicator = new PlayerHPIndicator(font);
		SceneLayoutUtils.adjustGameHUDText(indicator);
		Player.getInstance().addObservable(indicator.new PlayerHPIndicatorObserver());
		return indicator;
	}
}
