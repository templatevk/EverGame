package pp.game.handlers.level;

import pp.game.handlers.*;

public class TestLevelHandler extends UpdateHandler implements ILevelHandler {
	private boolean update;
	private BasicLevelHandler handler;
	
	public TestLevelHandler(BasicLevelHandler handler) {
		this.handler = handler;
		update = handler.getMonsterTypes().length == 0 ? false : true; 
	}
	
	@Override
	public void onUpdate(float secondsElapsed) {
		if (update) {
			handler.onUpdate(secondsElapsed);
		}
	}
}
