package pp.game.textures;

import pp.game.utils.geometry.*;

public enum BonusTextureType {
	AK_47("weapons/AK_47/image.png"), 
	FLAMETHROWER("weapons/Flamethrower/image.png"), 
	LASER("weapons/Laser/image.png"), 
	PM("weapons/PM/image.png"),  
	SHOTGUN("weapons/Shotgun/image.png"), 
	UZI("weapons/UZI/image.png"),
	
	HP_SMALL("textures/bonus/hp_small.png"),
	HP_MEDIUM("textures/bonus/hp_medium.png"),
	HP_LARGE("textures/bonus/hp_large.png"),
	SPEED("textures/bonus/speed.png"),
	BULLETS("textures/bonus/bullets.png"),
	FREEZE("textures/bonus/freeze.png");
	
	private String assetPath;

	private BonusTextureType(String assetPath) {
		this.assetPath = SceneLayoutUtils.IS_HD ? 
				new StringBuilder(assetPath).insert(assetPath.lastIndexOf('/') + 1, "hd_").toString()
				: assetPath;
	}

	public String getAssetPath() {
		return assetPath;
	}
}
