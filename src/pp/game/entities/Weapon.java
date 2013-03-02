package pp.game.entities;

import java.util.*;

import org.andengine.entity.sprite.*;

import pp.game.*;
import pp.game.audio.*;
import pp.game.handlers.*;
import pp.game.observers.*;
import pp.game.scene.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;
import pp.game.utils.type.*;

public class Weapon {	
	private static final float BULLETS_BONUS_SHOT_DELAY_COEF = 0.5f;
	
	private static List<IObserver<Weapon>> observers = new ArrayList<IObserver<Weapon>>();
	
	private final float OFFSET;
	
	private WeaponType type;
	private Sprite sprite;	
	private boolean isReady;
	private int currentBullets;
	private float delayUntilReady;
	private BonusType weaponBonus;
	
	private class WeaponUpdateHandler extends UpdateHandler {
		public WeaponUpdateHandler() {
			
		}
		
		@Override
		public void onUpdate(float secondsElapsed) {
			if (delayUntilReady > 0) {
				delayUntilReady = delayUntilReady - secondsElapsed < 0 ? 0 
						: delayUntilReady - secondsElapsed;
			} else {
				notifyObservers();
			}
			isReady = delayUntilReady == 0;
		}
	}
	
	private Weapon(Sprite sprite, WeaponType type) {
		OFFSET = (sprite.getWidthScaled() + sprite.getHeightScaled()) / 2f / 10f;
		this.sprite = sprite;
		this.type = type;
		this.isReady = true;
		this.currentBullets = type.getMagazineVolume();
		this.weaponBonus = BonusType.NONE;
	}
	
	public static Weapon getWeapon(WeaponType type) {
		Sprite sprite = new Sprite(0f, 0f, TextureHolder.getInstance()
				.getTexture(TypeConverter.getWeaponTextureType(type)), 
				Game.getGameActivity().getVertexBufferObjectManager());		
		Weapon weapon = new Weapon(sprite, type);
		SceneLayoutUtils.adjustWeapon(weapon);
		
		weapon.getSprite().registerUpdateHandler(weapon.new WeaponUpdateHandler());
		weapon.notifyObservers();
		
		return weapon;
	}
	
	private void notifyObservers() {
		for (IObserver<Weapon> observer : observers) {
			observer.onChanged(this);
		}
	}
	
	private void shotBullet(Point direction) {
		shotBullet(direction, 0, 0);
	}
	
	private void shotBullet(Point direction, float xOffset, float yOffset) {
		Bullet bullet = Bullet.getBullet(type, xOffset, yOffset);
		GameScene.getInstance().attachChild(bullet.getShape());
		bullet.getBody().setLinearVelocity(type.getBulletSpeed() * direction.x, 
				type.getBulletSpeed() * direction.y);
	}
	
	public boolean isReady() {
		return isReady;
	}	
	
	public WeaponType getWeaponType() {
		return type;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void shot(Point direction) {	
		switch (type) {
		case AK_47:
		case FLAMETHROWER:
		case LASER:
		case M_32:
		case PM:
		case REMINGTON:
		case UZI:
			shotBullet(direction);
			break;
		case SHOTGUN:
			shotBullet(direction, -3f * OFFSET, 0);
			shotBullet(direction, -1.5f * OFFSET, -OFFSET);
			shotBullet(direction, -1.5f * OFFSET, OFFSET);
			shotBullet(direction, 0, 0);
			shotBullet(direction, 1.5f * OFFSET, OFFSET);
			shotBullet(direction, 1.5f * OFFSET, -OFFSET);
			shotBullet(direction, 3f * OFFSET, 0);
			break;
		}
		
		AudioHolder.getInstance().playWeaponShotSound(
				TypeConverter.getWeaponShotSoundType(type));
		
		if (weaponBonus == BonusType.BULLETS) {
			delayUntilReady = type.getShotDelay() * BULLETS_BONUS_SHOT_DELAY_COEF;
		} else {
			currentBullets--;
			notifyObservers();
			if (currentBullets == 0) {
				currentBullets = type.getMagazineVolume();
				delayUntilReady = type.getReloadDelay();
				AudioHolder.getInstance().playWeaponReloadSound(
						TypeConverter.getWeaponReloadSoundType(type));
			} else {
				delayUntilReady = type.getShotDelay();
			}
		}
		isReady = false;
	}

	public int getCurrentBullets() {
		return currentBullets;
	}

	public static void addObservable(IObserver<Weapon> observer) {
		observers.add(observer);
	}
	
	public static void removeObservable(IObserver<Weapon> observer) {
		observers.remove(observer);
	}

	public BonusType getWeaponBonus() {
		return weaponBonus;
	}

	@SuppressWarnings("incomplete-switch")
	public void setWeaponBonus(BonusType weaponBonus) {
		switch (weaponBonus) {
		case BULLETS:
			currentBullets = type.getMagazineVolume();
			break;
		}
		
		this.weaponBonus = weaponBonus;
	}
}
