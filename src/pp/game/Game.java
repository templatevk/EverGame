package pp.game;

import java.util.*;

import org.andengine.engine.camera.*;
import org.andengine.engine.options.*;
import org.andengine.engine.options.resolutionpolicy.*;
import org.andengine.entity.scene.*;
import org.andengine.ui.activity.*;
import org.andengine.util.debug.*;

import pp.game.audio.*;
import pp.game.entities.*;
import pp.game.level.*;
import pp.game.observers.*;
import pp.game.physics.*;
import pp.game.scene.*;
import pp.game.scene.hud.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;
import android.app.*;
import android.content.*;
import android.content.SharedPreferences.Editor;
import android.os.Process;
import android.view.*;

public class Game implements IGame, IGameInitializer, IObservable<Integer> {
	private static final String PREFFERENCES = "CruelLandPrefferences";
	private static final String FIRST_SCORE = "scores.first";
	private static final String SECOND_SCORE = "scores.second";
	private static final String THIRD_SCORE = "scores.third";
	
	private static final Game INSTANCE = new Game();
	
	private static BaseGameActivity gameActivity;
	
	private BoundCamera camera;	
	private MainScene scene;
	private List<IPreparable> preparables;
	private List<IDestroyable> destroyables;
	private List<IPausable> pausables;
	private List<IResetable> resetables;
	private List<IObserver<Integer>> scoreObservers;
	private int score;
	
	private Comparator<IPrioritized> prioritizedComp = new Comparator<IPrioritized>() {
		@Override
		public int compare(IPrioritized first, IPrioritized second) {
			return first.getPriority().compareTo(second.getPriority());
		}
	};
	
	static IGameInitializer getGameInitializer(BaseGameActivity gameActivity) {
		Game.gameActivity = gameActivity;
		return INSTANCE;
	}
	
	public static IGame getGameInstance() {
		return INSTANCE;
	}
	
	public static BaseGameActivity getGameActivity() {
		return gameActivity;
	}
	
	private Game() {
		preparables = new ArrayList<IPreparable>();
		destroyables = new ArrayList<IDestroyable>();
		pausables = new ArrayList<IPausable>();
		resetables = new ArrayList<IResetable>();
		scoreObservers = new ArrayList<IObserver<Integer>>();
	}
	
	private void notifyScoreObservers() {
		for (IObserver<Integer> observer : scoreObservers) { 
			observer.onChanged(score);
		}
	}
	
	private void initializePreparables() {
		Player.getInstance();
		PhysicsManager.getInstance();
	}	
	
	// IGameInitializer
	
	@Override
	public Scene getMainScene() {
		return scene;
	}
	
	@Override
	public IChildClickListener getClickListener() {
		return scene;
	}
	
	@Override	
	public EngineOptions createEngineOptions() {		
		Display display = gameActivity.getWindowManager().getDefaultDisplay();
		
		camera = new BoundCamera(0, 0, display.getWidth(), display.getHeight());
		camera.setBounds(0, 0, display.getWidth() * SceneLayoutUtils.BACKGROUND_SCALE_COEF, 
				display.getHeight() * SceneLayoutUtils.BACKGROUND_SCALE_COEF);
		camera.setBoundsEnabled(true);
		
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
				new RatioResolutionPolicy(display.getWidth(), display.getHeight()), camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		
		return engineOptions;
	}

	@Override
	public void initialize() {	
		AudioHolder.getInstance().initialize();
		TextureHolder.getInstance();
		scene = new MainScene();
		initializePreparables();
	}	
	
	@Override
	public void postInitialize() {
		AudioHolder.getInstance().playMenuMusic(MenuMusicType.MENU);
	}
	
	// IGame
	
	@Override
	public List<Integer> getHighScores() {
		SharedPreferences prefs = gameActivity.getSharedPreferences(PREFFERENCES, 
				Context.MODE_PRIVATE);
		
		List<Integer> scores = new ArrayList<Integer>();
		scores.add(prefs.getInt(FIRST_SCORE, 0));
		scores.add(prefs.getInt(SECOND_SCORE, 0));
		scores.add(prefs.getInt(THIRD_SCORE, 0));
		
		return scores;
	}
	
	@Override
	public void addScore(int score) {
		this.score += score;
		notifyScoreObservers();
	}
	
	@Override
	public int getCurrentScore() {
		return score;
	}
	
	@Override
	public void start(ILevelInitializer levelInitializer) {
		score = 0;
		notifyScoreObservers();
		
		Collections.sort(preparables, prioritizedComp);
		GameScene.getInstance().registerLevelHandler(levelInitializer.getLevelHandler());
		ILevel level = levelInitializer.getLevel();
		for (IPreparable preparable : preparables) {
			preparable.prepare(level);
		}
	}
	
	@Override
	public void stop() {
		SharedPreferences prefs = gameActivity.getSharedPreferences(PREFFERENCES, 
				Context.MODE_PRIVATE);
		int first = prefs.getInt(FIRST_SCORE, 0);
		int second = prefs.getInt(SECOND_SCORE, 0);
		int third = prefs.getInt(THIRD_SCORE, 0);
		if (score > first) {
			first = score;
		} else if (score > second) {
			second = score;
		} else if (score > third) {
			third = score;
		}
		Editor editor = prefs.edit();
		editor.putInt(FIRST_SCORE, first);
		editor.putInt(SECOND_SCORE, second);
		editor.putInt(THIRD_SCORE, third);
		editor.apply();
		editor.commit();
		
		Collections.sort(resetables, prioritizedComp);
		for (IResetable resetable : resetables) {
			resetable.reset();
		}	
	}
	
	@Override
	public void pause() {
		Collections.sort(pausables, prioritizedComp);
		for (IPausable pausable : pausables) {
			pausable.pause();
		}
	}
	
	@Override
	public void resume() {
		Collections.sort(pausables, prioritizedComp);
		for (IPausable pausable : pausables) {
			pausable.resume();
		}		
	}	
	
	@Override
	public void exit() {
		Collections.sort(destroyables, prioritizedComp);
		for (IDestroyable destroyable : destroyables) {
			destroyable.destroy();
		}
		System.exit(0);
	}

	@Override
	public void addPreparable(IPreparable preparable) {
		preparables.add(preparable);
	}

	@Override
	public void removePreparable(IPreparable preparable) {
		preparables.remove(preparable);		
	}
	
	@Override
	public void addDestroyable(IDestroyable destroyable) {
		destroyables.add(destroyable);		
	}
	
	@Override
	public void removeDestroyable(IDestroyable destroyable) {
		destroyables.remove(destroyable);		
	}
	
	@Override
	public void addPauseable(IPausable pausable) {
		pausables.add(pausable);
	}
	
	@Override
	public void removePauseable(IPausable pausable) {
		pausables.remove(pausable);
	}
	
	@Override
	public void addResetable(IResetable resetable) {
		resetables.add(resetable);
	}
	
	@Override
	public void removeResetable(IResetable resetable) {
		resetables.remove(resetable);
	}
	
	@Override
	public void addObservable(IObserver<Integer> observer) {
		scoreObservers.add(observer);
	}
	
	@Override
	public void removeObservable(IObserver<Integer> observer) {
		scoreObservers.remove(observer);
	}
}
