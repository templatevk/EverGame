package pp.game.audio;

public enum WeaponShotSoundType {
	AK_47("weapons/AK_47/shot.ogg"), 
	FLAMETHROWER("weapons/Flamethrower/shot.ogg"), 
	LASER("weapons/Laser/shot.ogg"), 
	PM("weapons/PM/shot.ogg"), 
	SHOTGUN("weapons/Shotgun/shot.ogg"), 
	UZI("weapons/UZI/shot.ogg");
	
	private String assetPath;
	
	private WeaponShotSoundType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}
