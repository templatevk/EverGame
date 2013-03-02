package pp.game.scene.hud;

import org.andengine.entity.modifier.*;
import org.andengine.entity.text.*;
import org.andengine.opengl.font.*;
import org.andengine.util.*;

import pp.game.*;
import pp.game.observers.*;
import pp.game.utils.geometry.*;

class ScorePointsIndicator extends Text {
	private static final float SCALE_MODIFIER_DURATION = 0.5f;
	private static final int MAX_CHARACTERS_TO_DRAW = 6;
	private static final int X = (int)SceneLayoutUtils.WIDTH / 2;
	private static final int Y = 10;
	
	private class ScorePointsObserver implements IObserver<Integer> {
		public ScorePointsObserver() {
			
		}
		
		@Override
		public void onChanged(Integer score) {					
			setText(score.toString());
			registerEntityModifier(new SequenceEntityModifier(
					new ScaleModifier(SCALE_MODIFIER_DURATION, 1, 1.4f),
					new ScaleModifier(SCALE_MODIFIER_DURATION, 1.4f, 1)));
		}
	}
	
	private ScorePointsIndicator(IFont font) {
		super(X, Y, font, "", MAX_CHARACTERS_TO_DRAW, new TextOptions(HorizontalAlign.RIGHT), 
				Game.getGameActivity().getVertexBufferObjectManager());
	}
	
	static ScorePointsIndicator getScoreIndicator() {
		Font font = GameHUDSettings.getFont(android.graphics.Color.argb(200, 0, 0, 255), FontSize.SMALL);
		ScorePointsIndicator indicator = new ScorePointsIndicator(font);
		SceneLayoutUtils.adjustGameHUDText(indicator);
		Game.getGameInstance().addObservable(indicator.new ScorePointsObserver());
		return indicator;
	}
}
