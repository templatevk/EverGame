package pp.game.audio;

import java.util.*;

public enum EntityHitSoundType {
	ZOMBIE("monsters/zombie/sounds/hit.ogg"),
	SPIDER("monsters/spider/sounds/hit.ogg"),
	RUNNER("monsters/runner/sounds/hit.ogg"),
	PLAYER_0("player/sounds/hit_0.ogg"),
	PLAYER_1("player/sounds/hit_1.ogg"),
	PLAYER_2("player/sounds/hit_2.ogg");
	
	public static final int PLAYER_HIT_SOUNDS_COUNT = 3;
	
	private static Random rand = new Random();
	
	private String assetPath;
	
	private EntityHitSoundType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
	
	public static EntityHitSoundType getRandomPlayerSound() {
		return EntityHitSoundType.valueOf("PLAYER_" + rand.nextInt(
				EntityHitSoundType.PLAYER_HIT_SOUNDS_COUNT));
	}
}
