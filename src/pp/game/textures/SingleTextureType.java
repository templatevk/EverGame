package pp.game.textures;

import java.util.*;

public enum SingleTextureType {
	STUB("textures/single/blood_0.png"),
	
	BLOOD_0("textures/single/blood_0.png"),
	BLOOD_1("textures/single/blood_1.png"),
	BLOOD_2("textures/single/blood_2.png");

	private static final int BLOOD_TEXTURE_TYPES_COUNT = 3;
	
	private static Random rand = new Random();
	
	private String assetPath;
	
	private SingleTextureType(String assetPath) {
		this.assetPath = assetPath;
	}

	public static SingleTextureType getRandomBlood() {
		return SingleTextureType.valueOf("BLOOD_" + rand.nextInt(BLOOD_TEXTURE_TYPES_COUNT));
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}