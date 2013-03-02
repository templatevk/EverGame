package pp.game.textures;

public enum BulletTextureType {
	AK_47("weapons/AK_47/player.png"), 
	FLAMETHROWER("weapons/Flamethrower/player.png"), 
	LASER("weapons/Laser/player.png"), 
	M_32("weapons/M_32/player.png"), 
	PM("weapons/PM/player.png"), 
	REMINGTON("weapons/Remington/player.png"), 
	SHOTGUN("weapons/Shotgun/player.png"), 
	UZI("weapons/UZI/player.png");
	
	private String assetPath;	
	
	private BulletTextureType(String assetPath) {
		this.assetPath = assetPath;
	}

	public String getAssetPath() {
		return assetPath;
	}
}
