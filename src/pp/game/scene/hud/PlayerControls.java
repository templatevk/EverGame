package pp.game.scene.hud;

import org.andengine.engine.camera.hud.controls.*;

import pp.game.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;
import android.opengl.*;

public class PlayerControls {
	public static final float CONTROL_UPDATE_INTERVAL = 0.03f;
	
	private static final float CONTROL_ALPHA = 0.3f;
	
	private static PlayerControl moveControl;
	
	private PlayerControls() {
		
	}
	
	private static void initialize() {
		if (SingleTiledTextureType.PLAYER_WALK.getAnimationDuration() - CONTROL_UPDATE_INTERVAL < 10) {
			throw new RuntimeException("Player's animation duration should be greater at least by 10 ms than PlayerControl's update interval");
		}
		
		TextureHolder holder = TextureHolder.getInstance();
		
		moveControl = new PlayerControl(
				Game.getGameActivity().getEngine().getCamera(), 
				holder.getTexture(PlayerControlTextureType.MOVE_BASE), 
				holder.getTexture(PlayerControlTextureType.MOVE_KNOB), 
				Game.getGameActivity().getVertexBufferObjectManager(), 
				PlayerControlType.MOVE_CONTROL);	
		moveControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		moveControl.getControlBase().setAlpha(CONTROL_ALPHA);
		moveControl.getControlKnob().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		moveControl.getControlKnob().setAlpha(CONTROL_ALPHA);
		SceneLayoutUtils.adjustPlayerControl(moveControl);
		
		PlayerControl shootControl = new PlayerControl(
				Game.getGameActivity().getEngine().getCamera(), 
				holder.getTexture(PlayerControlTextureType.SHOOT_BASE), 
				holder.getTexture(PlayerControlTextureType.SHOOT_KNOB), 
				Game.getGameActivity().getVertexBufferObjectManager(), 
				PlayerControlType.SHOOT_CONTROL);
		shootControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		shootControl.getControlBase().setAlpha(CONTROL_ALPHA);
		shootControl.getControlKnob().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		shootControl.getControlKnob().setAlpha(CONTROL_ALPHA);
		SceneLayoutUtils.adjustPlayerControl(shootControl);		
		
		moveControl.setChildScene(shootControl);
	}
	
	public static AnalogOnScreenControl getControls() {
		if (moveControl == null) {
			initialize();
		}		
		return moveControl;
	}
}
