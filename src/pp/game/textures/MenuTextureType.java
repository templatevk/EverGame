package pp.game.textures;

public enum MenuTextureType {
	MAIN_MENU_EXIT("textures/menu/main_menu_exit.png"),
	MAIN_MENU_NEW_GAME("textures/menu/main_menu_new_game.png"),
	MAIN_MENU_HIGH_SCORES("textures/menu/main_menu_high_scores.png"),
	MAIN_MENU_BACKGROUND("textures/menu/main_menu_background.png"),
	
	PAUSE_MENU_MAIN_MENU("textures/menu/pause_menu_main_menu.png"),
	PAUSE_MENU_RESUME("textures/menu/pause_menu_resume.png");

	private String assetPath;
	
	private MenuTextureType(String assetPath) {
		this.assetPath = assetPath;
	}

	public String getAssetPath() {
		return assetPath;
	}
}

