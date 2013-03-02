package pp.game.handlers;

import org.andengine.engine.handler.*;
import org.andengine.entity.*;

import pp.game.utils.concurrent.*;

public class UnregisterHandlerCommand implements IHandlerCommand {
	private IEntity entity;
	private IUpdateHandler handler;
	
	public UnregisterHandlerCommand(IEntity entity, IUpdateHandler handler) {
		this.entity = entity;
		this.handler = handler;
	}
	
	@Override
	public void execute() {
		ConcurrentUtils.removeHandlerFromEntity(entity, handler);
	}
}
