package pp.game.scene;

import org.andengine.entity.scene.*;
import org.andengine.entity.scene.menu.*;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.*;

import pp.game.*;
import pp.game.audio.*;
import pp.game.level.*;
import pp.game.scene.hud.*;

public class MainScene extends Scene implements IOnMenuItemClickListener, IChildClickListener, IPreparable {
	static final int MAIN_MENU_NEW_GAME = 0x0;
	static final int MAIN_MENU_HIGH_SCORES = 0x1;
	static final int MAIN_MENU_EXIT = 0x2;
	
	static final int PAUSE_MENU_RESUME = 0x3;
	static final int PAUSE_MENU_MAIN_MENU = 0x4;
	
	private MainMenuScene mainMenuScene;
	private PauseMenuScene pauseMenuScene;
	private HighScoresMenuScene highScoresScene;
	
	public MainScene() {
		Game.getGameInstance().addPreparable(this);
		
		mainMenuScene = new MainMenuScene(this);
		pauseMenuScene = new PauseMenuScene(this);
		highScoresScene = new HighScoresMenuScene(this);
		
		GameScene.setChildClickListener(this);	
		
		setChildScene(mainMenuScene);
	}
	
	@Override
	public void back() {
		
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene scene, IMenuItem item,
			float localX, float localY) {
		switch (item.getID()) {
		case MAIN_MENU_NEW_GAME:
			clearChildScene();
			setChildScene(GameScene.scene());
			Game.getGameInstance().start(LevelFactory.getLevel(LevelType.SURVIVAL));
			break;
		case MAIN_MENU_HIGH_SCORES:
			clearChildScene();
			highScoresScene.update();
			setChildScene(highScoresScene);
			break;
		case MAIN_MENU_EXIT:
			Game.getGameInstance().exit();
			break;
			
		case PAUSE_MENU_RESUME:
			clearChildScene();			
			Game.getGameInstance().resume();
			AudioHolder.getInstance().playGameMusic(GameScene.getInstance().getCurrentMusic(), true);
			setChildScene(GameScene.scene());
			break;
		case PAUSE_MENU_MAIN_MENU:
			clearChildScene();
			Game.getGameInstance().stop();
			setChildScene(mainMenuScene);
			break;
		}
		return true;
	}

	@Override
	public void onChildClicked(int ID) {
		switch (ID) {
		case ClickCodes.GAME_SCENE_BACK:
			clearChildScene();
			Game.getGameInstance().pause();
			AudioHolder.getInstance().playMenuMusic(MenuMusicType.MENU);
			setChildScene(pauseMenuScene);
			break;
		case ClickCodes.GAME_ACTIVITY_BACK:
			getChildScene().back();
			break;
		case ClickCodes.HIGH_SCORES_SCENE_BACK:
			clearChildScene();
			setChildScene(mainMenuScene);
			break;
		}
	}
	
	@Override
	public void prepare(ILevel level) {
		
	}
	
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}
}
