package pp.game;

import org.andengine.engine.options.*;
import org.andengine.entity.scene.*;
import org.andengine.ui.activity.*;

import pp.game.scene.hud.*;

public class GameActivity extends BaseGameActivity {
	private IGameInitializer initializer;
	
	public GameActivity() {
		initializer = Game.getGameInitializer(this);
	}
	
	@Override
	public void onBackPressed() {
		initializer.getClickListener().onChildClicked(ClickCodes.GAME_ACTIVITY_BACK);
	}
	
	@Override
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback populateSceneCallback)
			throws Exception {
		initializer.postInitialize();
		populateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		return initializer.createEngineOptions();
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback createResourcesCallback)
			throws Exception {	
		initializer.initialize();
		createResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback createSceneCallback) 
			throws Exception {	
		createSceneCallback.onCreateSceneFinished(initializer.getMainScene());		
	}
}
