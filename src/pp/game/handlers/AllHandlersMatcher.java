package pp.game.handlers;

import org.andengine.engine.handler.*;
import org.andengine.engine.handler.IUpdateHandler.*;

public class AllHandlersMatcher implements IUpdateHandlerMatcher {
	public AllHandlersMatcher() {
		
	}
	
	@Override
	public boolean matches(IUpdateHandler handler) {
		return true;
	}
}
