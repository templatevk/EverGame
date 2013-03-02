package pp.game.textures;

import pp.game.utils.geometry.*;

public enum WeaponTextureType {	
	AK_47("weapons/AK_47/player.png"), 
	FLAMETHROWER("weapons/Flamethrower/player.png"), 
	LASER("weapons/Laser/player.png"), 
	PM("weapons/PM/player.png"),  
	SHOTGUN("weapons/Shotgun/player.png"), 
	UZI("weapons/UZI/player.png");
	
	private String assetPath;	
	
	private WeaponTextureType(String assetPath) {
		this.assetPath = SceneLayoutUtils.IS_HD ? 
				new StringBuilder(assetPath).insert(assetPath.lastIndexOf('/') + 1, "hd_").toString()
				: assetPath;
	}

	public String getAssetPath() {
		return assetPath;
	}
}
