package pp.game.entities;

import pp.game.utils.geometry.*;

public enum WeaponType {	
//	RUNNER(100, Player.DEFAULT_PLAYER_SPEED * 0.50f, 5, 0.5f), 
//	ZOMBIE(250, Player.DEFAULT_PLAYER_SPEED * 0.17f, 15, 1), 
//	SPIDER(150, Player.DEFAULT_PLAYER_SPEED * 0.33f, 10, 0.75f);
	
	AK_47	(200, 	0.13f, 	1.5f, 	12 * SceneLayoutUtils.VELOCITY_ADJUST_COEF, 	30), 
	PM		(90, 	0.5f, 	1.0f,	6 * SceneLayoutUtils.VELOCITY_ADJUST_COEF, 		7),  
	SHOTGUN	(85, 	0.7f, 	1.5f, 	11 * SceneLayoutUtils.VELOCITY_ADJUST_COEF,		5), 
	UZI		(100,	0.09f, 	1.0f, 	13 * SceneLayoutUtils.VELOCITY_ADJUST_COEF, 	30),
	
	FLAMETHROWER(0, 0, 0, 0, 0), 
	LASER(0, 0, 0, 0, 0), 
	M_32(0, 0, 0, 0, 0), 
	REMINGTON(0, 0, 0, 0, 0);
	
	private float damage;
	private float shotDelay;
	private float reloadDelay;
	private float bulletSpeed;
	private int magazineVolume;
	
	private WeaponType(float damage, float shotDelay, float reloadDelay,
			float bulletSpeed, int magazineVolume) {
		this.damage = damage;
		this.shotDelay = shotDelay;
		this.reloadDelay = reloadDelay;
		this.bulletSpeed = bulletSpeed;
		this.magazineVolume = magazineVolume;
	}

	public float getDamage() {
		return damage;
	}

	public float getShotDelay() {
		return shotDelay;
	}

	public float getReloadDelay() {
		return reloadDelay;
	}

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public int getMagazineVolume() {
		return magazineVolume;
	}
}
