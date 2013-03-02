package pp.game.scene;

import org.andengine.engine.camera.*;
import org.andengine.engine.handler.*;
import org.andengine.entity.*;
import org.andengine.entity.scene.*;
import org.andengine.entity.scene.background.*;
import org.andengine.opengl.util.*;

import pp.game.*;
import pp.game.audio.*;
import pp.game.handlers.*;
import pp.game.handlers.level.*;
import pp.game.level.*;
import pp.game.physics.*;
import pp.game.scene.hud.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;

public class GameScene extends Scene implements IGameScene, IPreparable, IResetable, IPausable {
	private static final GameScene INSTANCE = new GameScene();
	
	private RepeatingSpriteBackground background;
	private ILevelHandler handler;
	private Camera camera;
	private IChildClickListener listener;
	private GameMusicType currentMusicType;
	
	private GameScene() {
		IGame game = Game.getGameInstance();
		game.addPreparable(this);
		game.addResetable(this);
		game.addPauseable(this);
		
		setIgnoreUpdate(true);
		
		camera = Game.getGameActivity().getEngine().getCamera();
		
		setChildScene(PlayerControls.getControls());
		camera.setHUD(GameHUD.getInstance());
	}
	
	static void setChildClickListener(IChildClickListener listener) {
		INSTANCE.listener = listener;
	}
	
	static Scene scene() {
		return INSTANCE;
	}
	
	public static IGameScene getInstance() {
		return INSTANCE;
	}
	
	private void unregisterHandler(IUpdateHandler handler) {
		super.unregisterUpdateHandler(handler);
	}
	
	private void unregisterHandlers(IUpdateHandlerMatcher matcher) {
		super.unregisterUpdateHandlers(matcher);
	}
	
	@Override
	public boolean unregisterUpdateHandler(final IUpdateHandler pUpdateHandler) {
		postRunnable(new Runnable() {
			@Override
			public void run() {
				unregisterHandler(pUpdateHandler);
			}
		});
		return true;
	}
	
	@Override
	public boolean unregisterUpdateHandlers(final IUpdateHandlerMatcher matcher) {
		postRunnable(new Runnable() {
			@Override
			public void run() {
				unregisterHandlers(matcher);
			}
		});
		return true;
	}
	
	@Override
	public void registerLevelHandler(ILevelHandler handler) {
		if (this.handler != null) {
			unregisterUpdateHandler(this.handler);
		}
		this.handler = handler;
		super.registerUpdateHandler(handler);
	}
	
	@Override
	public void detachChild(IEntity entity, boolean onUpdateThread) {
		if (onUpdateThread) {
			detachChild(entity);
		} else {
			super.detachChild(entity);
		}
	}
	
	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		sortChildren();
		super.onManagedDraw(pGLState, pCamera);
	}
	
	@Override
	public void back() {
		listener.onChildClicked(ClickCodes.GAME_SCENE_BACK);
	}
	
	@Override
	public boolean detachChild(final IEntity entity) {
		Game.getGameActivity().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				detachChild(entity, false);
			}
		});
		return true;
	}
	
	@Override
	public void prepare(ILevel level) {	
		setIgnoreUpdate(false);
		registerUpdateHandler(PhysicsManager.getInstance().getUpdateHandler());
		AudioHolder.getInstance().playGameMusic(level.getMusic());
		
		background = new RepeatingSpriteBackground(
				camera.getWidth() * SceneLayoutUtils.BACKGROUND_SCALE_COEF,
				camera.getHeight() * SceneLayoutUtils.BACKGROUND_SCALE_COEF,
				Game.getGameActivity().getTextureManager(),
				TextureHolder.getInstance().getTexture(level.getBackground()), 
				Game.getGameActivity().getVertexBufferObjectManager());
		attachChild(SceneLayoutUtils.adjustLevelBackground(background).getSprite());
	}
	
	@Override
	public void pause() {
		setIgnoreUpdate(true);
	}
	
	@Override
	public void resume() {
		setIgnoreUpdate(false);
	}
	
	@Override
	public void reset() {
		setIgnoreUpdate(true);
		detachChildren();
		unregisterHandlers(new AllHandlersMatcher());
	}
	
	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

	@Override
	public GameMusicType getCurrentMusic() {
		return currentMusicType;
	}

	@Override
	public void setCurrentMusic(GameMusicType type) {
		currentMusicType = type;
		AudioHolder.getInstance().playGameMusic(type, true);
	}
}
