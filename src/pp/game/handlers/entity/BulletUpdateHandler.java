package pp.game.handlers.entity;

import pp.game.entities.*;
import pp.game.handlers.*;
import pp.game.physics.*;
import pp.game.utils.geometry.*;

public class BulletUpdateHandler extends DelayedUpdateHandler {
	private static final float HANDLER_DELAY = 0.3f;
	
	private Bullet bullet;
	
	public BulletUpdateHandler(Bullet bullet) {
		this.bullet = bullet;
		setRequiredDelay(HANDLER_DELAY);
	}
	
	protected void onUpdate() {
		if (!SceneLayoutUtils.isInScene(bullet.getShape())) {
			bullet.getShape().unregisterUpdateHandler(this);
			PhysicsManager.getInstance().removeBody(bullet.getBody());
		}
	}
}
