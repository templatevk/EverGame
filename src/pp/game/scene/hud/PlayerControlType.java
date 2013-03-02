package pp.game.scene.hud;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;

public enum PlayerControlType {
	MOVE_CONTROL, SHOOT_CONTROL;
	
	static IAnalogOnScreenControlListener getListener(PlayerControlType type) {
		switch (type) {
		case MOVE_CONTROL:
			return new MoveControlListener();
		case SHOOT_CONTROL:
			return new ShootControlListener();
		}
		return null;
	}
}
