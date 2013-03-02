package pp.game.level;

import pp.game.handlers.*;
import pp.game.handlers.level.*;

class TestLevelInitializer extends ConfigLevelInitializer {
	private ILevelHandler handler;
	
	public TestLevelInitializer(ConfigLevelType type) {
		super(type);
	}
	
	public TestLevelInitializer(ConfigLevelType type, ILevelHandler handler) {
		super(type);
		this.handler = handler;
	}
	
	public ILevelHandler getLevelHandler() {
		return handler == null ? new TestLevelHandler((BasicLevelHandler)super.getLevelHandler())
				: handler;
	}
}
