package pp.game.scene;

import java.util.*;

import org.andengine.entity.scene.background.*;
import org.andengine.entity.scene.menu.*;
import org.andengine.entity.scene.menu.item.*;
import org.andengine.entity.sprite.*;
import org.andengine.entity.text.*;
import org.andengine.opengl.font.*;
import org.andengine.opengl.vbo.*;
import org.andengine.util.*;

import pp.game.*;
import pp.game.scene.hud.*;
import pp.game.textures.*;
import pp.game.utils.*;
import pp.game.utils.geometry.*;

public class HighScoresMenuScene extends MenuScene {
	private static final int PLACES_COUNT = 3;
	private static final int MAX_CHARACTERS_TO_DRAW = 7;
	
	private Text firstText;
	private Text secondText;
	private Text thirdText;
	
	private IChildClickListener listener;
	
	public HighScoresMenuScene(IChildClickListener listener) {
		super(Game.getGameActivity().getEngine().getCamera());
		this.listener = listener;
		
		TextureHolder holder = TextureHolder.getInstance();
		VertexBufferObjectManager manager = Game.getGameActivity().getVertexBufferObjectManager();
		
		setBackground(new SpriteBackground(SceneLayoutUtils.adjustSpriteToScreenSize(
				new Sprite(0, 0, holder.getTexture(MenuTextureType.MAIN_MENU_BACKGROUND),
				manager))));
		
		Font firstFont = GameHUDSettings.getFont(android.graphics.Color.rgb(220, 0, 0));
		Font secondFont = GameHUDSettings.getFont(android.graphics.Color.rgb(222, 222, 55));
		Font thirdFont = GameHUDSettings.getFont(android.graphics.Color.rgb(0, 0, 255));
		
		firstText = new TextMenuItem(0, firstFont, "", MAX_CHARACTERS_TO_DRAW, new TextOptions(HorizontalAlign.LEFT), manager);
		secondText = new TextMenuItem(0, secondFont, "", MAX_CHARACTERS_TO_DRAW, new TextOptions(HorizontalAlign.LEFT), manager);
		thirdText = new TextMenuItem(0, thirdFont, "", MAX_CHARACTERS_TO_DRAW, new TextOptions(HorizontalAlign.LEFT), manager);
		
		attachChild(SceneLayoutUtils.adjustToLeftCenteredListItem(firstText, 0, PLACES_COUNT));
		attachChild(SceneLayoutUtils.adjustToLeftCenteredListItem(secondText, 1, PLACES_COUNT));
		attachChild(SceneLayoutUtils.adjustToLeftCenteredListItem(thirdText, 2, PLACES_COUNT));
	}
	
	public void update() {
		List<Integer> scores = Game.getGameInstance().getHighScores();
		firstText.setText(StringUtils.appendSpaces(String.valueOf(1) + ". " 
				+ String.valueOf(scores.get(0)), MAX_CHARACTERS_TO_DRAW));
		secondText.setText(StringUtils.appendSpaces(String.valueOf(2) + ". " 
				+ String.valueOf(scores.get(1)), MAX_CHARACTERS_TO_DRAW));
		thirdText.setText(StringUtils.appendSpaces(String.valueOf(3) + ". " 
				+ String.valueOf(scores.get(2)), MAX_CHARACTERS_TO_DRAW));
	}
	
	@Override
	public void back() {
		listener.onChildClicked(ClickCodes.HIGH_SCORES_SCENE_BACK);
	}
}
