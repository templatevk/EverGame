package pp.game.scene.hud;

import org.andengine.entity.text.*;
import org.andengine.opengl.font.*;
import org.andengine.util.*;
import org.andengine.util.debug.*;

import pp.game.*;
import pp.game.entities.*;
import pp.game.observers.*;
import pp.game.utils.geometry.*;
import android.graphics.*;

class WeaponMagazineIndicator extends Text {
	private static final int MAX_CHARACTERS_TO_DRAW = 5;	
	private static final int Y = 10;
	private static final float MAGIC_X_ADJUST_COEF = 0.32f;
	
	private class WeaponMagazineObserver implements IObserver<Weapon> {
		public WeaponMagazineObserver() {
			
		}
		
		@Override
		public void onChanged(Weapon weapon) {					
			setText(weapon.getCurrentBullets() + "/" + weapon.getWeaponType().getMagazineVolume());
		}
	}
	
	private WeaponMagazineIndicator(IFont font) {
		super(0, Y, font, "", MAX_CHARACTERS_TO_DRAW, new TextOptions(HorizontalAlign.LEFT), 
				Game.getGameActivity().getVertexBufferObjectManager());
	}
	
	static WeaponMagazineIndicator getMagazineIndicator() {
		Font font = GameHUDSettings.getFont(Color.rgb(189, 183, 107), FontSize.SMALL);
		WeaponMagazineIndicator indicator = new WeaponMagazineIndicator(font);
		SceneLayoutUtils.adjustGameHUDText(indicator);
		indicator.setX(SceneLayoutUtils.WIDTH - GameHUD.SMALL_TEXTURE_WIDTH * MAGIC_X_ADJUST_COEF);
		Weapon.addObservable(indicator.new WeaponMagazineObserver());
		return indicator;
	}
}
