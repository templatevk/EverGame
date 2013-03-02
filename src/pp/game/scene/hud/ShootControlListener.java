package pp.game.scene.hud;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.*;
import org.andengine.util.debug.*;
import org.andengine.util.math.*;

import pp.game.*;
import pp.game.entities.*;
import pp.game.level.*;
import pp.game.utils.geometry.*;

class ShootControlListener extends PlayerControlListener implements IAnalogOnScreenControlListener, IPreparable {
	private Player player;
	private boolean prepared = false;
	
	public ShootControlListener() {
		Game.getGameInstance().addPreparable(this);
	}
	
	@Override
	public void prepare(ILevel level) {
		if (!prepared) {
			prepared = true;
			player = Player.getInstance();
		}
	}
	
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}
	
	@Override
	public void onControlChange(BaseOnScreenControl control, float x, float y) {
		if (!prepared || player.isDead()) {
			return;
		}
		
		if (x != 0 && y != 0) {
			float absX = Math.abs(x);
			float absY = Math.abs(y);
			float diff = 1 - MathUtils.distance(0, 0, x, y);
			if (absX < absY) {
				float coef = Math.abs(x / y);
				y += Math.signum(y) * diff;
				x += Math.signum(x) * diff * coef;
			} else {
				float coef = Math.abs(y / x);				
				x += Math.signum(x) * diff;
				y += Math.signum(y) * diff * coef;
			}
			
			final Point direction = new Point(x, y);
			player.getWeapon().getSprite().setRotation(GeometryUtils.getRotation(direction));
			if (player.getWeapon().isReady()) {
				player.getWeapon().shot(direction);				
			}
		}
	}
}
