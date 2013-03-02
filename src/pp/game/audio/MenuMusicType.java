package pp.game.audio;

public enum MenuMusicType {
	MENU("music/menu/menu.ogg");
	
	private String assetPath;
	
	private MenuMusicType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}
