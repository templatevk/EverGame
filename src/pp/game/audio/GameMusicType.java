package pp.game.audio;

public enum GameMusicType {
	BASE_0("music/game/base_0.ogg"),
	BASE_1("music/game/base_1.ogg"),
	BASE_2("music/game/base_2.ogg"),
	END_GAME("music/game/end_game.ogg"),
	LOW_HP("music/game/low_hp.ogg");
	
	private String assetPath;
	
	private GameMusicType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}
