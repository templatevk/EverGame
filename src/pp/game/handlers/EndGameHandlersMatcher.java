package pp.game.handlers;

import org.andengine.engine.handler.*;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.util.debug.*;

import pp.game.handlers.contact.*;
import pp.game.handlers.level.*;

public class EndGameHandlersMatcher implements IUpdateHandlerMatcher {
	@Override
	public boolean matches(IUpdateHandler handler) {
		return handler instanceof ILevelHandler
				|| handler instanceof IContactHandler;
	}
}
