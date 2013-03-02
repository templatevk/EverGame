package pp.game.scene;

import org.andengine.engine.handler.*;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.entity.*;

import pp.game.audio.*;
import pp.game.handlers.level.*;

public interface IGameScene {
	GameMusicType getCurrentMusic();
	void setCurrentMusic(GameMusicType type);
	void registerLevelHandler(ILevelHandler handler);
	boolean detachChild(IEntity entity);
	void detachChild(IEntity entity, boolean onUpdateThread);
	void attachChild(IEntity entity);
	void registerUpdateHandler(IUpdateHandler handler);
	boolean unregisterUpdateHandler(IUpdateHandler handler);
	boolean unregisterUpdateHandlers(IUpdateHandlerMatcher matcher);
	void postRunnable(Runnable run);
}
