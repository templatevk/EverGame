package pp.game.utils.concurrent;

import org.andengine.engine.handler.*;
import org.andengine.entity.*;

import pp.game.*;

public class ConcurrentUtils {
	private ConcurrentUtils() {
		
	}
	
	public static void removeHandlerFromEntity(final IEntity entity, 
			final IUpdateHandler handler) {
		Game.getGameActivity().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				entity.unregisterUpdateHandler(handler);
			}
		});
	}
}
