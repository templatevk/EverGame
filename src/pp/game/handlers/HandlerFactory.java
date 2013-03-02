package pp.game.handlers;

import pp.game.entities.*;
import pp.game.handlers.entity.*;

public class HandlerFactory {
	private HandlerFactory() {
		
	}
	
	public static IMonsterUpdateHandler getMonsterHandler(Monster monster) {
		return new BasicMonsterUpdateHandler(monster);
	}
}
