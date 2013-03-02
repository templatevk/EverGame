package pp.game.audio;

public enum SoundType {
	PLAYER_DEATH("player/sounds/death.ogg");
	
	private String assetPath;
	
	private SoundType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}
