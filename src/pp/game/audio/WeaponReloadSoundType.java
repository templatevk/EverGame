package pp.game.audio;

public enum WeaponReloadSoundType {
	AK_47("weapons/AK_47/reload.ogg"), 
	FLAMETHROWER("weapons/Flamethrower/reload.ogg"), 
	LASER("weapons/Laser/reload.ogg"),  
	PM("weapons/PM/reload.ogg"),  
	SHOTGUN("weapons/Shotgun/reload.ogg"), 
	UZI("weapons/UZI/reload.ogg");
	
	private String assetPath;
	
	private WeaponReloadSoundType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}
