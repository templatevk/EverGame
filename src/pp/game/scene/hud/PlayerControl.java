package pp.game.scene.hud;

import org.andengine.engine.camera.*;
import org.andengine.engine.camera.hud.controls.*;
import org.andengine.opengl.texture.region.*;
import org.andengine.opengl.vbo.*;

import pp.game.*;
import pp.game.textures.*;
import pp.game.utils.*;
import android.opengl.*;
import android.provider.MediaStore.Audio.*;

public class PlayerControl extends AnalogOnScreenControl {
	private float controlBaseWidth;
	private float controlBaseHeight;
	private float controlKnobWidth;
	private float controlKnobHeight;
	private PlayerControlType type;
	
	PlayerControl(Camera camera, ITextureRegion baseRegion, 
			ITextureRegion knobRegion, VertexBufferObjectManager manager,
			PlayerControlType type) {
		super(0, 0, camera, baseRegion, knobRegion, PlayerControls.CONTROL_UPDATE_INTERVAL, 
				manager, PlayerControlType.getListener(type));

		this.controlBaseHeight = baseRegion.getHeight();
		this.controlBaseWidth = baseRegion.getWidth();
		this.controlKnobHeight = knobRegion.getHeight();
		this.controlKnobWidth = knobRegion.getWidth();
		this.type = type;

		setOnControlClickEnabled(false);
		setCullingEnabled(false);
	}

	public float getBaseWidth() {
		return controlBaseWidth;
	}

	public float getBaseHeight() {
		return controlBaseHeight;
	}
	
	public float getActualBaseWidth() {
		return controlBaseWidth * getControlBase().getScaleX();
	}

	public float getActualBaseHeight() {
		return controlBaseHeight * getControlBase().getScaleY();
	}
	
	public float getKnobWidth() {
		return controlKnobWidth;
	}

	public float getKnobHeight() {
		return controlKnobHeight;
	}
	
	public float getActualKnobWidth() {
		return controlKnobWidth * getControlKnob().getScaleX();
	}

	public float getActualKnobHeight() {
		return controlKnobHeight * getControlKnob().getScaleY();
	}

	public PlayerControlType getType() {
		return type;
	}
}
