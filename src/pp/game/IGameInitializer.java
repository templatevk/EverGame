package pp.game;

import org.andengine.engine.options.*;
import org.andengine.entity.scene.*;

import pp.game.scene.hud.*;

interface IGameInitializer {
	EngineOptions createEngineOptions();
	Scene getMainScene();
	IChildClickListener getClickListener();
	void initialize();
	void postInitialize();
}
