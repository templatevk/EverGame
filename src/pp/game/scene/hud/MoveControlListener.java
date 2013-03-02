package pp.game.scene.hud;

import org.andengine.engine.camera.hud.controls.*;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.entity.sprite.*;
import org.andengine.util.debug.*;
import org.andengine.util.math.*;

import pp.game.*;
import pp.game.entities.*;
import pp.game.level.*;
import pp.game.textures.*;
import pp.game.utils.*;
import pp.game.utils.geometry.*;

class MoveControlListener extends PlayerControlListener implements IAnalogOnScreenControlListener, IPreparable {
	private static final float COEF_REQUIRED_DIFF = 0.05f;
	private static final float COEF_MIN_VALUE = 0.05f;
	private final int[] slowWalkFrames = (int[])(((Object[])
			SingleTiledTextureType.PLAYER_WALK.getUserData())[1]);
	
	private long[] fastWalkDurations;
	private long[] slowWalkDurations;
	private int[] stopTiles;
	private float duration;
	private float prevCoef;
	private Player player;
	private AnimatedSprite playerSprite;
	private boolean prepared = false;
	
	public MoveControlListener() {
		stopTiles = (int[])(((Object[])
				SingleTiledTextureType.PLAYER_WALK.getUserData())[0]);
		fastWalkDurations = new long[SingleTiledTextureType.PLAYER_WALK.getTilesCount()];
		slowWalkDurations = new long[slowWalkFrames.length];
		duration = SingleTiledTextureType.PLAYER_WALK.getAnimationDuration();
		prevCoef = -1f;
		Game.getGameInstance().addPreparable(this);
	}
	
	private int getStopTileIndex() {
		return CalcUtils.getGreaterOrEqual(stopTiles, playerSprite.getCurrentTileIndex());
	}
	
	private void animateSprite(float coef) {
		if (coef > 0.5) {
			playerSprite.animate(fastWalkDurations, true);
		} else {
			playerSprite.setCurrentTileIndex(CalcUtils.getGreaterOrEqual(
					slowWalkFrames, playerSprite.getCurrentTileIndex()));
			playerSprite.animate(slowWalkDurations, 
					slowWalkFrames, true);
		}
	}
	
	private boolean refreshAnimationData(float coef) {
		if (Math.abs(coef - prevCoef) >= COEF_REQUIRED_DIFF) {
			prevCoef = coef;
			if (coef > 0.5) {
				for (int i = 0; i < fastWalkDurations.length; i++) {
					fastWalkDurations[i] = (long)(duration / coef);
				}
			} else {
				for (int i = 0; i < slowWalkDurations.length; i++) {
					slowWalkDurations[i] = (long)(duration / coef);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void prepare(ILevel level) {
		if (!prepared) {
			prepared = true;
			player = Player.getInstance();
			playerSprite = player.getLegsSprite();
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
		
		x /= control.getScaleX();
		y /= control.getScaleY();
		
		final float coef = MathUtils.distance(0, 0, x, y);
		if (coef < COEF_MIN_VALUE) { // do not move the player if the control's change is too small
			playerSprite.stopAnimation();
			playerSprite.setCurrentTileIndex(getStopTileIndex());
			player.getBody().setLinearVelocity(0, 0);
			return;
		}
		
		final float fX = x;
		final float fY = y;
		float xVelocity = fX * Player.getInstance().getPlayerSpeed();
		float yVelocity = fY * Player.getInstance().getPlayerSpeed();
		player.getBody().setLinearVelocity(xVelocity, yVelocity);
		
		boolean animationNeedsRestart = refreshAnimationData(coef);

		if (playerSprite.isAnimationRunning()) {
			if (fX == 0 && fY == 0) {
				playerSprite.stopAnimation();
				playerSprite.setCurrentTileIndex(getStopTileIndex());
			} else {							
				playerSprite.setRotation(GeometryUtils.getRotation(new Point(fX, fY)));
				if (animationNeedsRestart) {
					playerSprite.stopAnimation();
					animateSprite(coef);
				}
			}
		} else {
			if (fX != 0 || fY != 0) {
				animateSprite(coef);		
			}
		}
	}
	
	@Override
	public void onControlClick(AnalogOnScreenControl control) {
		
	}
}
